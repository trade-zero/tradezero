from shared.domain.specifications import IQuery

from .select_builder import SelectBuilder
from .insert_builder import InsertBuilder
from .update_builder import UpdateBuilder
from .delete_builder import DeleteBuilder


class QueryBuilder(IQuery):

    def select(self, *columns):
        return SelectBuilder(*columns)

    def insert(self, values):
        return InsertBuilder(values)

    def update(self, values):
        return UpdateBuilder(values)

    def delete(self):
        return DeleteBuilder()
