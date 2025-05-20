from shared.domain.specifications import PaginationSpecification
from shared.domain.specifications import EqualsSpecification
from shared.domain.specifications import ISelectBuilder


class SelectBuilder(ISelectBuilder):
    def builder(self):
        pass


def test_select_builder():
    # Arrange
    database_name = "prod"
    collection_name = "fruits"
    fields = ["fruit", "price"]
    limit = 10
    order_by_fields = "price"
    order_by_ascending = True
    where = EqualsSpecification("fruit", "apple")
    spec = PaginationSpecification().pagination(page=0, per_page=5)

    # Act
    query = SelectBuilder()
    query.select(*fields).from_database(database_name).from_collection(collection_name)
    query.order_by(order_by_fields, order_by_ascending)
    query.limit(limit)
    query.where(where)
    query.with_spec(spec)

    # Assert
    assert query.database_name == database_name
    assert query.collection_name == collection_name
    assert query._projection == fields
    assert query._order_by_fields == order_by_fields
    assert query._order_by_ascending == order_by_ascending
    assert query._limit_value == limit
    assert where in query._where_conditions
    assert query._specification == spec
