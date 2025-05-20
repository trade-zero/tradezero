from shared.domain.enums import EOrderType
from shared.domain.enums import EPositionType
from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from router import TradeUseCase
from router.domain.ports import TradeAbstract

from robots.core.domain.enums import EDirectionType

from ..entities import SettingsInputs
from ..ports import IPutTargetToReducePosition


class PutTargetToReducePositionService(IPutTargetToReducePosition):
    def __init__(
            self,
            trade_use_case: type[TradeAbstract] = TradeUseCase,
    ):
        self.trade = trade_use_case()

    def __call__(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition],
    ) -> None:
        if len(positions_open) == 0:
            return

        if settings.globals.direction == EDirectionType.DIRECTION_BUY:
            orders_limit: list[TradeOrder] = [
                *filter(lambda x: x.type == EOrderType.ORDER_TYPE_BUY_LIMIT, orders_pending)]
            if len(orders_limit) > 0:
                orders_max: TradeOrder = max(orders_limit, key=lambda doc: doc.price_open)
                # GET: take the smallest position above the largest order.
                positions_buy: list[TradePosition] = [
                    *filter(lambda x: x.price_open >= orders_max.price_open, positions_open)]
                position_min: TradePosition = min(positions_buy, key=lambda doc: doc.price_open)
            else:
                position_min: TradePosition = min(positions_open, key=lambda doc: doc.price_open)

            cutoff = position_min.price_open
            cutoff += (settings.position.ticks_to_make_martingale_backward * settings.info.trade_tick_size)
            for position in positions_open:
                if position.position_type == EPositionType.POSITION_TYPE_BUY and position.price_open > cutoff:
                    tp = position.price_open + (settings.manager.tick_to_offset_costs * settings.info.trade_tick_size)
                    if position.take_profit != tp:
                        self.trade.position_modify(
                            symbol=settings.info.name,
                            ticket=position.ticket,
                            take_profit=tp,
                        )

        if settings.globals.direction == EDirectionType.DIRECTION_SELL:
            orders_limit: list[TradeOrder] = [
                *filter(lambda x: x.type == EOrderType.ORDER_TYPE_SELL_LIMIT, orders_pending)]
            if len(orders_limit) > 0:
                orders_min: TradeOrder = min(orders_limit, key=lambda doc: doc.price_open)
                # GET: take the smallest position above the largest order.
                positions_sell: list[TradePosition] = [
                    *filter(lambda x: x.price_open <= orders_min.price_open, positions_open)]
                position_max: TradePosition = max(positions_sell, key=lambda doc: doc.price_open)
            else:
                position_max: TradePosition = max(positions_open, key=lambda doc: doc.price_open)

            cutoff = position_max.price_open
            cutoff -= (settings.position.ticks_to_make_martingale_backward * settings.info.trade_tick_size)
            for position in positions_open:
                if position.position_type == EPositionType.POSITION_TYPE_SELL and position.price_open < cutoff:
                    tp = position.price_open - (settings.manager.tick_to_offset_costs * settings.info.trade_tick_size)
                    if position.take_profit != tp:
                        self.trade.position_modify(
                            symbol=settings.info.name,
                            ticket=position.ticket,
                            take_profit=tp,
                        )
