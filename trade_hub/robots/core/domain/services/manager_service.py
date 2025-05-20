import typing as t

from ..ports import ManagerAbstract


class ManagerService(ManagerAbstract):

    def filter_by_magic_number(self, iterable: list[t.Any], magic_number: int) -> list[t.Any]:
        return [*filter(lambda x: x.magic_number == magic_number, iterable)]

    def check_exit_any(self, iterable: list[t.Any], magic_number: int) -> bool:
        return any([*map(lambda x: x.magic_number == magic_number, iterable)])
