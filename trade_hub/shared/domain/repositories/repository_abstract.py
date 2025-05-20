import typing as t
from abc import ABC

from ..specifications import IQuery
from .connection_repository import IConnectionRepository
from .connection_string import ConnectionString
from .crud_repository import ICRUDRepository


class IRepository(ICRUDRepository, ABC):

    def __init__(
            self,
            connection_string: ConnectionString,
            database_name: t.Optional[str] = None,
            collection_name: t.Optional[str] = None,
            *,
            connection_diver: type[IConnectionRepository] = IConnectionRepository,
            builder: type[IQuery] = IQuery
    ):
        self.connection = connection_diver(connection_string)
        self.builder = builder()
        self._database_name = database_name
        self._collection_name = collection_name
