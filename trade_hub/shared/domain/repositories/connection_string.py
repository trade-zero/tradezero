import typing as t

from dataclasses import dataclass
from dataclasses import field


@dataclass
class ConnectionString:
    name: t.Optional[str] = field(default=None)
    hostname: t.Optional[str] = field(default=None)
    user: t.Optional[str] = field(default=None)
    password: t.Optional[str] = field(default=None)
    port: t.Optional[t.Union[int, str]] = field(default=None)
    uri: t.Optional[str] = field(default=None)
