from router.domain.ports import TradeAbstract

from shared.domain.entities import TradePosition

from ..ports import CloseAllPositionAbstract


class CloseAllPositionService(CloseAllPositionAbstract):
    def __init__(self, trade: type[TradeAbstract] = TradeAbstract):
        self.trade = trade()

    def __call__(self, positions: list[TradePosition]) -> None:
        for position in positions:
            self.trade.position_close(position.ticket)
