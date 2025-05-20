from abc import ABC, abstractmethod

from .builder_interface import IBuilder


class IQueryBuilder(IBuilder, ABC):

    @abstractmethod
    def from_database(self, database_name: str):
        pass
