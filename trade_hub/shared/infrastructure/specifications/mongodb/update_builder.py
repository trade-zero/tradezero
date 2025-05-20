from shared.domain.specifications import IUpdateBuilder


class UpdateBuilder(IUpdateBuilder):

    def builder(self):
        return {
            "filter": self.conditions,
            "update": {"$set": self.values}
        }
