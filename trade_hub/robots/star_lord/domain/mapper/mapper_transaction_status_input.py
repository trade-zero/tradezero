from ..dto import TransactionStatusInputDto
from ..entities import SettingsInputs


def from_transaction_to_settings(dto: TransactionStatusInputDto) -> SettingsInputs:
    return SettingsInputs(
        globals=dto.setting.globals,
        time=dto.setting.time,
        position=dto.setting.position,
        hedge=dto.setting.hedge,
        manager=dto.setting.manager,
        info=dto.setting.info,
    )


def from_settings_to_transaction(entity: SettingsInputs) -> TransactionStatusInputDto:
    return TransactionStatusInputDto(
        setting=entity,
    )
