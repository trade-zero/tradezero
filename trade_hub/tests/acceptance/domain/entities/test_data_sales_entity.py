from tests.mocks import test_data_raw_sales
from etl.domain.entities.data_sales_entity import DataSalesEntity


def test_data_sales_entity_with_mocks_data():
    # Arrange
    data = test_data_raw_sales()

    # Act
    test_entity = [*map(lambda doc: isinstance(DataSalesEntity(*doc), DataSalesEntity), data)]

    # Assert
    assert all(test_entity) is True
