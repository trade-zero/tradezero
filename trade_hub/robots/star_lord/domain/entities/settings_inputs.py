from dataclasses import dataclass

from shared.utils import DataClassJsonMixin
from shared.domain.entities import SymbolInfo

from .settings_global import SettingsGlobal
from .settings_time import SettingsTime
from .settings_position import SettingsPosition
from .settings_manager import SettingsManager


@dataclass
class SettingsInputs(DataClassJsonMixin):
    globals: SettingsGlobal
    time: SettingsTime
    position: SettingsPosition
    hedge: SettingsPosition
    manager: SettingsManager
    info: SymbolInfo
