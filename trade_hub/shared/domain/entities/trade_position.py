import typing as t

from datetime import datetime
from dataclasses import dataclass

from shared.domain.entities.entity import IEntity
from shared.domain.enums import EPositionType
from shared.domain.enums import EPositionReason


@dataclass
class TradePosition(IEntity):
    ticket: int
    identifier: int
    external_id: str
    symbol: str
    time: datetime
    time_update: datetime
    price_open: float
    volume: float
    stop_loss: float
    take_profit: float
    position_type: t.Union[EPositionType, str, int]
    reason_type: t.Union[EPositionReason, str, int]
    comment: str
    magic_number: int

    def __post_init__(self):
        if isinstance(self.position_type, int):
            self.position_type = EPositionType(self.position_type)
        elif isinstance(self.position_type, str):
            self.position_type = EPositionType[self.position_type]
        elif not isinstance(self.position_type, EPositionType):
            raise ValueError("trade_mode must be type EPositionType")

        if isinstance(self.reason_type, int):
            self.reason_type = EPositionReason(self.reason_type)
        elif isinstance(self.reason_type, str):
            self.reason_type = EPositionReason[self.reason_type]
        elif not isinstance(self.reason_type, EPositionReason):
            raise ValueError("trade_mode must be type EPositionReason")
