import typing as t

from abc import ABC

from ..ports import IQueryBuilder

from ..ports import IPaginationSpecification
from ..ports import IConditionSpecification


class ISelectBuilder(IQueryBuilder, ABC):

    def __init__(self):
        self.database_name: t.Optional[str] = None
        self.collection_name: t.Optional[str] = None
        self._projection: t.Union[dict, list] = {}
        self.values: dict = {}
        self._limit_value: t.Optional[int] = None
        self._order_by_fields: t.Optional[str] = None
        self._order_by_ascending: t.Optional[bool] = None
        self._where_conditions: list[IConditionSpecification] = []
        self._specification: t.Optional[IPaginationSpecification] = None

    def select(self, *fields):
        self._projection: list = list(fields)
        return self

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
            self._where_conditions.append(i)

        return self

    def order_by(self, fields, ascending=True):
        self._order_by_fields = fields
        self._order_by_ascending = ascending
        return self

    def limit(self, limit: int):
        self._limit_value = limit
        return self

    def with_spec(self, spec: IPaginationSpecification):
        self._specification = spec
        return self

    def with_values(self, values: list[t.Any]):
        self.values = values
        return self
