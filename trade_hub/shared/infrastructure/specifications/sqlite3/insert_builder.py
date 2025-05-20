from shared.domain.specifications import IInsertBuilder


class InsertBuilder(IInsertBuilder):

    def builder(self):
        query = "INSERT INTO {} VALUES ({})".format(
            self.table_name, ", ".join(str(val) for val in self.values)
        )
        return query

