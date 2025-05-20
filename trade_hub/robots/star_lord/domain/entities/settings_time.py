import pytz
import typing as t

from datetime import datetime

from dataclasses import field
from dataclasses import dataclass

from shared.utils import DataClassJsonMixin

# DEFAULT_TIMEZONE = "Etc/UTC"
DEFAULT_TIMEZONE = "America/Sao_Paulo"
timezone = pytz.timezone(DEFAULT_TIMEZONE)


@dataclass
class SettingsTime(DataClassJsonMixin):
    start_operations: t.Union[datetime, str]
    close_orders: t.Union[datetime, str]
    close_positions: t.Union[datetime, str]
    time_now: t.Union[datetime] = field(default=datetime.now())

    def __post_init__(self):

        if isinstance(self.start_operations, str):
            self.start_operations = datetime.fromisoformat(self.start_operations)

        if isinstance(self.close_orders, str):
            self.close_orders = datetime.fromisoformat(self.close_orders)

        if isinstance(self.close_positions, str):
            self.close_positions = datetime.fromisoformat(self.close_positions)
