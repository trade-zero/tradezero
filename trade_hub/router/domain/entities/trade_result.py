import typing as t

from dataclasses import dataclass
from dataclasses import field

from shared.domain.enums import ETradeRetCode


@dataclass
class TradeResult:
    return_code: ETradeRetCode
    deal: t.Optional[int] = field(default=None)
    order: t.Optional[int] = field(default=None)
    volume: t.Optional[float] = field(default=None)
    price: t.Optional[float] = field(default=None)
    bid: t.Optional[float] = field(default=None)
    ask: t.Optional[float] = field(default=None)
    comment: t.Optional[str] = field(default="")
    request_id: t.Optional[int] = field(default=None)
    return_code_external: t.Optional[int] = field(default=None)

    def __post_init__(self):
        self.return_code = ETradeRetCode(self.return_code)
