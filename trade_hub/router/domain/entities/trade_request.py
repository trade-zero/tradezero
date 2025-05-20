import typing as t

from datetime import datetime

from dataclasses import dataclass
from dataclasses import field

from shared.domain.enums import ETradeActions
from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderFilling
from shared.domain.enums import EOrderTime


@dataclass
class TradeRequest:
    action_type: ETradeActions
    symbol: str
    order: t.Optional[int] = field(default=None)
    volume: t.Optional[float] = field(default=None)
    price: t.Optional[float] = field(default=None)
    stop_limit: t.Optional[float] = field(default=None)
    stop_loss: t.Optional[float] = field(default=None)
    take_profit: t.Optional[float] = field(default=None)
    deviation: t.Optional[float] = field(default=None)
    type_type: t.Optional[EOrderType] = field(default=None)
    type_filling_type: t.Optional[EOrderFilling] = field(default=None)
    type_time_type: t.Optional[EOrderTime] = field(default=None)
    expiration: t.Optional[datetime] = field(default=None)
    position: t.Optional[int] = field(default=None)
    position_by: t.Optional[int] = field(default=None)
    comment: t.Optional[str] = field(default="")
    magic_number: t.Optional[int] = field(default=None)

    def __post_init__(self):
        self.action_type = ETradeActions(self.action_type)
        self.type_type = EOrderType(self.type_type) if self.type_type else None
        self.type_filling_type = EOrderFilling(self.type_filling_type) if self.type_filling_type else None
        self.type_time_type = EOrderTime(self.type_time_type) if self.type_time_type else None
