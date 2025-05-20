import typing as t
from abc import ABC, abstractmethod


class ISpecification(ABC):

    @abstractmethod
    def to_query(self) -> t.Any:
        pass

    @abstractmethod
    def to_document(self) -> t.Any:
        pass
