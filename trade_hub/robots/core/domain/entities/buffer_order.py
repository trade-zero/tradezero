from dataclasses import field
from dataclasses import dataclass

from ..enums import EDirectionType


@dataclass
class BufferOrder:
    volume: float
    price: float
    symbol: str
    magic_number: int
    type: EDirectionType = field(default=None)
