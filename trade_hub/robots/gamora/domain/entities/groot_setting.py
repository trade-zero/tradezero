from dataclasses import dataclass

from robots.core.domain.enums import EDirectionType


@dataclass
class GrootSetting:
    symbol: str
    freq: float
    gain: float
    slippage: float
    volume: float
    max_orders: int
    magic_number: int
    direction_type: EDirectionType
