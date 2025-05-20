import typing as t

from datetime import datetime

from shared.domain.enums import ETradeActions
from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderFilling
from shared.domain.enums import EOrderTime


class TradeRequest(t.TypedDict):
    action_type: ETradeActions
    symbol: str
    order: t.Optional[int]
    volume: t.Optional[float]
    price: t.Optional[float]
    stop_limit: t.Optional[float]
    stop_loss: t.Optional[float]
    take_profit: t.Optional[float]
    deviation: t.Optional[float]
    type: t.Optional[EOrderType]
    type_filling: t.Optional[EOrderFilling]
    type_time: t.Optional[EOrderTime]
    expiration: t.Optional[datetime]
    position: t.Optional[int]
    position_by: t.Optional[int]
    comment: t.Optional[str]
    magic_number: t.Optional[int]
