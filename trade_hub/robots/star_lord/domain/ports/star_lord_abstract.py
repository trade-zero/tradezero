from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..dto import TransactionStatusInputDto
from ..entities import SettingsInputs
from ..ports import SMActionsAbstract
from ..ports import SMTransactionAbstract


class StarLordAbstract:
    actions: SMActionsAbstract
    transactions: SMTransactionAbstract

    def on_init(
            self,
            settings: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> TransactionStatusInputDto:
        ...

    def trade_timer(
            self,
            inner_transaction: TransactionStatusInputDto,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> TransactionStatusInputDto:
        ...

    def trade_transaction(self, orders: list[TradeOrder], positions: list[TradePosition]) -> None:
        ...
