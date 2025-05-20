import typing as t

from shared.domain.entities import TradeOrder

from ..entities import BufferOrder


def get_closest_order_with_equal_volume(
        buffer: BufferOrder,
        orders: list[TradeOrder],
        censored_comment: str = None,
) -> t.Union[int, None]:

    # if censored_comment:
    #     orders_filter: list[TradeOrder] = [
    #         *filter(lambda x: x.volume_current == buffer.volume and x.comment == censored_comment, orders)]
    # else:
    #     orders_filter: list[TradeOrder] = [*filter(lambda x: x.volume_current == buffer.volume, orders)]
    idx = None
    for i, order in enumerate(orders):
        if order.volume_current == buffer.volume and order.price_open == buffer.price:
            idx = i
            break

    # orders_filter: list[TradeOrder] = [*filter(lambda x: x.volume_current == buffer.volume, orders)]
    #
    # if len(orders_filter) == 0:
    #     return None
    #
    # diff = [*map(lambda x: abs(x.price_open - buffer.price), orders_filter)]
    # it = min(enumerate(diff), key=lambda x: x[1])

    # return it[0]
    return idx
