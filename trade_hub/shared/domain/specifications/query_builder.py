from abc import ABC

from ..ports import IBuilder

from .select_builder import ISelectBuilder
from .insert_builder import IInsertBuilder
from .update_buider import IUpdateBuilder
from .delete_builder import IDeleteBuilder


class IQuery(IBuilder, ABC):

    def select(self, *columns) -> ISelectBuilder:
        class SelectBuilder(ISelectBuilder):

            def builder(self):
                ...

        return SelectBuilder()

    def insert(self, values) -> IInsertBuilder:
        class InsertBuilder(IInsertBuilder):

            def builder(self):
                ...

        return InsertBuilder()

    def update(self, values) -> IUpdateBuilder:
        class UpdateBuilder(IUpdateBuilder):

            def builder(self):
                ...

        return UpdateBuilder()

    def delete(self) -> IDeleteBuilder:
        class DeleteBuilder(IDeleteBuilder):
            def builder(self):
                pass

        return DeleteBuilder()

    def builder(self):
        pass
