from datetime import datetime

from etl.domain.enums.tables_available import ETablesAvailable
from etl.domain.entities.info_spec_entity import InfoSpecEntity
from etl.domain.entities.info_schema_entity import InfoSchemaEntity
from etl.domain.entities.contacts_etl_entity import ContactsETLEntity


def test_etl_contacts_entity_without_data():
    # Arrange
    info_spec = InfoSpecEntity()
    info_schema = InfoSchemaEntity(name="test", database="test", table=ETablesAvailable.V_ZETTI_VENDAS)

    # Act
    entity = ContactsETLEntity(spec=info_spec, schema=info_schema)

    # Assert
    assert isinstance(entity.data, list)
    assert isinstance(entity.progress, list)
    assert isinstance(entity.extract_at, datetime)
