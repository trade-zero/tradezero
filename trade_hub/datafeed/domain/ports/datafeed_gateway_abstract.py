from datetime import datetime

from shared.domain.entities import Tick

from ..entities import Candlestick


class DataFeedGatewayAbstract:

    def get_last_tick(self, symbols) -> Tick:
        ...

    def get_ticks(self, symbols: str, start_time: datetime) -> list[Tick]:
        ...

    def get_rates(self, symbols: str, start_time: datetime) -> list[Candlestick]:
        ...
