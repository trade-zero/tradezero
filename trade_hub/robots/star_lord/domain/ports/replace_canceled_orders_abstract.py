from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from router.domain.ports import TradeAbstract

from ..entities import SettingsInputs


class ReplaceCanceledOrdersAbstract:
    trade: TradeAbstract

    def __call__(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> None:
        print(f"replace_canceled_orders: {settings.globals.direction}")
