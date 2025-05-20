from shared.domain.specifications import IUpdateBuilder
from shared.domain.specifications import EqualsSpecification


class UpdateBuilder(IUpdateBuilder):

    def builder(self):
        pass


def test_update_builder():
    # Arrange
    database_name = "prod"
    collection_name = "fruits"
    model = [{"fruit": "apple", "price": 10.1}]
    values = [{"price": 10.1}]
    where = EqualsSpecification("fruit", "apple")

    # Act
    query = UpdateBuilder()
    query.from_database(database_name)
    query.from_collection(collection_name)
    query.update(values)
    query.where(where)

    # Assert
    assert query.database_name == database_name
    assert query.collection_name == collection_name
    assert query.values == values
    assert where in query.conditions
