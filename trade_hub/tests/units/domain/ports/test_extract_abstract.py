from etl.domain.enums import ETablesAvailable
from etl.domain.entities import InfoSpecEntity
from etl.domain.entities import InfoSchemaEntity
from etl.domain.entities import ContactsETLEntity
from etl.domain.services import ETLExtractAbstract

from .fixtures import repository
from tests.spy.domain.ports.prepare_abstract import ETLPrepareSpy


def test_extract_abstract(repository):
    # Arrange
    spec = InfoSpecEntity(**{"columns": "test", "where": {"start": "2023-01-01", "end": "2023-01-02"}})
    schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)
    contact = ContactsETLEntity(schema=schema, spec=spec)

    prepare = ETLPrepareSpy(repo=repository)
    contact = prepare.handle(contact=contact)

    # Act
    extract = ETLExtractAbstract(repo=repository)
    res = extract.handle(contact=contact)

    # Assert
    assert type(contact) == type(res)
