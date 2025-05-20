from shared.domain.specifications import IUpdateBuilder


class UpdateBuilder(IUpdateBuilder):

    def builder(self):
        query = "UPDATE {} SET {} WHERE {}".format(
            self.collection_name,
            ", ".join("{} = {}".format(key, val) for key, val in self.values.items()),
            " AND ".join(self.conditions),
        )
        return query
