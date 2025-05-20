from abc import ABC, abstractmethod


class IBuilder(ABC):

    def reset(self) -> None:
        ...

    @abstractmethod
    def builder(self):
        ...
