from dataclasses import dataclass

from shared.utils import DataClassJsonMixin


@dataclass
class SettingsManager(DataClassJsonMixin):
    max_of_lots: float
    max_of_delta: float
    tick_to_offset_costs: float
    tick_to_reduce_reentry: float
    asynchronous_communication: bool
