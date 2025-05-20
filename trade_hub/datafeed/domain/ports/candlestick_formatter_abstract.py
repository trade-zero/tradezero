from datetime import datetime

from shared.domain.entities import Tick

from ..entities import Candlestick


class CandlestickFormatterAbstract:

    last_tick_timer: datetime = None

    def format(self, ticks: list[Tick]) -> list[Candlestick]:
        ...
