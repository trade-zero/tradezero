import pytest


from etl.domain.repositories import IRepository
from etl.domain.repositories import ConnectionString


@pytest.fixture
def repository():
    # schema = SchemaRepositoryModel(**{"database": "test", "table": "test_sales", "columns": ["data_venda"]})
    connection_string = ConnectionString(**{"uri": "uri://connection"})
    repo = IRepository(connection_string)
    return repo
