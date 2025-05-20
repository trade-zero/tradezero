import typing as t
import pytz

from datetime import datetime
from dataclasses import dataclass

from .entity import IEntity
from ..enums import EDealType
from ..enums import EDealEntry
from ..enums import EDealReason

FROM_STRING = '%Y-%m-%dT%H:%M:%S:%f'
DEFAULT_TIMEZONE = "Etc/UTC"
timezone = pytz.timezone(DEFAULT_TIMEZONE)


@dataclass
class TradeDeal(IEntity):
    ticket: int
    order: int
    time: t.Union[datetime, int]
    time_msc: t.Union[datetime, int]
    type: t.Union[EDealType, str, int]
    entry: t.Union[EDealEntry, str, int]
    magic_number: int
    position_id: int
    reason: t.Union[EDealReason, str, int]
    volume: float
    price: float
    commission: float
    swap: float
    profit: float
    fee: float
    symbol: str
    comment: str
    external_id: str

    def __post_init__(self):
        if isinstance(self.type, int):
            self.type = EDealType(self.type)
        elif isinstance(self.type, str):
            self.type = EDealType[self.type]
        elif not isinstance(self.type, EDealType):
            raise ValueError("trade_mode must be type EDealType")

        if isinstance(self.entry, int):
            self.entry = EDealEntry(self.entry)
        elif isinstance(self.entry, str):
            self.entry = EDealEntry[self.entry]
        elif not isinstance(self.entry, EDealEntry):
            raise ValueError("trade_mode must be type EDealEntry")

        if isinstance(self.reason, int):
            self.reason = EDealReason(self.reason)
        elif isinstance(self.reason, str):
            self.reason = EDealReason[self.reason]
        elif not isinstance(self.reason, EDealReason):
            raise ValueError("trade_mode must be type EDealReason")

        if not isinstance(self.time, datetime):
            time = datetime.fromtimestamp(self.time, tz=timezone)
            self.time = datetime.fromisoformat(time.strftime(FROM_STRING))

        if not isinstance(self.time_msc, datetime):
            time = datetime.fromtimestamp(self.time_msc/1000, tz=timezone)
            self.time_msc = datetime.fromisoformat(time.strftime(FROM_STRING))
