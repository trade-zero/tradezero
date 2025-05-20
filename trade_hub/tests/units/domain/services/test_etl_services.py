from etl.domain.enums import ETablesAvailable

from etl.domain.entities import InfoSpecEntity
from etl.domain.entities import InfoSchemaEntity
from etl.domain.entities import ContactsETLEntity

from etl.domain.usecases import ETLUseCaseAbstract
from etl.domain.repositories import ConnectionString

from tests.spy.domain.ports.prepare_abstract import ETLPrepareSpy
from tests.spy.domain.ports.extract_abstract import ETLExtractSPY
from tests.spy.domain.ports.transform_abstract import ETLTransformSpy
from tests.spy.domain.ports.load_abstract import ETLLoadSpy


def test_etl_services():
    # Arrange
    spec = InfoSpecEntity(**{"columns": "test", "where": {"start": "2023-01-01", "end": "2023-01-02"}})
    schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)
    contact = ContactsETLEntity(schema=schema, spec=spec)

    # Act
    etl = ETLUseCaseAbstract(
        connection_string_search=ConnectionString(**{"uri": "uri://connection"}),
        connection_string_persistence=ConnectionString(**{"uri": "uri://connection"}),
        connection_string_info=ConnectionString(**{"uri": "uri://connection"}),
        prepare=ETLPrepareSpy,
        extract=ETLExtractSPY,
        transform=ETLTransformSpy,
        load=ETLLoadSpy,
    )

    res = etl.run(contact=contact)

    # Assert
    assert type(contact) == type(res)
