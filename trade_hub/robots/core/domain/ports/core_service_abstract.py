from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from router.domain.ports import TradeAbstract

from robots.star_lord.domain.entities import SettingsInputs


class ICoreService:
    trade: TradeAbstract

    def __call__(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> None:
        ...
