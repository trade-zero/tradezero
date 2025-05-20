from datetime import datetime

from ..domain.entities import Candlestick
from ..domain.services import CandlestickFormatterService

from ..infrastructure.adapters import Mt5DataFeedGatewayAdapter


class DataFeedUseCase:
    def __init__(
            self,
            gateway: type[Mt5DataFeedGatewayAdapter] = Mt5DataFeedGatewayAdapter,
            formatter: type[CandlestickFormatterService] = CandlestickFormatterService,
    ):
        self.gateway = gateway()
        self.formatter = formatter()
        self.stop = False

    def execute(self, start_time: datetime, symbol: str) -> list[Candlestick]:
        current_candlestick = None

        while True:

            ticks = self.gateway.get_ticks(symbol, start_time)
            candlesticks = self.formatter.format(ticks, candle=current_candlestick)

            if len(ticks) > 0:
                start_time = ticks[-1].time

            if len(candlesticks) > 0:
                # current_candlestick = candlesticks[-1]
                current_candlestick = None

            if self.stop:
                break

        return candlesticks
