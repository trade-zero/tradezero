from shared.utils import ExponentialDistribution
from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from gateway import mt5

from robots.core.domain.enums import EDirectionType
from robots.core.domain.filters import order_filter
from robots.core.domain.mapper import buffer_order_mapper
from robots.core.domain.mapper import trade_order_filter_by_type_mapper
from robots.core.domain.mapper import trade_position_filter_by_type_mapper

from router.domain.ports import TradeAbstract

from ..entities import SettingsInputs
from ..ports import IPendingOrdersInitPositionBackward


class PendingOrdersInitPositionBackwardService(IPendingOrdersInitPositionBackward):
    def __init__(
            self,
            distribution: ExponentialDistribution,
            trade_use_case: type[TradeAbstract] = TradeAbstract,
    ):
        self.trade = trade_use_case()
        self.distribution = distribution

    def __call__(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> None:
        """
        fazer a distribuição das ordens com distribuição de frequência exponêncial.
        se existir ordens apregoada: modificar o preço e retirar o stop-loss e take-profit.
        se sobra ordens remover as ordens não utilizadas.
        verificar se já existe posição onde será colocada a ordem.
        """
        offset = settings.position.ticks_offset * settings.info.trade_tick_size
        volume_total = 0.
        volume_min = settings.info.volume_min
        pts_to_martingale = settings.position.ticks_to_make_martingale_backward * settings.info.trade_tick_size
        smaller_distance = settings.position.smaller_distance_between_lots * settings.info.trade_tick_size
        coverage_size = int(settings.position.ticks_to_make_martingale_backward * settings.position.qtd_martingale)
        coverage_size += int(smaller_distance)

        orders_filtered = trade_order_filter_by_type_mapper(orders=orders_pending)
        positions_filtered = trade_position_filter_by_type_mapper(positions=positions_open)

        positions_filtered.buy.sort(key=lambda x: x.price_open, reverse=True)
        positions_filtered.sell.sort(key=lambda x: x.price_open, reverse=False)

        entre_price_buy = 0
        if len(positions_filtered.buy) > 0:
            entre_price_buy = max(positions_filtered.buy, key=lambda x: x.price_open).price_open

        entre_price_sell = 0
        if len(positions_filtered.sell) > 0:
            entre_price_sell = min(positions_filtered.sell, key=lambda x: x.price_open).price_open

        self.distribution.fit(
            qtd_levels=settings.position.qtd_martingale,
            qtd_target_by_levels=pts_to_martingale,
            qtd_initial_lots=settings.position.qtd_init_lots,
            leverage_factor=settings.position.position_multiply_factor_backward,
        )

        buffer_orders = buffer_order_mapper.from_init_orders(
            distribution=self.distribution,
            direction=settings.globals.direction,
            coverage_size=coverage_size,
            tick_size=settings.info.trade_tick_size,
            smaller_distance=smaller_distance,
            entre_price_buy=entre_price_buy,
            entre_price_sell=entre_price_sell,
            volume_total=volume_total,
            symbol=settings.globals.symbol,
            magic_number=settings.globals.magic_number,
            offset=offset,
            volume_min=volume_min,
            router_order=buffer_order_mapper.router_order_to_position
        )

        positions = []
        if settings.globals.direction == EDirectionType.DIRECTION_BUY:
            positions = positions_filtered.buy
            positions.sort(key=lambda x: x.price_open, reverse=False)
        elif settings.globals.direction == EDirectionType.DIRECTION_SELL:
            positions = positions_filtered.sell
            positions.sort(key=lambda x: x.price_open, reverse=True)

        for position in positions:
            """remove order when exist position"""
            diff = [*map(lambda x: abs(x.price - position.price_open), buffer_orders)]
            i = min(enumerate(diff), key=lambda x: x[1])
            o = buffer_orders[i[0]]
            if i[1] < pts_to_martingale and position.volume == o.volume:
                buffer_orders.pop(i[0])

        # orders_filtered.buy_limit.sort(key=lambda x: x.price_open, reverse=False)
        # orders_filtered.sell_limit.sort(key=lambda x: x.price_open, reverse=True)

        for order in buffer_orders:
            info = mt5.symbol_info_tick(order.symbol)
            if order.type == EDirectionType.DIRECTION_BUY:
                if order.price >= info.ask:
                    continue
                it = order_filter.get_closest_order_with_equal_volume(order, orders=orders_filtered.buy_limit)
                if it is not None:
                    o = orders_filtered.buy_limit.pop(it)
                    if o.price_open != order.price or o.take_profit != 0.0 or o.stop_loss != 0.0:
                        self.trade.order_modify(
                            ticket=o.ticket,
                            take_profit=0.0,
                            stop_loss=0.0,
                            price=float(order.price),
                            comment="",
                        )
                else:
                    self.trade.buy_limit(
                        volume=order.volume,
                        price=float(order.price),
                        symbol=order.symbol,
                        magic_number=order.magic_number,
                    )

            elif order.type == EDirectionType.DIRECTION_SELL:
                if order.price <= info.bid:
                    continue
                it = order_filter.get_closest_order_with_equal_volume(order, orders=orders_filtered.sell_limit)
                if it is not None:
                    o = orders_filtered.sell_limit.pop(it)
                    if o.price_open != order.price or o.take_profit != 0.0 or o.stop_loss != 0.0:
                        self.trade.order_modify(
                            ticket=o.ticket,
                            take_profit=0.0,
                            stop_loss=0.0,
                            price=float(order.price),
                            comment="",
                        )
                else:
                    self.trade.sell_limit(
                        volume=order.volume,
                        price=float(order.price),
                        symbol=order.symbol,
                        magic_number=order.magic_number,
                    )

        if settings.globals.direction == EDirectionType.DIRECTION_BUY:
            if len(orders_filtered.buy_limit) > 0:
                for order in orders_filtered.buy_limit:
                    self.trade.order_delete(ticket=order.ticket)

        if settings.globals.direction == EDirectionType.DIRECTION_SELL:
            if len(orders_filtered.sell_limit) > 0:
                for order in orders_filtered.sell_limit:
                    self.trade.order_delete(ticket=order.ticket)

        # TODO: put stop-loss when not used all positions
