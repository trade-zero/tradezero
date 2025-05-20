from enum import Enum


class ESymbolOrders(Enum):
    SYMBOL_ORDERS_GTC = 0
    SYMBOL_ORDERS_DAILY = 1
    SYMBOL_ORDERS_DAILY_NO_STOPS = 2
