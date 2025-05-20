from dataclasses import field
from dataclasses import dataclass

from shared.domain.entities import TradeOrder


@dataclass
class TradeOrderByType:
    buy_limit: list[TradeOrder] = field(default_factory=list)
    buy_stop: list[TradeOrder] = field(default_factory=list)
    sell_limit: list[TradeOrder] = field(default_factory=list)
    sell_stop: list[TradeOrder] = field(default_factory=list)
