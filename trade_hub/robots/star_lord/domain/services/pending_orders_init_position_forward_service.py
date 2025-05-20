from shared.utils import ExponentialDistribution
from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from robots.core.domain.enums import EDirectionType
from robots.core.domain.entities import BufferOrder
from robots.core.domain.filters import order_filter
from robots.core.domain.mapper import trade_order_filter_by_type_mapper
from robots.core.domain.mapper import trade_position_filter_by_type_mapper

from router.domain.ports import TradeAbstract

from ..entities import SettingsInputs
from ..ports import IPendingOrdersInitPositionForward


class PendingOrdersInitPositionForwardService(IPendingOrdersInitPositionForward):
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
        direction = settings.globals.direction
        condition_buy = direction == EDirectionType.DIRECTION_BUY or direction == EDirectionType.WITHOUT_DIRECTION
        condition_sell = direction == EDirectionType.DIRECTION_SELL or direction == EDirectionType.WITHOUT_DIRECTION
        buffer_order: list[BufferOrder] = []
        orders_filter = trade_order_filter_by_type_mapper(orders=orders_pending)
        positions_filter = trade_position_filter_by_type_mapper(positions=positions_open)

        positions_filter.buy.sort(key=lambda x: x.price_open, reverse=True)
        positions_filter.sell.sort(key=lambda x: x.price_open, reverse=False)

        entre_price_buy = 0
        if len(positions_filter.buy) > 0:
            entre_price_buy = max(positions_filter.buy, key=lambda x: x.price_open).price_open

        entre_price_sell = 0
        if len(positions_filter.sell) > 0:
            entre_price_sell = min(positions_filter.sell, key=lambda x: x.price_open).price_open

        if condition_buy and len(orders_filter.buy_stop) < settings.position.qtd_martingale:
            for i in range(1, settings.position.qtd_martingale + 1):
                diff = i * settings.position.ticks_to_make_martingale_forward * settings.info.trade_tick_size

                buffer_order += [
                    BufferOrder(
                        volume=settings.position.qtd_init_lots,
                        price=entre_price_buy + diff,
                        symbol=settings.globals.symbol,
                        magic_number=settings.globals.magic_number,
                    )
                ]

            for o in buffer_order:
                it = order_filter.get_closest_order_with_equal_volume(o, orders_filter.buy_stop)
                if it is not None:
                    temp = orders_filter.buy_stop.pop(it)
                    self.trade.order_modify(
                        ticket=temp.ticket,
                        price=o.price,
                        stop_loss=0.0,
                        take_profit=0.0,
                        comment=temp.comment,
                    )

                else:
                    self.trade.buy_stop(
                        volume=o.volume,
                        price=o.price,
                        symbol=o.symbol,
                        magic_number=o.magic_number,
                    )

            for o in orders_filter.buy_stop:
                self.trade.order_delete(ticket=o.ticket)

        if condition_sell and len(orders_filter.sell_stop) < settings.position.qtd_martingale:
            for i in range(1, settings.position.qtd_martingale + 1):
                diff = i * settings.position.ticks_to_make_martingale_forward * settings.info.trade_tick_size

                buffer_order += [
                    BufferOrder(
                        volume=settings.position.qtd_init_lots,
                        price=entre_price_sell - diff,
                        symbol=settings.globals.symbol,
                        magic_number=settings.globals.magic_number,
                    )
                ]

            for o in buffer_order:

                it = order_filter.get_closest_order_with_equal_volume(o, orders_filter.sell_stop)
                if it is not None:
                    temp = orders_filter.sell_stop.pop(it)
                    if o.price != temp.price_open or temp.take_profit != 0.0 or temp.stop_loss != 0.0:
                        print(f"update position forward sell {temp.ticket}", end="\r")
                        self.trade.order_modify(
                            ticket=temp.ticket,
                            price=o.price,
                            stop_loss=0.0,
                            take_profit=0.0,
                            comment=temp.comment,
                        )

                else:
                    self.trade.sell_stop(
                        volume=settings.position.qtd_init_lots,
                        price=o.price,
                        symbol=settings.globals.symbol,
                        magic_number=settings.globals.magic_number,
                    )

            for o in orders_filter.sell_stop:
                self.trade.order_delete(ticket=o.ticket)
