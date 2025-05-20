import typing as t

from datetime import datetime
from dataclasses import dataclass

from shared.domain.entities.entity import IEntity
from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderTime
from shared.domain.enums import EOrderFilling
from shared.domain.enums import EOrderState
from shared.domain.enums import EOrderReason


@dataclass
class TradeOrder(IEntity):
    ticket: int
    symbol: str
    time_setup: datetime
    time_done: datetime
    time_expiration: datetime
    type: EOrderType
    type_time: t.Union[EOrderTime, str, int]
    type_filling: t.Union[EOrderFilling, str, int]
    state: t.Union[EOrderState, str, int]
    position_id: int
    position_by_id: int
    reason: t.Union[EOrderReason, str, int]
    volume_initial: int
    volume_current: int
    price_open: float
    stop_loss: float
    take_profit: float
    price_stop_limit: float
    comment: str
    magic_number: int

    def __post_init__(self):
        if isinstance(self.type, int):
            self.type = EOrderType(self.type)
        elif isinstance(self.type, str):
            self.type = EOrderType[self.type]
        elif not isinstance(self.type, EOrderType):
            raise ValueError("trade_mode must be type EOrderType")

        if isinstance(self.type_time, int):
            self.type_time = EOrderTime(self.type_time)
        elif isinstance(self.type_time, str):
            self.type_time = EOrderTime[self.type_time]
        elif not isinstance(self.type_time, EOrderTime):
            raise ValueError("trade_mode must be type EOrderTime")

        if isinstance(self.type_filling, int):
            self.type_filling = EOrderFilling(self.type_filling)
        elif isinstance(self.type_filling, str):
            self.type_filling = EOrderFilling[self.type_filling]
        elif not isinstance(self.type_filling, EOrderFilling):
            raise ValueError("trade_mode must be type EOrderFilling")

        if isinstance(self.state, int):
            self.state = EOrderState(self.state)
        elif isinstance(self.state, str):
            self.state = EOrderState[self.state]
        elif not isinstance(self.state, EOrderState):
            raise ValueError("trade_mode must be type EOrderState")

        if isinstance(self.reason, int):
            self.reason = EOrderReason(self.reason)
        elif isinstance(self.reason, str):
            self.reason = EOrderReason[self.reason]
        elif not isinstance(self.reason, EOrderReason):
            raise ValueError("trade_mode must be type EOrderReason")
