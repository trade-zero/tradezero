from shared.domain.enums import ETradeActions
from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderTime

from ..entities import TradeRequest


class FormatterSendOrderAbstract:

    def position_open(self, r: TradeRequest) -> dict:
        ...

    def position_modify(
            self,
            action_type: ETradeActions,
            symbol: str,
            stop_loss: float,
            take_profit: float,
            ticket: int,
            magic_number: int,
    ) -> dict:
        ...

    def position_close(
            self,
            action_type: ETradeActions,
            price: float,
            type_type: EOrderType,
            ticket: int,
            symbol: str,
            volume: float,
            magic_number: int,
            deviation: int,
    ):
        ...

    def order_open(self, r: TradeRequest) -> dict:
        ...

    def order_modify(
            self,
            symbol: str,
            ticket: int,
            action_type: ETradeActions,
            price: float,
            stop_loss: float,
            take_profit: float,
            stop_limit: float,
            type_time: EOrderTime,
            expiration: int,
            comment: str,
    ):
        ...

    def order_close(
            self,
            action_type: ETradeActions,
            ticket: int,
            comment: str,
    ):
        ...
