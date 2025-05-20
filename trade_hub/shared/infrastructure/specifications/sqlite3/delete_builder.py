from shared.domain.specifications import IDeleteBuilder


class DeleteBuilder(IDeleteBuilder):

    def builder(self):
        query = "DELETE FROM {} WHERE {}".format(
            self.collection_name, " AND ".join(self.conditions)
        )
        return query
