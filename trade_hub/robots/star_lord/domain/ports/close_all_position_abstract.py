from shared.domain.entities import TradePosition

from router.domain.ports import TradeAbstract


class CloseAllPositionAbstract:
    trade: TradeAbstract

    def __call__(
            self,
            positions: list[TradePosition],
    ) -> None:
        print("close_all_position")
