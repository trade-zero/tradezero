import typing as t
from datetime import datetime

from dataclasses import field
from dataclasses import dataclass


@dataclass
class Candlestick:

    time: datetime
    open: float
    high: float
    low: float
    close: float
    volume_tick: float
    volume_real: float
    spread: t.Optional[int] = field(default=None)

    def __repr__(self):
        return str({
            "time": self.time.strftime("%Y-%m-%d %H:%M:%S"),
            "open": self.open,
            "high": self.high,
            "low": self.low,
            "close": self.close,
            "volume": self.volume_real,
        })
