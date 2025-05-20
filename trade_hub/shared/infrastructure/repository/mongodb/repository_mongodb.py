import typing as t
from abc import ABC

from shared.domain.repositories import IRepository
from shared.domain.repositories import ConnectionString

from ...specifications.mongodb import QueryBuilder
from .connection_mongodb import ConnectionMongodb


class RepositoryMongodb(IRepository, ABC):
    def __init__(
            self,
            connection_string: ConnectionString,
            database_name: t.Optional[str] = None,
            collection_name: t.Optional[str] = None,
            *,
            connection_diver=ConnectionMongodb,
            builder=QueryBuilder
    ):
        IRepository.__init__(
            self,
            connection_string=connection_string,
            connection_diver=connection_diver,
            database_name=database_name,
            collection_name=collection_name,
            builder=builder
        )

