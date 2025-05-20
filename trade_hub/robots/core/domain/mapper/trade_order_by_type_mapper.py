from shared.domain.enums import EOrderType
from shared.domain.entities import TradeOrder

from ..entities import TradeOrderByType


def trade_order_filter_by_type_mapper(orders: list[TradeOrder]) -> TradeOrderByType:

    buy_limit:  list[TradeOrder] = [*filter(lambda x: x.type == EOrderType.ORDER_TYPE_BUY_LIMIT , orders)]
    buy_stop:   list[TradeOrder] = [*filter(lambda x: x.type == EOrderType.ORDER_TYPE_BUY_STOP  , orders)]
    sell_limit: list[TradeOrder] = [*filter(lambda x: x.type == EOrderType.ORDER_TYPE_SELL_LIMIT, orders)]
    sell_stop:  list[TradeOrder] = [*filter(lambda x: x.type == EOrderType.ORDER_TYPE_SELL_STOP , orders)]

    return TradeOrderByType(
        buy_limit=buy_limit,
        buy_stop=buy_stop,
        sell_limit=sell_limit,
        sell_stop=sell_stop,
    )
