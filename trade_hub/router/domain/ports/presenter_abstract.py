import json
import typing as t


class PresenterAbstract:
    @staticmethod
    def to_dict(o: t.Any) -> dict:
        return {}

    @staticmethod
    def to_json(o: t.Any) -> str:
        return json.dumps(PresenterAbstract.to_dict(o))

    @staticmethod
    def to_list_dict(o: list[t.Any]) -> list[dict]:
        return [PresenterAbstract.to_dict(order) for order in o]

    @staticmethod
    def to_list_json(o: list[t.Any]) -> str:
        return json.dumps(PresenterAbstract.to_list_dict(o))
