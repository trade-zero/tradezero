import pytz

import typing as t

from datetime import datetime
from dataclasses import dataclass

from shared.domain.enums import ETickFlag


timezone = pytz.timezone("Etc/UTC")


@dataclass
class Tick:
    time: t.Union[datetime, int]
    bid: float
    ask: float
    last: float
    volume: float
    time_msc: t.Union[datetime, int]
    flags: ETickFlag
    volume_real: float

    def __post_init__(self):
        self.flags = ETickFlag(self.flags)
        self.time = datetime.fromtimestamp(self.time, timezone)
        self.time_msc = datetime.fromtimestamp(self.time_msc/1000, timezone)
