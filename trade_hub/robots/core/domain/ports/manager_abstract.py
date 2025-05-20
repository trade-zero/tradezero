import typing as t
from abc import ABC


class ManagerAbstract(ABC):

    def filter_by_magic_number(self, iterable: list[t.Any], magic_number: int) -> list[t.Any]:
        ...

    def check_exit_any(self, iterable: list[t.Any], magic_number: int) -> bool:
        ...
