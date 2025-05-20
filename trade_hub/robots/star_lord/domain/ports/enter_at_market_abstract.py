from ..entities import SettingsInputs

from router.domain.ports import TradeAbstract


class EntreAtMarketAbstract:
    trade: TradeAbstract

    def __call__(
            self,
            settings: SettingsInputs,
    ) -> None:
        print(f"enter_at_market: {settings.globals.direction}")
