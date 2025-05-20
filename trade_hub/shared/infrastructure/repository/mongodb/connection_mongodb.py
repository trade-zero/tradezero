from pymongo import MongoClient

from shared.domain.repositories import IConnectionRepository
from shared.domain.repositories import ConnectionString


class ConnectionMongodb(IConnectionRepository):

    def __init__(self, connection_string: ConnectionString) -> None:
        IConnectionRepository.__init__(self, connection_string)

    def __enter__(self):
        self.session = MongoClient(self.connection_string.uri)
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.session.close()
        self.session = None
