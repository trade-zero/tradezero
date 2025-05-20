from dataclasses import field
from dataclasses import dataclass

from shared.utils import DataClassJsonMixin


@dataclass
class SettingsPosition(DataClassJsonMixin):
    qtd_martingale: int
    qtd_init_lots: float
    smaller_distance_between_lots: float
    position_multiply_factor_forward: float
    position_multiply_factor_backward: float
    ticks_to_make_martingale_forward: float
    ticks_to_make_martingale_backward: float
    ticks_offset: float = field(default=0.)
    is_active_backward: bool = field(default=False)
    is_active_forward: bool = field(default=False)
