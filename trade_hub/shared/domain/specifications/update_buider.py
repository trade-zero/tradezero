import typing as t

from abc import ABC

from ..ports import IQueryBuilder
from ..ports import IConditionSpecification


class IUpdateBuilder(IQueryBuilder, ABC):
    def __init__(self, values: t.Optional[list] = None):
        self.database_name: t.Optional[str] = None
        self.collection_name: t.Optional[str] = None
        self.conditions: list[IConditionSpecification] = []
        self.values = values

    def update(self, values):
        self.values = values
        return self

    def from_database(self, database_name: str):
        self.database_name = database_name
        return self

    def from_collection(self, collection_name):
        self.collection_name = collection_name
        return self

    def where(self, condition: IConditionSpecification):
        self.conditions.append(condition)
        return self
