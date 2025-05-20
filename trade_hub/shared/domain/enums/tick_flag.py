from enum import Enum


class ETickFlag(Enum):
    TICK_FLAG_BID = 2
    TICK_FLAG_ASK = 4
    TICK_FLAG_LAST = 8
    TICK_FLAG_VOLUME = 16
    TICK_FLAG_BUY = 32
    TICK_FLAG_SELL = 64
