from dataclasses import dataclass

from shared.utils import DataClassJsonMixin

from robots.core.domain.enums import EDirectionType
from robots.core.domain.enums import ETradeModeType


@dataclass
class SettingsGlobal(DataClassJsonMixin):
    symbol: str
    magic_number: int
    direction: EDirectionType
    trade_mode: ETradeModeType
