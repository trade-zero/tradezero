from datetime import datetime

from etl.domain.enums.info_stages import EInfoStages
from etl.domain.enums.info_status import EInfoStatus

from etl.domain.entities.info_stages_entity import InfoStagesEntity


def test_etl_contacts_entity_without_data():
    # Arrange
    stage = EInfoStages.PREPARE
    status = EInfoStatus.STARTED

    # Act
    entity = InfoStagesEntity(stage=stage, status=status)

    # Assert
    assert isinstance(entity.stage, EInfoStages)
    assert isinstance(entity.status, EInfoStatus)
    assert isinstance(entity.updated_at, datetime)
