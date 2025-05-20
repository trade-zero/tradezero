import json
import typing as t

from abc import ABC
from enum import Enum
from uuid import UUID
from decimal import Decimal
from datetime import datetime
from dataclasses import asdict
from dataclasses import dataclass


Json = t.Union[dict, list, str, int, float, bool, None]


def isinstance_safe(o, type_):
    try:
        result = isinstance(o, type_)
    except Exception as e:
        return False
    else:
        return result


class ExtendedJSONEncoder(json.JSONEncoder):
    def default(self, o) -> Json:
        result: Json
        if isinstance_safe(o, t.Collection):
            if isinstance_safe(o, t.Mapping):
                result = dict(o)
            else:
                result = list(o)
        elif isinstance_safe(o, datetime):
            result = o.timestamp()
        elif isinstance_safe(o, UUID):
            result = str(o)
        elif isinstance_safe(o, Enum):
            result = o.value
        elif isinstance_safe(o, Decimal):
            result = str(o)
        else:
            result = json.JSONEncoder.default(self, o)
        return result


@dataclass
class DataClassJsonMixin(ABC):
    def to_json(
            self,
            *,
            skipkeys: bool = False,
            ensure_ascii: bool = True,
            check_circular: bool = True,
            allow_nan: bool = True,
            indent: t.Optional[t.Union[int, str]] = None,
            separators: t.Optional[tuple[str, str]] = None,
            default: t.Optional[t.Callable] = None,
            sort_keys: bool = False,
            **kwargs
    ) -> str:
        return json.dumps(
            self.to_dict(),
            cls=ExtendedJSONEncoder,
            skipkeys=skipkeys,
            ensure_ascii=ensure_ascii,
            check_circular=check_circular,
            allow_nan=allow_nan,
            indent=indent,
            separators=separators,
            default=default,
            sort_keys=sort_keys,
            **kwargs
        )

    def to_dict(self) -> dict[str, Json]:
        return asdict(
            self, dict_factory=lambda data: {
                field: value.value if isinstance(value, Enum) else value
                for field, value in data
            }
        )
