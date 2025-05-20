import typing as t
from dataclasses import dataclass
from dataclasses import fields
from dataclasses import asdict


def get_columns(data: dataclass) -> list[str]:

    return [field.name for field in fields(data)]


def to_database(data: t.Union[dataclass, list[dataclass]]) -> list[dict]:

    if not isinstance(data, list):
        data = [data]

    context = [asdict(i) for i in data]
    return context
