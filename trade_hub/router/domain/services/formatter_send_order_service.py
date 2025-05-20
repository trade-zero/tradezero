import typing as t

from datetime import datetime

from shared.domain.enums import ETradeActions
from shared.domain.enums import EOrderType
from shared.domain.enums import EOrderTime
from shared.domain.enums import EOrderFilling

from shared.utils import Singleton

from ..entities import TradeRequest
from ..ports import FormatterSendOrderAbstract


class FormatterSendOrderService(FormatterSendOrderAbstract, metaclass=Singleton):

    def position_open(self, r: TradeRequest) -> dict:

        if r.volume <= 0.0:
            raise ValueError("volume must be greater than zero")

        if r.price <= 0.0:
            raise ValueError("price must be greater than zero")

        if not (r.type_type == EOrderType.ORDER_TYPE_BUY or r.type_type == EOrderType.ORDER_TYPE_SELL):
            raise ValueError(f"Type must be typeof {EOrderType=}")

        order = {
            "action": r.action_type.value,
            "symbol": r.symbol,
            "volume": r.volume,
            "type": r.type_type.value,
            "type_time": r.type_time_type.value,
            "type_filling": r.type_filling_type.value,
            "price": r.price,
            "sl": r.stop_loss,
            "tp": r.take_profit,
            "deviation": r.deviation,
            "comment": r.comment,
            "magic": r.magic_number,
        }

        if r.position:
            order.update({"position": r.position})

        return order

    def position_modify(
            self,
            action_type: ETradeActions,
            symbol: str,
            stop_loss: float,
            take_profit: float,
            ticket: int,
            magic_number: int,
    ) -> dict:

        if not isinstance(stop_loss, float):
            stop_loss = float(stop_loss)

        if not isinstance(take_profit, float):
            take_profit = float(take_profit)
        order = {
            "action": action_type.value,
            "symbol": symbol,
            "sl": stop_loss,
            "tp": take_profit,
            "magic": magic_number,
            "position": ticket,
        }

        return order

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

        order = {
            "action": action_type.value,
            "type": type_type.value,
            "symbol": symbol,
            "price": price,
            "volume": volume,
            "deviation": deviation,
            "magic": magic_number,
            "position": ticket,
        }

        return order

    def order_open(self, r: TradeRequest) -> dict:

        if r.volume <= 0.0:
            raise ValueError("volume must be greater than zero")

        if r.price <= 0.0:
            raise ValueError("price must be greater than zero")

        if r.type_type == EOrderType.ORDER_TYPE_BUY or r.type_type == EOrderType.ORDER_TYPE_SELL:
            raise ValueError(f"Type must be typeof {EOrderType=}")

        if r.type_time_type == EOrderTime.ORDER_TIME_GTC and r.expiration == 0:
            r.type_time_type = EOrderTime.ORDER_TIME_DAY

        order = {
            "action": r.action_type.value,
            "symbol": r.symbol,
            "volume": r.volume,
            "type": r.type_type.value,
            "type_time": r.type_time_type.value,
            "type_filling": r.type_filling_type.value,
            "price": r.price,
            "sl": r.stop_loss,
            "tp": r.take_profit,
            "deviation": r.deviation,
            "comment": r.comment,
            "magic": r.magic_number,
        }

        if r.position:
            order += {"position", r.position}

        if r.expiration != 0:
            order += {"expiration": r.expiration}

        return order

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

        if type_time == EOrderTime.ORDER_TIME_GTC and expiration == 0:
            type_time = EOrderTime.ORDER_TIME_DAY

        order = {
            "symbol": symbol,
            "action": action_type.value,
            "order": ticket,
            "price": price,
            "sl": stop_loss,
            "tp": take_profit,
            "stoplimit": stop_limit,
            "comment": comment,
            # "type_time": type_time,
            # "expiration": expiration,
        }

        return order

    def order_close(
            self,
            action_type: ETradeActions,
            ticket: int,
            comment: str,
    ):

        if action_type != ETradeActions.TRADE_ACTION_REMOVE:
            raise ValueError(f"Action must be {ETradeActions=}")

        return {
            "action": action_type.value,
            "order": ticket,
            "comment": comment,
        }
