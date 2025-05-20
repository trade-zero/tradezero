from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..dto import TransactionStatusInputDto
from ..enums import ETransactionStatus
from ..entities import SettingsInputs


class SMTransactionAbstract:
    settings: SettingsInputs
    transaction_status: ETransactionStatus
    total_positions: int
    total_orders: int
    check_orders: bool
    check_positions: bool

    def from_buffer(
            self,
            sm: TransactionStatusInputDto,
            orders: list[TradeOrder],
            positions: list[TradePosition]
    ) -> TransactionStatusInputDto:
        ...
