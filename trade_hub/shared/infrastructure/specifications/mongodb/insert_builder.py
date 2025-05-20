from shared.domain.specifications import IInsertBuilder


class InsertBuilder(IInsertBuilder):

    def builder(self):

        return {"documents": self.values}
