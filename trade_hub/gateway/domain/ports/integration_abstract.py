from abc import ABC
from datetime import datetime

import numpy as np

from shared.domain import enums
from shared.domain import entities


class IntegrationAbstract(ABC):
    def initialize(self) -> bool:
        """Establish a connection with the MetaTrader 5 terminal"""

    def login(self) -> bool:
        """Connect to a trading account using specified parameters"""

    def shutdown(self) -> None:
        """Close the previously established connection to the MetaTrader 5 terminal"""

    def version(self) -> list[int, int, str]:
        """Return the MetaTrader 5 terminal version"""

    def last_error(self) -> tuple[int, str]:
        """Return data on the last error"""

    def account_info(self) -> entities.AccountInfo:
        """Get info on the current trading account"""

    def terminal_info(self) -> entities.TerminalInfo:
        """Get status and parameters of the connected MetaTrader 5 terminal"""

    def symbols_total(self) -> int:
        """Get the number of all financial instruments in the MetaTrader 5 terminal"""

    def symbols_get(self, group: str = None) -> list[entities.SymbolInfo]:
        """Get all financial instruments from the MetaTrader 5 terminal"""

    def symbol_info(self, symbol) -> entities.SymbolInfo:
        """Get data on the specified financial instrument"""

    def symbol_info_tick(self, symbol) -> entities.Tick:
        """Get the last tick for the specified financial instrument"""

    def symbol_select(self, symbol: str, enable: bool = True) -> bool:
        """Select a symbol in the MarketWatch window or remove a symbol from the window"""

    def market_book_add(self, symbol: str) -> bool:
        """Subscribes the MetaTrader 5 terminal to the Market Depth change events for a specified symbol"""

    def market_book_get(self, symbol: str) -> tuple[entities.BookInfo]:
        """Returns a tuple from BookInfo featuring Market Depth entries for the specified symbol"""

    def market_book_release(self, symbol: str) -> bool:
        """Cancels subscription of the MetaTrader 5 terminal to the Market Depth change events for a specified symbol"""

    def copy_rates_from(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # initial bar open date
            timeframe: enums.ETimeframe = enums.ETimeframe.TIMEFRAME_M1,  # timeframe
            count: int = 1_000,  # number of bars
    ) -> np.ndarray:
        """Get bars from the MetaTrader 5 terminal starting from the specified date"""

    def copy_rates_from_pos(
            self,
            symbol: str,  # symbol name
            timeframe: enums.ETimeframe = enums.ETimeframe.TIMEFRAME_M1,  # timeframe
            start_pos: int = 0,  # initial bar index
            count: int = 1_000,  # number of bars
    ) -> np.ndarray:
        """Get bars from the MetaTrader 5 terminal starting from the specified index"""

    def copy_rates_range(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # date the bars are requested from
            date_to: datetime,  # date, up to which the bars are requested
            timeframe: enums.ETimeframe = enums.ETimeframe.TIMEFRAME_M1,  # timeframe
    ) -> np.ndarray:
        """Get bars in the specified date range from the MetaTrader 5 terminal"""

    def copy_ticks_from(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # date the ticks are requested from
            *,
            count: int = 10_000,  # number of requested ticks
            flags: enums.ECopyTicks = enums.ECopyTicks.COPY_TICKS_TRADE
            # combination of flags defining the type of requested ticks
    ) -> np.ndarray:
        """Get ticks from the MetaTrader 5 terminal starting from the specified date"""

    def copy_ticks_range(
            self,
            symbol: str,  # symbol name
            date_from: datetime,  # date the ticks are requested from
            date_to: datetime,  # date, up to which the ticks are requested
            *,
            flags: enums.ECopyTicks = enums.ECopyTicks.COPY_TICKS_TRADE  # combination of flags defining the type of requested ticks
    ) -> np.ndarray:
        """Get ticks for the specified date range from the MetaTrader 5 terminal"""

    def orders_total(self) -> int:
        """Get the number of active orders."""

    def orders_get(self, symbol: str = None, group: str = None, ticket: str = None) -> tuple[entities.TradeOrder]:
        """Get active orders with the ability to filter by symbol or ticket"""

    def order_calc_margin(
            self,
            action: enums.EOrderType,  # order type (ORDER_TYPE_BUY or ORDER_TYPE_SELL)
            symbol: str,  # symbol name
            volume: float,  # volume
            price: float = None,  # open price
    ) -> float:
        """Return margin in the account currency to perform a specified trading operation"""

    def order_calc_profit(
            self,
            action: enums.EOrderType,  # order type (ORDER_TYPE_BUY or ORDER_TYPE_SELL)
            symbol: str,  # symbol name
            volume: float,  # volume
            price_open: float = None,  # open price
            price_close: float = None,  # close price
    ) -> float:
        """Return profit in the account currency for a specified trading operation"""

    def order_check(self, request: entities.TradeRequest) -> entities.TradeCheckResult:
        """Check funds sufficiency for performing a required trading operation"""

    def order_send(self, request: entities.TradeRequest) -> entities.OrderSendResult:
        """Send a request to perform a trading operation."""

    def positions_total(self):
        """Get the number of open positions"""

    def positions_get(
            self, symbol: str = None, group: str = None, ticket: int = None
    ) -> tuple[entities.TradePosition]:
        """Get open positions with the ability to filter by symbol or ticket"""

    def history_orders_total(self, date_from: datetime, date_to: datetime) -> int:
        """Get the number of orders in trading history within the specified interval"""

    def history_orders_get(
            self,
            date_from: datetime,  # date the orders are requested from
            date_to: datetime,  # date, up to which the orders are requested
            group: str,  # filter for selecting orders by symbols
            ticket: int,  # order ticket
            position: int,  # position ticket
    ) -> tuple[entities.TradeOrder]:
        """Get orders from trading history with the ability to filter by ticket or position"""

    def history_deals_total(self, date_from: datetime, date_to: datetime) -> int:
        """Get the number of deals in trading history within the specified interval"""

    def history_deals_get(
            self,
            date_from: datetime,  # date the orders are requested from
            date_to: datetime,  # date, up to which the orders are requested
            group: str,  # filter for selecting orders by symbols
            ticket: int,  # order ticket
            position: int,  # position ticket
    ) -> tuple[entities.TradeDeal]:
        """Get deals from trading history with the ability to filter by ticket or position"""
