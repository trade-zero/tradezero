from dataclasses import dataclass

from ..enums import EBookType


@dataclass
class BookInfo:
    type: EBookType
    price: float
    volume: float
    volume_dbl: float

    def __post_init__(self):
        self.type = EBookType(self.type)
