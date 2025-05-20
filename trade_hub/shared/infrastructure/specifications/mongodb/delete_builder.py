from shared.domain.specifications import IDeleteBuilder


class DeleteBuilder(IDeleteBuilder):

    def builder(self):
        return {
            "filter": self.conditions
        }
