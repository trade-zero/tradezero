import typing as t
from abc import ABC

from ..ports import IConditionSpecification


class GreaterThanSpecification(IConditionSpecification, ABC):

    def is_satisfied_by(self, item: t.Any) -> bool:
        return item > self.value

    def to_query(self) -> str:
        return f" {self.field} > '{self.value}' "

    def to_document(self) -> dict:
        return {self.field: {"$gt": self.field}}
