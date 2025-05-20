from router.domain.ports import TradeAbstract

from shared.domain.entities import TradeOrder

from ..ports import CloseAllOrdersAbstract


class CloseAllOrdersService(CloseAllOrdersAbstract):
    def __init__(self, trade: type[TradeAbstract] = TradeAbstract):
        self.trade = trade()

    def __call__(self, orders: list[TradeOrder]) -> None:
        for order in orders:
            self.trade.order_delete(order.ticket)
