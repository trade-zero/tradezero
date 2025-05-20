import typing as t

from datetime import datetime

from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..entities import TradeResult
from ..entities import TradeRequest


class GatewayAbstract:

    def place_order(self, order: TradeRequest) -> TradeResult:
        ...

    def get_orders_pending(
            self,
            symbol: str = None,
            group: str = None,
            ticket: str = None
    ) -> t.Union[list[TradeOrder], TradeOrder]:
        ...

    def get_positions_open(
            self,
            symbol: str = None,
            group: str = None,
            ticket: str = None,
    ) -> t.Union[list[TradePosition], TradePosition]:
        ...

    def get_history_orders(
            self,
            date_from: datetime,
            date_to: datetime,
            symbol: str = None,
            group: str = None,
            ticket: str = None,
    ) -> TradeOrder:
        ...

    def get_deals_history(
            self,
            date_from: datetime,
            date_to: datetime,
            symbol: str = None,
            group: str = None,
            ticket: str = None,
    ) -> list[TradeDeal]:
        ...
