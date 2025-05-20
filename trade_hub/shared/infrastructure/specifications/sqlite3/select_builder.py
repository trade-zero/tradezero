import typing as t

from shared.domain.specifications.select_builder import ISelectBuilder


class SelectBuilder(ISelectBuilder):

    def __init__(self, *columns):
        ISelectBuilder.__init__(self)
        self._bet = "."
        self.collection_name: t.Optional[str] = None
        self._projection: list = []
        self._order_by_field: t.Optional[str] = None

    def select(self, *fields: str):
        self._projection = list(fields)
        return self

    def builder(self):
        query = "SELECT "
        if self._projection:
            query += ", ".join(self._projection)
        else:
            query += "*"

        query += " FROM "
        if self.database_name:
            query += self.database_name + self._bet

        query += self.collection_name

        if len(self._where_conditions) > 0:
            query += " WHERE "
            for condition in self._where_conditions:
                query += condition.to_query()

        if self._order_by_ascending:
            query += " ORDER BY {} {}".format(
                self._order_by_field, "ASC" if self._order_by_ascending else "DESC"
            )

        if self._limit_value:
            query += " LIMIT {}".format(self._limit_value)

        if self._specification:
            query += self._specification.to_query()

        return query
