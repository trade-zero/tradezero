import typing as t

from abc import ABC

from .session import ISession
from .connection_string import ConnectionString


class IConnectionRepository(ABC):
    session: t.Optional[ISession]

    def __init__(self, connection_string: ConnectionString) -> None:
        self.connection_string = connection_string
        self.session = None

    def __enter__(self):
        self.session = ISession()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.session = None
