from dataclasses import dataclass

from .trade_request import TradeRequest
from .trade_result import TradeResult


@dataclass
class TradeTransaction:
    request: TradeRequest
    result: TradeResult
