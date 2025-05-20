import pytz

from datetime import datetime

from shared.domain.enums import EDealType
from shared.domain.enums import EDealEntry
from shared.domain.enums import EDealReason
from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from router.domain.ports import TradeAbstract

from ..entities import SettingsInputs
from ..ports import IPendingOrdersMovePositionPriceByFrequency

DEFAULT_TIMEZONE = "America/Sao_Paulo"


class PendingOrdersMovePositionPriceByFrequencyService(IPendingOrdersMovePositionPriceByFrequency):
    def __init__(
            self,
            trade_use_case: type[TradeAbstract] = TradeAbstract,
    ):
        self.timezone = pytz.timezone(DEFAULT_TIMEZONE)
        self.trade = trade_use_case()
        self.time = datetime.now(tz=self.timezone)

    def __call__(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> SettingsInputs:
        time = datetime.now()
        reduce_reentry = settings.manager.tick_to_reduce_reentry
        offset_costs = settings.manager.tick_to_offset_costs
        tick_size = settings.info.trade_tick_size
        tick_value = settings.info.trade_tick_value
        orders: list[TradeDeal] = [
            *filter(
                lambda doc: doc.time_msc >= settings.time.time_now
                and doc.entry == EDealEntry.DEAL_ENTRY_OUT
                and doc.reason == EDealReason.DEAL_REASON_TP, deals_history
            )
        ]

        for order in orders:

            price_entry = order.price
            take_profit = order.price
            profit_in_ticks = order.profit / (order.volume * tick_value)

            if order.type == EDealType.DEAL_TYPE_BUY:

                if profit_in_ticks >= (reduce_reentry-offset_costs):
                    # if total profit (gain by frequency)
                    price_entry += profit_in_ticks * tick_size
                else:
                    # if reduce volume (risk management)
                    price_entry += (profit_in_ticks + reduce_reentry) * tick_size
                    take_profit = price_entry - ((reduce_reentry + offset_costs + offset_costs) * tick_size)

                self.trade.sell_limit(
                    volume=order.volume,
                    price=price_entry,
                    take_profit=take_profit,
                    symbol=order.symbol,
                    magic_number=order.magic_number,
                    comment="F",
                )

            elif order.type == EDealType.DEAL_TYPE_SELL:

                if profit_in_ticks >= (reduce_reentry - offset_costs):
                    # if total profit (gain by frequency)
                    price_entry -= profit_in_ticks * tick_size
                else:
                    # if reduce volume (risk management)
                    price_entry -= (profit_in_ticks + reduce_reentry) * tick_size
                    take_profit = price_entry + ((reduce_reentry + offset_costs + offset_costs) * tick_size)

                self.trade.buy_limit(
                    volume=order.volume,
                    price=price_entry,
                    take_profit=take_profit,
                    symbol=order.symbol,
                    magic_number=order.magic_number,
                    comment="F",
                )

        settings.time.time_now = time
        return settings
