import typing as t

from shared.domain.specifications import ISelectBuilder


class SelectBuilder(ISelectBuilder):

    def __init__(self, *fields):
        ISelectBuilder.__init__(self)
        self.collection_name: t.Optional[str] = None
        self._projection: dict = {}
        self.select(*fields)

    def select(self, *fields):
        for field in fields:
            self._projection[field] = 1
        return self

    def builder(self):
        query = {
            "filter": {},
            "projection": {},
            "sort": {},
            "skip": 0,
            "limit": 0,
        }

        for condition in self._where_conditions:
            query["filter"].update(condition.to_document())

        if self._projection:
            query["projection"].update(self._projection)

        if self._order_by_fields:
            query["sort"].update({self._order_by_fields: 1 if self._order_by_ascending else -1})

        if self._limit_value:
            query.update({"limit": self._limit_value})

        if self._specification:
            query.update(self._specification.to_document())

        return query
