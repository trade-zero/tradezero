from etl.domain.enums import ETablesAvailable
from etl.domain.entities import InfoSpecEntity
from etl.domain.entities import InfoSchemaEntity
from etl.domain.entities import ContactsETLEntity
from etl.domain.services import ETLPrepareAbstract

from .fixtures import repository


def test_prepare_abstract(repository):
    # Arrange
    spec = InfoSpecEntity(**{"columns": "test", "where": {"start": "2023-01-01", "end": "2023-01-02"}})
    schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)
    contact = ContactsETLEntity(schema=schema, spec=spec)

    # Act
    etl = ETLPrepareAbstract(repository)
    res = etl.handle(contact=contact)

    # Assert
    assert type(contact) == type(res)
