import typing as t

from shared.domain.repositories import IRepository

from ..entities import Order


class OrderRepository(IRepository):
    def save_order(self, order: t.Union[list[Order], Order]) -> None:
        pass

    def get_orders(self) -> list[Order]:
        pass
