from abc import ABC


class ISession(ABC):
    @classmethod
    def cursor(cls):
        return cls

    @staticmethod
    def execute(params):
        return [params]

    @staticmethod
    def fetchmany(x: int):
        return []

    @staticmethod
    def commit(): ...
