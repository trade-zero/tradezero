import typing as t

from abc import ABC, abstractmethod

from .specification_interface import ISpecification


class IConditionSpecification(ISpecification, ABC):
    def __init__(self, field, value):
        self.field = field
        self.value = value

    @abstractmethod
    def is_satisfied_by(self, item: t.Any) -> bool:
        pass
