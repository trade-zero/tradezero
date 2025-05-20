import typing as t


from abc import ABC

from ..specifications import IQuery
from ..ports import IConditionSpecification

from .connection_repository import IConnectionRepository
# from .session import ISession


class ICRUDRepository(ABC):
    connection: t.Optional[IConnectionRepository] = None
    _database_name: t.Optional[str] = None
    _collection_name: t.Optional[str] = None
    # _collection: t.Optional[ISession] = None
    builder: t.Optional[IQuery] = None

    def insert(self, data) -> list:
        query = self.builder.insert(data)\
            .from_database(self._database_name)\
            .from_collection(self._collection_name)\
            .builder()

        with self.connection as cnx:
            cnx.session[self._database_name][self._collection_name].insert_one(query)

        return []

    def select(self, *fields, where: list[IConditionSpecification] = None) -> list:
        if not where:
            where = []

        query = self.builder.select(*fields)\
            .from_database(self._database_name)\
            .from_collection(self._collection_name)\
            .where(where).builder()

        with self.connection as cnx:
            docs = [*cnx.session[self._database_name][self._collection_name].find(**query)]

        return docs

    def update(self, values, condition: list[IConditionSpecification]):
        query = self.builder.update(values)\
            .from_database(self._database_name)\
            .from_collection(self._collection_name)\
            .where(condition)\
            .builder()

        with self.connection as cnx:
            ...

    def delete(self, condition: list[IConditionSpecification]):
        query = self.builder.delete()\
            .where(condition)\
            .builder()

        with self.connection as cnx:
            ...
