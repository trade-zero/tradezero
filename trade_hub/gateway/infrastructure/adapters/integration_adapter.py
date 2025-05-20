from datetime import datetime

import MetaTrader5 as mt5
import numpy as np

from shared.domain import enums
from shared.domain import entities

from ...domain.ports.integration_abstract import IntegrationAbstract


class IntegrationAdapter(IntegrationAbstract):
    def initialize(self) -> bool:
        return mt5.initialize()

    def login(self) -> bool:
        return mt5.login()

    def shutdown(self) -> None:
        return mt5.shutdown()

    def version(self) -> tuple[int, int, str]:
        return mt5.version()

    def last_error(self) -> tuple[int, str]:
        return mt5.last_error()

    def account_info(self) -> entities.AccountInfo:
        return mt5.account_info()

    def terminal_info(self) -> entities.TerminalInfo:
        return mt5.terminal_info()

    def symbols_total(self) -> int:
        return mt5.symbols_total()

    def symbols_get(self, group: str = None) -> list[entities.SymbolInfo]:
        if group:
            return mt5.symbols_get(group=group)
        return mt5.symbols_get()

    def symbol_info(self, symbol) -> entities.SymbolInfo:
        return mt5.symbol_info(symbol)

    def symbol_info_tick(self, symbol) -> entities.Tick:
        return mt5.symbol_info_tick(symbol)

    def symbol_select(self, symbol: str, enable: bool = True) -> bool:
        return mt5.symbol_select(symbol, enable)

    def market_book_add(self, symbol: str) -> bool:
        return mt5.market_book_add(symbol)

    def market_book_get(self, symbol: str) -> tuple[entities.BookInfo]:
        return mt5.market_book_get(symbol)

    def market_book_release(self, symbol: str) -> bool:
        return mt5.market_book_release(symbol)

    def copy_rates_from(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # initial bar open date
            timeframe: enums.ETimeframe = enums.ETimeframe.TIMEFRAME_M1,  # timeframe
            count: int = 1_000,  # number of bars
    ) -> np.ndarray:
        return mt5.copy_rates_from(symbol, timeframe.value, date_from, count)

    def copy_rates_from_pos(
            self,
            symbol: str,  # symbol name
            timeframe: enums.ETimeframe = enums.ETimeframe.TIMEFRAME_M1,  # timeframe
            start_pos: int = 0,  # initial bar index
            count: int = 1_000,  # number of bars
    ) -> np.ndarray:
        return mt5.copy_rates_from_pos(symbol, timeframe.value, start_pos, count)

    def copy_rates_range(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # date the bars are requested from
            date_to: datetime,  # date, up to which the bars are requested
            timeframe: enums.ETimeframe = enums.ETimeframe.TIMEFRAME_M1,  # timeframe
    ) -> np.ndarray:
        return mt5.copy_rates_range(symbol, timeframe.value, date_from, date_to)

    def copy_ticks_from(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # date the ticks are requested from
            *,
            count: int = 10_000,  # number of requested ticks
            flags: enums.ECopyTicks = enums.ECopyTicks.COPY_TICKS_TRADE  # combination of flags defining the type of requested ticks
    ) -> np.ndarray:
        return mt5.copy_ticks_from(symbol, date_from, count, flags.value)

    def copy_ticks_range(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # date the ticks are requested from
            date_to: datetime,  # date, up to which the ticks are requested
            *,
            flags: enums.ECopyTicks = enums.ECopyTicks.COPY_TICKS_TRADE  # combination of flags defining the type of requested ticks
    ) -> np.ndarray:
        return mt5.copy_ticks_range(symbol, date_from, date_to, flags.value)

    def orders_total(self) -> int:
        return mt5.orders_total()

    def orders_get(self, *args, **kwargs) -> tuple[entities.TradeOrder]:

        kwargs = {k: v for k, v in kwargs.items() if v}
        if len(args) + len(kwargs) == 0:
            return mt5.orders_get()
        else:
            if len(args) + len(kwargs) == 1:
                return mt5.orders_get(*args, **kwargs)

        raise ValueError("Must be one parament 'symbol or group or ticket'")

    def order_calc_margin(
            self,
            action: enums.EOrderType,  # order type (ORDER_TYPE_BUY or ORDER_TYPE_SELL)
            symbol: str,  # symbol name
            volume: float,  # volume
            price: float = None,  # open price
    ) -> float:

        if action not in [enums.EOrderType.ORDER_TYPE_SELL, enums.EOrderType.ORDER_TYPE_BUY] and not price:
            raise ValueError("the price must be a valid value")

        if not price:
            if action == enums.EOrderType.ORDER_TYPE_BUY:
                price = self.symbol_info_tick(symbol).ask
            elif action == enums.EOrderType.ORDER_TYPE_SELL:
                price = self.symbol_info_tick(symbol).bid

        return mt5.order_calc_margin(action.value, symbol, volume, price)

    def order_calc_profit(
            self,
            action: enums.EOrderType,  # order type (ORDER_TYPE_BUY or ORDER_TYPE_SELL)
            symbol: str,  # symbol name
            volume: float,  # volume
            price_open: float = None,  # open price
            price_close: float = None,  # close price
    ) -> float:

        if action not in [enums.EOrderType.ORDER_TYPE_SELL, enums.EOrderType.ORDER_TYPE_BUY]:
            raise ValueError("the price must be a valid value")

        return mt5.order_calc_profit(action.value, symbol, volume, price_open, price_close)

    def order_check(self, request: entities.TradeRequest) -> entities.TradeCheckResult:
        # return mt5.order_check(request)
        return NotImplemented

    def order_send(self, request: entities.TradeRequest) -> entities.OrderSendResult:
        return mt5.order_send(request)

    def positions_total(self) -> int:
        return mt5.positions_total()

    def positions_get(self, *args, **kwargs) -> tuple[entities.TradePosition]:

        if len(args) + len(kwargs) == 0:
            return mt5.positions_get()

        elif len(args) + len(kwargs) == 1:
            return mt5.positions_get(*args, **kwargs)

        raise ValueError("Must be one parament 'symbol or group or ticket'")

    def history_orders_total(self, date_from: datetime, date_to: datetime) -> int:
        return mt5.history_orders_total(date_from, date_to)

    def history_orders_get(self, date_from: datetime, date_to: datetime, *args, **kwargs) -> tuple[entities.TradeOrder]:

        kwargs = {k: v for k, v in kwargs.items() if v}
        if len(args) + len(kwargs) == 0:
            return mt5.history_orders_get(date_from, date_to)
        elif len(args) + len(kwargs) == 1:
            return mt5.history_orders_get(date_from, date_to, *args, **kwargs)

        raise ValueError("Must be one parament 'group or ticket or position'")

    def history_deals_total(self, date_from: datetime, date_to: datetime) -> int:
        return mt5.history_deals_total(date_from, date_to)

    def history_deals_get(self, date_from: datetime, date_to: datetime, *args, **kwargs) -> tuple[entities.TradeDeal]:

        kwargs = {k: v for k, v in kwargs.items() if v}
        if len(args) + len(kwargs) == 0:
            return mt5.history_deals_get(date_from, date_to)
        elif len(args) + len(kwargs) == 1:
            return mt5.history_deals_get(date_from, date_to, *args, **kwargs)

        raise ValueError("Must be one parament 'group or ticket or position'")
