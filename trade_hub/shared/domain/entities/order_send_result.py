from dataclasses import dataclass

from shared.domain.enums import ETradeRetCode
from shared.domain.entities import TradeRequest


@dataclass
class OrderSendResult:
    retcode: ETradeRetCode
    deal: int
    order: int
    volume: float
    price: float
    bid: float
    ask: float
    comment: str
    request_id: int
    retcode_external: int
    request: TradeRequest

    def __post_init__(self):
        self.retcode = ETradeRetCode(self.retcode)
