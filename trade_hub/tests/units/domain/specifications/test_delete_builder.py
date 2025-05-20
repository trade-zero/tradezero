from shared.domain.specifications import IDeleteBuilder
from shared.domain.specifications import EqualsSpecification


class DeleteBuilder(IDeleteBuilder):

    def builder(self):
        pass


def test_delete_builder():
    # Arrange
    database_name = "prod"
    collection_name = "fruits"
    where = EqualsSpecification("fruit", "apple")

    # Act
    query = DeleteBuilder()
    query.from_database(database_name)
    query.from_collection(collection_name)
    query.where(where)

    # Assert
    assert query.database_name == database_name
    assert query.collection_name == collection_name
    assert where in query.conditions
