from math import trunc

from shared.utils import ExponentialDistribution

from robots.core.domain.enums import EDirectionType

from ..entities import BufferOrder


def router_order_to_position(
        direction: EDirectionType,
        entre_price_buy: float,
        entre_price_sell: float,
        current_price: float,
        offset: float,
) -> tuple[EDirectionType, float]:

    if direction == EDirectionType.DIRECTION_BUY or direction == EDirectionType.WITHOUT_DIRECTION:
        type_ = EDirectionType.DIRECTION_BUY
        price = entre_price_buy - (current_price + offset)

    elif direction == EDirectionType.DIRECTION_SELL or direction == EDirectionType.WITHOUT_DIRECTION:
        type_ = EDirectionType.DIRECTION_SELL
        price = entre_price_sell + (current_price + offset)

    else:
        raise ValueError(f"direction must be EDirectionType: {direction}")

    return type_, price


def router_order_to_hedge(
        direction: EDirectionType,
        entre_price_buy: float,
        entre_price_sell: float,
        current_price: float,
        offset: float,
) -> tuple[EDirectionType, float]:

    if direction == EDirectionType.DIRECTION_BUY or direction == EDirectionType.WITHOUT_DIRECTION:
        type_ = EDirectionType.DIRECTION_BUY
        # if entre_price_sell != 0.0:
        #     price = entre_price_sell - (current_price + offset)
        # else:
        #     price = entre_price_buy - (current_price + offset)
        price = entre_price_buy - (current_price + offset)

    elif direction == EDirectionType.DIRECTION_SELL or direction == EDirectionType.WITHOUT_DIRECTION:
        type_ = EDirectionType.DIRECTION_SELL
        # if entre_price_buy != 0.0:
        #     price = entre_price_buy + (current_price + offset)
        # else:
        #     price = entre_price_sell + (current_price + offset)
        price = entre_price_sell + (current_price + offset)

    else:
        raise ValueError(f"direction must be EDirectionType: {direction}")

    return type_, price


def from_init_orders(
        distribution: ExponentialDistribution,
        direction: EDirectionType,
        coverage_size: int,
        tick_size: float,
        smaller_distance: float,
        entre_price_buy: float,
        entre_price_sell: float,
        volume_total: float,
        symbol: str,
        magic_number: int,
        offset: float,
        router_order: lambda x: x,
        volume_min: float = 1.0,
) -> list[BufferOrder]:
    buffer_orders: list[BufferOrder] = []
    for tick in range(1, coverage_size + 1):
        price = (tick * tick_size)
        volume_current = distribution.predict(price)
        volume_diff = abs(volume_total - volume_current)

        # print(f"{price}, {volume_diff=}, {volume_total=}, {volume_current=}")
        if price % smaller_distance == 0 and volume_diff >= volume_min:
            volume_total = trunc(volume_current)
            volume = float(trunc(volume_diff))

            type_, price_ = router_order(
                direction=direction,
                entre_price_buy=entre_price_buy,
                entre_price_sell=entre_price_sell,
                current_price=price,
                offset=offset,
            )

            buffer_orders += [
                BufferOrder(
                    type=type_,
                    volume=volume,
                    price=price_,
                    symbol=symbol,
                    magic_number=magic_number,
                )
            ]

    return buffer_orders
