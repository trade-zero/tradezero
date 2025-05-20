import typing as t
from abc import ABC

from ..ports import IConditionSpecification


class EqualsSpecification(IConditionSpecification, ABC):

    def is_satisfied_by(self, item: t.Any) -> bool:
        return self.value == item

    def to_query(self) -> t.Any:
        return f" {self.field} = {self.value} "

    def to_document(self) -> t.Any:
        return {self.field: self.value}
