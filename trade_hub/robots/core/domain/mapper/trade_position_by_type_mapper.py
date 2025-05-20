from shared.domain.enums import EPositionType
from shared.domain.entities import TradePosition

from ..entities import TradePositionByType


def trade_position_filter_by_type_mapper(positions: list[TradePosition]) -> TradePositionByType:

    buy: list[TradePosition] = [*filter(lambda x: x.position_type == EPositionType.POSITION_TYPE_BUY, positions)]
    sell: list[TradePosition] = [*filter(lambda x: x.position_type == EPositionType.POSITION_TYPE_SELL, positions)]

    return TradePositionByType(buy=buy, sell=sell)
