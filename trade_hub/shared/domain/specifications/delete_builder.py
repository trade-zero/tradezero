import typing as t

from abc import ABC

from ..ports import IQueryBuilder
from ..ports import IConditionSpecification


class IDeleteBuilder(IQueryBuilder, ABC):
    def __init__(self):
        self.database_name: t.Optional[str] = None
        self.collection_name: t.Optional[str] = None
        self.conditions: list[IConditionSpecification] = []

    def from_database(self, database_name: str):
        self.database_name = database_name
        return self

    def from_collection(self, collection_name: str):
        self.collection_name = collection_name
        return self

    def where(self, conditions: t.Union[list[IConditionSpecification], IConditionSpecification]):

        if not isinstance(conditions, list):
            conditions = [conditions]

        for i in conditions:
            self.conditions.append(i)

        return self
