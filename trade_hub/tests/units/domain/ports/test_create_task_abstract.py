from datetime import datetime
from datetime import timedelta

from etl.domain.enums import ETablesAvailable
from etl.domain.services import CreateTaskAbstract
from etl.domain.entities import InfoSchemaEntity
from etl.domain.entities import ScheduledTaskEntity


def test_create_task_abstract():
    # Arrange
    start = datetime.fromisoformat("2020-01-01 00:00:00")
    delta = timedelta(days=7)
    schema = InfoSchemaEntity(
        name="test",
        database="test",
        table=ETablesAvailable.V_ZETTI_VENDAS
    )

    # Act
    create_task = CreateTaskAbstract()
    tasks = create_task(start, delta, schema)

    # Assert
    assert all([*map(lambda doc: isinstance(doc, ScheduledTaskEntity), tasks)])
