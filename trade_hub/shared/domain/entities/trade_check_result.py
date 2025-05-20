from dataclasses import dataclass

from shared.domain.enums import ETradeRetCode


@dataclass
class TradeCheckResult:
    return_code: ETradeRetCode
    balance: float
    equity: float
    profit: float
    margin: float
    margin_free: float
    margin_level: float
    comment: str

    def __post_init__(self):
        self.return_code = ETradeRetCode(self.return_code)
