from enum import Enum


class ETimeframe(Enum):
    TIMEFRAME_M1 = 1  # 1 minute
    TIMEFRAME_M2 = 2  # 2 minutes
    TIMEFRAME_M3 = 3  # 3 minutes
    TIMEFRAME_M4 = 4  # 4 minutes
    TIMEFRAME_M5 = 5  # 5 minutes
    TIMEFRAME_M6 = 6  # 6 minutes
    TIMEFRAME_M10 = 10  # 10 minutes
    TIMEFRAME_M12 = 12  # 12 minutes
    TIMEFRAME_M15 = 15  # 15 minutes
    TIMEFRAME_M20 = 20  # 20 minutes
    TIMEFRAME_M30 = 30  # 30 minutes
    TIMEFRAME_H1 = 16385  # 1 hour
    TIMEFRAME_H2 = 16386  # 2 hours
    TIMEFRAME_H3 = 16387  # 3 hours
    TIMEFRAME_H4 = 16388  # 4 hours
    TIMEFRAME_H6 = 16390  # 6 hours
    TIMEFRAME_H8 = 16392  # 8 hours
    TIMEFRAME_H12 = 16396  # 12 hours
    TIMEFRAME_D1 = 16408  # 1 day
    TIMEFRAME_W1 = 32769  # 1 week
    TIMEFRAME_MN1 = 49153  # 1 month
