import typing as t

from shared.utils import Singleton
from shared.domain.entities import Tick

from ..entities import Candlestick
from ..ports import CandlestickFormatterAbstract


class CandlestickFormatterService(CandlestickFormatterAbstract, metaclass=Singleton):

    def format(self, ticks: list[Tick], candle: t.Optional[Candlestick] = None) -> list[Candlestick]:

        if len(ticks) == 0:
            return []

        # _ = [map(lambda doc: doc.time.replace(second=0, microsecond=0), ticks)]

        ohlc = []

        for tick in ticks:
            tick.time = tick.time.replace(second=0, microsecond=0)

            if candle is None:
                candle = Candlestick(
                    time=tick.time,
                    open=tick.last,
                    high=max(tick.ask, tick.last),
                    low=min(tick.bid, tick.last),
                    close=tick.last,
                    volume_tick=tick.volume,
                    volume_real=tick.volume_real,
                )

            if candle.time == tick.time:
                candle.high = max(candle.high, tick.last)
                candle.low = min(candle.low, tick.last)
                candle.close = tick.last
                candle.volume_tick += tick.volume
                candle.volume_real += tick.volume_real
            else:
                ohlc.append(candle)
                print(candle)

                candle = None

        # Add the last candle
        ohlc.append(candle)
        print(candle, end="\r")

        return ohlc
