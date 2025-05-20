import typing as t
from abc import ABC

from ..ports import IConditionSpecification


class InnerJoinSpecification(IConditionSpecification, ABC):

    def __init__(self, table, condition: IConditionSpecification):
        self.join_type = "RIGHT JOIN"
        self.join_table = table
        self.join_condition = condition

    def is_satisfied_by(self, item: t.Any) -> bool:
        return self.value == item

    def to_query(self) -> t.Any:
        return "{} {} ON {}".format(self.join_type, self.join_table, self.join_condition)

    def to_document(self) -> t.Any:
        return {self.field: self.value}
