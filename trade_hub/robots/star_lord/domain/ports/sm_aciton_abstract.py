from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..dto import TransactionStatusInputDto


class SMActionsAbstract:

    def update(
            self,
            sm: TransactionStatusInputDto,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> None:
        ...
