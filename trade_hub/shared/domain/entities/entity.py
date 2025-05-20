from dataclasses import asdict
from enum import Enum


class IEntity:

    def to_json(self) -> dict:
        content = asdict(self)

        for k, v in content.items():
            if isinstance(v, Enum):
                content[k] = v.name

        return content
