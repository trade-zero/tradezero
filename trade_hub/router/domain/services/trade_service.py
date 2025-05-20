import typing as t
from datetime import datetime

from gateway import mt5

from shared.domain.enums import EOrderTime
from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderFilling
from shared.domain.enums import EPositionType
from shared.domain.enums import ETradeActions
from shared.domain.enums import ETradeRetCode

from ..ports import TradeAbstract
from ..ports import GatewayAbstract
from ..ports import FormatterSendOrderAbstract

from ..entities import TradeResult
from ..entities import TradeRequest
from ..entities import TradeTransaction


class TradeService(TradeAbstract):
    def __init__(
            self,
            gateway: type[GatewayAbstract] = GatewayAbstract,
            formatter: type[FormatterSendOrderAbstract] = FormatterSendOrderAbstract
    ):
        self.formatter = formatter()
        self.gateway = gateway()

    def buy(
            self, volume: float,
            symbol: str,
            *,
            price: t.Optional[float] = 0.0,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            comment: t.Optional[str] = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None,
    ) -> TradeTransaction:

        request = TradeRequest(
            action_type=ETradeActions.TRADE_ACTION_DEAL,
            volume=volume,
            symbol=symbol,
            price=price,
            stop_loss=stop_loss,
            take_profit=take_profit,
            deviation=deviation,
            type_type=EOrderType.ORDER_TYPE_BUY,
            type_time_type=EOrderTime.ORDER_TIME_GTC,
            type_filling_type=EOrderFilling.ORDER_FILLING_RETURN,
            comment=comment,
            magic_number=magic_number,
            position=position,
        )

        last_tick = mt5.symbol_info_tick(symbol)

        if request.price <= 0.0:
            request.price = last_tick.ask

        if abs(last_tick.ask - request.price) > deviation:
            result = TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_INVALID_PRICE)
            return TradeTransaction(request=request, result=result)

        formatted_order = self.formatter.position_open(r=request)
        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return TradeTransaction(request=request, result=result)

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
    ) -> TradeTransaction:

        request = TradeRequest(
            action_type=ETradeActions.TRADE_ACTION_PENDING,
            volume=volume,
            symbol=symbol,
            price=price,
            stop_loss=stop_loss,
            take_profit=take_profit,
            deviation=deviation,
            expiration=expiration,
            type_type=EOrderType.ORDER_TYPE_BUY_LIMIT,
            type_time_type=EOrderTime.ORDER_TIME_GTC,
            type_filling_type=EOrderFilling.ORDER_FILLING_RETURN,
            comment=comment,
            magic_number=magic_number,
            position=position,
        )

        last_tick = mt5.symbol_info_tick(symbol)

        if request.price <= 0.0:
            request.price = last_tick.ask

        if abs(last_tick.ask - request.price) <= deviation:
            result = TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_INVALID_PRICE)
            return TradeTransaction(request=request, result=result)

        formatted_order = self.formatter.order_open(r=request)
        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return TradeTransaction(request=request, result=result)

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
    ) -> TradeTransaction:

        request = TradeRequest(
            volume=volume,
            symbol=symbol,
            price=price,
            take_profit=take_profit,
            stop_loss=stop_loss,
            deviation=deviation,
            expiration=expiration,
            action_type=ETradeActions.TRADE_ACTION_PENDING,
            type_type=EOrderType.ORDER_TYPE_BUY_STOP,
            type_time_type=EOrderTime.ORDER_TIME_GTC,
            type_filling_type=EOrderFilling.ORDER_FILLING_RETURN,
            comment=comment,
            magic_number=magic_number,
            position=position,
        )

        last_tick = mt5.symbol_info_tick(symbol)

        if (request.price - last_tick.bid) <= deviation:
            result = TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_INVALID_PRICE)
            return TradeTransaction(request=request, result=result)

        formatted_order = self.formatter.order_open(r=request)
        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return TradeTransaction(request=request, result=result)

    def sell(
            self,
            volume: float,
            symbol: str,
            *,
            price: t.Optional[float] = 0.0,
            stop_loss: t.Optional[float] = 0.0,
            take_profit: t.Optional[float] = 0.0,
            comment: t.Optional[str] = "",
            magic_number: t.Optional[int] = 0,
            deviation: t.Optional[int] = 10,
            position: t.Optional[int] = None,
    ) -> TradeTransaction:

        request = TradeRequest(
            volume=volume,
            symbol=symbol,
            price=price,
            stop_loss=stop_loss,
            take_profit=take_profit,
            deviation=deviation,
            action_type=ETradeActions.TRADE_ACTION_DEAL,
            type_type=EOrderType.ORDER_TYPE_SELL,
            type_time_type=EOrderTime.ORDER_TIME_GTC,
            type_filling_type=EOrderFilling.ORDER_FILLING_RETURN,
            comment=comment,
            magic_number=magic_number,
            position=position,
        )

        last_tick = mt5.symbol_info_tick(symbol)

        if request.price == 0.0:
            request.price = last_tick.ask

        if abs(last_tick.ask - request.price) > deviation:
            result = TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_INVALID_PRICE)
            return TradeTransaction(request=request, result=result)

        formatted_order = self.formatter.position_open(r=request)
        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return TradeTransaction(request=request, result=result)

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
    ) -> TradeTransaction:

        request = TradeRequest(
            action_type=ETradeActions.TRADE_ACTION_PENDING,
            volume=volume,
            symbol=symbol,
            price=price,
            stop_loss=stop_loss,
            take_profit=take_profit,
            deviation=deviation,
            expiration=expiration,
            type_type=EOrderType.ORDER_TYPE_SELL_LIMIT,
            type_time_type=EOrderTime.ORDER_TIME_GTC,
            type_filling_type=EOrderFilling.ORDER_FILLING_RETURN,
            comment=comment,
            magic_number=magic_number,
            position=position
        )

        last_tick = mt5.symbol_info_tick(symbol)

        if (request.price - last_tick.bid) <= deviation:
            result = TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_INVALID_PRICE)
            return TradeTransaction(request=request, result=result)

        formatted_order = self.formatter.order_open(r=request)
        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return TradeTransaction(request=request, result=result)

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

    ) -> TradeTransaction:

        request = TradeRequest(
            volume=volume,
            symbol=symbol,
            price=price,
            stop_loss=stop_loss,
            take_profit=take_profit,
            expiration=expiration,
            deviation=deviation,
            action_type=ETradeActions.TRADE_ACTION_PENDING,
            type_type=EOrderType.ORDER_TYPE_SELL_STOP,
            type_filling_type=EOrderFilling.ORDER_FILLING_RETURN,
            type_time_type=EOrderTime.ORDER_TIME_GTC,
            comment=comment,
            position=position,
            magic_number=magic_number,
        )

        last_tick = mt5.symbol_info_tick(symbol)

        if (last_tick.ask - request.price) <= deviation:
            result = TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_INVALID_PRICE)
            return TradeTransaction(request=request, result=result)

        formatted_order = self.formatter.order_open(r=request)
        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return TradeTransaction(request=request, result=result)

    def order_delete(self, ticket: int, comment: str = ""):
        formatter_order = self.formatter.order_close(
            action_type=ETradeActions.TRADE_ACTION_REMOVE,
            ticket=ticket,
            comment=comment,
        )
        print(f"{datetime.now()}:{mt5.last_error()}")
        return self.gateway.place_order(formatter_order)

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
    ) -> TradeResult:
        diff = 0.0
        orders = mt5.orders_get(ticket=ticket)

        if orders is None:
            return TradeResult(return_code=ETradeRetCode.TRADE_RETCODE_ERROR)

        order, *_ = orders

        if price > 0.0:
            diff = (price - order.price_open)

        if price == 0.0:
            price = order.price_open

        if stop_loss == 0.0 and order.sl > 0.0:
            stop_loss = order.sl + diff

        if take_profit == 0.0 and order.tp > 0.0:
            take_profit = order.tp + diff

        formatted_order = self.formatter.order_modify(
            symbol=order.symbol,
            ticket=ticket,
            action_type=ETradeActions.TRADE_ACTION_MODIFY,
            price=price,
            stop_loss=stop_loss,
            take_profit=take_profit,
            stop_limit=stop_limit,
            type_time=type_time,
            expiration=expiration,
            comment=comment,
        )

        checked_order = mt5.order_check(formatted_order)
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return result

    def position_close(
            self,
            ticket: int,
            *,
            deviation: int = 10
    ) -> TradeTransaction:

        position, *_ = mt5.positions_get(ticket=ticket)

        # for position in positions:
        if position.type == EPositionType.POSITION_TYPE_BUY.value:
            return self.sell(
                volume=position.volume,
                symbol=position.symbol,
                position=position.identifier,
            )
        else:
            return self.buy(
                volume=position.volume,
                symbol=position.symbol,
                position=position.identifier,
            )

    def position_close_by(
            self,
            ticket: int,
            ticket_by: int
    ) -> TradeResult:
        mt5.connect()
        position1 = mt5.positions_get(ticket=ticket)
        position2 = mt5.positions_get(ticket=ticket_by)
        formatted_order = {
            "action": ETradeActions.TRADE_ACTION_CLOSE_BY.value,
            "position": ticket,
            "ticket_by": ticket_by,
            "magic": position1[0].magic,
        }
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return result

    def position_close_partial(
            self,
            ticket: int,
            volume: float,
            *,
            deviation: int = 10
    ) -> TradeRequest:
        ...

    def position_modify(
            self,
            symbol: str,
            ticket: int,
            *,
            stop_loss: float = 0.0,
            take_profit: float = 0.0,
            magic_number: int = 0,
    ) -> TradeResult:

        formatted_order = self.formatter.position_modify(
            action_type=ETradeActions.TRADE_ACTION_SLTP,
            symbol=symbol,
            stop_loss=stop_loss,
            take_profit=take_profit,
            ticket=ticket,
            magic_number=magic_number,
        )
        result = self.gateway.place_order(formatted_order)
        print(f"{datetime.now()}:{mt5.last_error()}")
        return result
