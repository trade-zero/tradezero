from datetime import datetime

from gateway import mt5
from shared.utils import Singleton
from shared.domain.entities import Tick
from shared.domain.enums import ECopyTicks

from ...domain.entities import Candlestick
from ...domain.ports import DataFeedGatewayAbstract


class Mt5DataFeedGatewayAdapter(DataFeedGatewayAbstract, metaclass=Singleton):

    def __init__(self, buffer_size: int = 10_000):
        self.buffer_size = buffer_size
        mt5.connect()

    def get_last_tick(self, symbols) -> Tick:
        if not mt5.is_connected():
            raise ValueError("DataFeedGatewayAbstract is not connected")

        last_tick = mt5.symbol_info_tick(symbols)

        return Tick(
            time=last_tick.time,
            bid=last_tick.bid,
            ask=last_tick.ask,
            last=last_tick.last,
            volume=last_tick.volume,
            time_msc=last_tick.time_msc,
            flags=last_tick.flags,
            volume_real=last_tick.volume_real,
        )

    def get_ticks(self, symbol: str, start_time: datetime) -> list[Tick]:
        if not mt5.is_connected():
            raise ValueError("DataFeedGatewayAbstract is not connected")

        # start_time = start_time.replace(tzinfo=self.timezone)

        ticks = []
        raw_ticks = mt5.copy_ticks_from(
            symbol=symbol,
            date_from=start_time,
        )

        for i in range(raw_ticks.shape[0]):
            raw_tick = raw_ticks[i]
            tick = Tick(
                time=raw_tick[0],
                bid=raw_tick[1],
                ask=raw_tick[2],
                last=raw_tick[3],
                volume=raw_tick[4],
                time_msc=raw_tick[5],
                flags=raw_tick[6],
                volume_real=raw_tick[7],
            )
            ticks.append(tick)

        return ticks

    def get_rates(self, symbol: str, start_time: datetime) -> list[Candlestick]:
        if not mt5.is_connected():
            raise ValueError("DataFeedGatewayAbstract is not connected")

        candlestick = []
        # mt5.symbol_select(symbol, True)
        raw_rates = mt5.copy_rates_from(symbol, start_time, self.buffer_size, ECopyTicks.COPY_TICKS_ALL)

        for i in range(raw_rates.shape[0]):
            raw_rate = raw_rates[i]
            candle = Candlestick(
                time=raw_rate[0],
                open=raw_rate[1],
                high=raw_rate[2],
                low=raw_rate[3],
                close=raw_rate[4],
                volume_tick=raw_rate[5],
                spread=raw_rate[6],
                volume_real=raw_rate[7],
            )
            candlestick.append(candle)

        return candlestick
