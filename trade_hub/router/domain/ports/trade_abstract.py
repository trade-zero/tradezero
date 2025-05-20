import typing as t

from datetime import datetime

from shared.domain.enums import EOrderTime

from ..entities import TradeRequest


class TradeAbstract:

    def buy(
            self,
            volume: float,
            symbol: str,
            *,
            price: t.Optional[float] = None,
            stop_loss: t.Optional[float] = None,
            take_profit: t.Optional[float] = None,
            comment: str = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None,
    ) -> TradeRequest:
        ...

    def buy_limit(
            self,
            volume: float,
            price: float,
            symbol: str,
            *,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            expiration: t.Union[datetime, int] = 0,
            comment: t.Optional[str] = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None
    ) -> TradeRequest:
        ...

    def buy_stop(
            self,
            volume: float,
            price: float,
            symbol: str,
            *,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            expiration: t.Union[datetime, int] = 0,
            comment: t.Optional[str] = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None
    ) -> TradeRequest:
        ...

    def sell(
            self,
            volume: float,
            symbol: str,
            *,
            price: t.Optional[float] = None,
            stop_loss: t.Optional[float] = None,
            take_profit: t.Optional[float] = None,
            comment: str = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None,
    ) -> TradeRequest:
        ...

    def sell_limit(
            self,
            volume: float,
            price: float,
            symbol: str,
            *,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            expiration: t.Union[datetime, int] = 0,
            comment: t.Optional[str] = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None
    ) -> TradeRequest:
        ...

    def sell_stop(
            self,
            volume: float,
            price: float,
            symbol: str,
            *,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            expiration: t.Union[datetime, int] = 0,
            comment: t.Optional[str] = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None
    ) -> TradeRequest:
        ...

    def order_delete(self, ticket: int, comment: str = ""):
        ...

    def order_modify(
            self,
            ticket: int,
            *,
            price: t.Optional[float] = 0.0,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            stop_limit: t.Optional[float] = 0.0,
            type_time: EOrderTime = EOrderTime.ORDER_TIME_GTC,
            expiration: t.Union[datetime, int] = 0,
            comment: t.Optional[str] = "",
    ) -> TradeRequest:
        ...

    def position_close(
            self,
            ticket: int,
            *,
            deviation: t.Optional[float] = -1,
    ) -> TradeRequest:
        ...

    def position_close_by(
            self,
            ticket: int,
            ticket_by: int,
    ) -> TradeRequest:
        ...

    def position_close_partial(
            self,
            ticket: int,
            volume: float,
            *,
            deviation: t.Optional[float] = -1,
    ) -> TradeRequest:
        ...

    def position_modify(
            self,
            symbol: str,
            ticket: int,
            *,
            stop_loss: t.Optional[float] = None,
            take_profit: t.Optional[float] = None,
            magic_number: int = 0,
    ) -> TradeRequest:
        ...
