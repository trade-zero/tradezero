from dataclasses import field
from dataclasses import dataclass

from shared.domain.entities import TradePosition


@dataclass
class TradePositionByType:
    buy: list[TradePosition] = field(default_factory=list)
    sell: list[TradePosition] = field(default_factory=list)
