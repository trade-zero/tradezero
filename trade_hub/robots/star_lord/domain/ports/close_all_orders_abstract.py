from shared.domain.entities import TradeOrder

from router.domain.ports import TradeAbstract


class CloseAllOrdersAbstract:
    trade: TradeAbstract

    def __call__(
            self,
            orders: list[TradeOrder],
    ) -> None:
        print("close_all_orders")
