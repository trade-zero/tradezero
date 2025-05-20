from gateway import mt5
from robots.core.domain.enums import EDirectionType

from router import TradeUseCase
from router.domain.ports import TradeAbstract

from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderState

from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..entities import SettingsInputs
from ..ports import ReplaceCanceledOrdersAbstract


class ReplaceCanceledOrdersService(ReplaceCanceledOrdersAbstract):
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
            positions_open: list[TradePosition]
    ) -> None:

        tick_to_reduce_reentry = settings.manager.tick_to_reduce_reentry
        tick_to_offset_costs = settings.manager.tick_to_offset_costs
        trade_tick_size = settings.info.trade_tick_size

        orders_history: list[TradeOrder] = [
            *filter(lambda doc: doc.state == EOrderState.ORDER_STATE_CANCELED, orders_history)]

        # TODO: NÃO RECOLAR ORDEM ONDE JÁ EXISTE ORDENS
        # TODO: SE A DISTÂNCIA FOR LONGA COLOCAR O GAIN
        for o in orders_pending:
            info = mt5.symbol_info_tick(o.symbol)
            cache = mt5.orders_get(ticket=o.ticket)
            if len(cache) > 0:
                continue

            for position in positions_open:
                if o.price_open == position.price_open and o.volume_current == position.volume and o.comment == position.price_open:
                    continue

            if o.type == EOrderType.ORDER_TYPE_SELL_LIMIT:
                if info.ask > o.price_open:
                    if o.take_profit == 0:
                        if abs(info.ask - o.price_open) > float(tick_to_reduce_reentry * trade_tick_size):
                            o.take_profit = float(o.price_open - (tick_to_offset_costs * trade_tick_size))

                    self.trade.sell(
                        volume=o.volume_current,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )
                else:
                    self.trade.sell_limit(
                        volume=o.volume_current,
                        price=o.price_open,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )

            elif o.type == EOrderType.ORDER_TYPE_SELL_STOP:
                if info.ask > o.price_open:
                    self.trade.sell_stop(
                        volume=o.volume_current,
                        price=o.price_open,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )
                else:
                    self.trade.sell_limit(
                        volume=o.volume_current,
                        price=o.price_open,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )

            elif o.type == EOrderType.ORDER_TYPE_BUY_LIMIT:
                if info.ask < o.price_open:
                    if o.take_profit == 0:
                        if abs(info.ask - o.price_open) > float(tick_to_reduce_reentry * trade_tick_size):
                            o.take_profit = float(o.price_open + (tick_to_offset_costs * trade_tick_size))

                    self.trade.buy(
                        volume=o.volume_current,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )
                else:
                    self.trade.buy_limit(
                        volume=o.volume_current,
                        price=o.price_open,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )

            elif o.type == EOrderType.ORDER_TYPE_BUY_STOP:
                if info.ask < o.price_open:
                    self.trade.buy_stop(
                        volume=o.volume_current,
                        price=o.price_open,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )
                else:
                    self.trade.buy_limit(
                        volume=o.volume_current,
                        price=o.price_open,
                        symbol=o.symbol,
                        take_profit=o.take_profit,
                        magic_number=o.magic_number,
                        comment=o.comment,
                    )

            print(mt5.last_error())
