import json

from ...domain.entities import Order
from ...domain.ports import PresenterAbstract


class OrderPresenter(PresenterAbstract):
    @staticmethod
    def to_dict(order: Order) -> dict:
        return {
            'symbol': order.symbol,
            'order_type': order.order_type,
            'price': order.price,
            'stop_loss': order.stop_loss,
            'take_profit': order.take_profit,
            'volume': order.volume,
            'magic_number': order.magic_number,
        }

    @staticmethod
    def to_json(order: Order) -> str:
        return json.dumps(OrderPresenter.to_dict(order))

    @staticmethod
    def to_list_dict(orders: list[Order]) -> list[dict]:
        return [OrderPresenter.to_dict(order) for order in orders]

    @staticmethod
    def to_list_json(orders: list[Order]) -> str:
        return json.dumps(OrderPresenter.to_list_dict(orders))
