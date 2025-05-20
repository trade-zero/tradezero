from shared.domain.specifications import IInsertBuilder


class InsertBuilder(IInsertBuilder):

    def builder(self):
        pass


def test_insert_builder():
    # Arrange
    database_name = "prod"
    collection_name = "fruits"
    values = [{"apple": 10.1, "limon": 1.99}]

    # Act
    query = InsertBuilder()
    query.from_database(database_name)
    query.from_collection(collection_name)
    query.insert(values)

    # Assert
    assert query.database_name == database_name
    assert query.collection_name == collection_name
    assert query.values == values


def test_insert_builder_add_value():
    # Arrange
    database_name = "prod"
    collection_name = "fruits"
    a = {"apple": 10.1}
    b = {"limon": 1.99}
    values = [a, b]

    # Act
    query = InsertBuilder()
    query.from_database(database_name)
    query.from_collection(collection_name)
    query.insert([a])

    # Assert
    assert query.database_name == database_name
    assert query.collection_name == collection_name
    assert query.values == [a]

    # Act
    query.insert([b])

    assert query.values == values
