from tests.mocks import test_data_raw_sales_meta
from etl.domain.entities.data_sales_goal_entity import DataSalesGoalEntity


def test_data_sales_entity_with_mocks_data():
    # Arrange
    data = test_data_raw_sales_meta()

    # Act
    test_entity = [*map(lambda doc: isinstance(DataSalesGoalEntity(*doc), DataSalesGoalEntity), data)]

    # Assert
    assert all(test_entity) is True
