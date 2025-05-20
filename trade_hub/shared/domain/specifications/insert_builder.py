import typing as t

from abc import ABC

from ..ports import IQueryBuilder


class IInsertBuilder(IQueryBuilder, ABC):
    def __init__(self, values: t.Optional[list] = None):
        self.database_name: t.Optional[str] = None
        self.collection_name: t.Optional[str] = None
        self.values = values or []

    def insert(self, values: list):

        if not isinstance(values, list):
            values = [values]

        self.values += values
        return self

    def from_database(self, database_name: str):
        self.database_name = database_name
        return self

    def from_collection(self, collection_name: str):
        self.collection_name = collection_name
        return self
