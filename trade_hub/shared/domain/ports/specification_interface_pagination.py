from abc import ABC, abstractmethod

from .specification_interface import ISpecification


class IPaginationSpecification(ISpecification, ABC):
    def __init__(self):
        self.per_page: int = 25
        self.offset: int = 0

    @abstractmethod
    def pagination(self, page: int, per_page: int):
        pass
