from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from router.domain.ports import TradeAbstract

from ..entities import SettingsInputs
from ..ports import IPendingOrdersMoveHedgePriceByFrequency


class PendingOrdersMoveHedgePriceByFrequencyService(IPendingOrdersMoveHedgePriceByFrequency):
    def __init__(
            self,
            trade_use_case: type[TradeAbstract] = TradeAbstract,
    ):
        self.trade = trade_use_case()

    def __call__(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> SettingsInputs:

        ...
