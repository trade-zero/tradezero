from pytest import fixture

from datetime import datetime
from datetime import timedelta

from shared.domain import enums

from gateway.infrastructure.adapters import IntegrationAdapter


@fixture
def mt5():
    return IntegrationAdapter()


@fixture
def symbol():
    return "WIN" "M23"


def test_initialize(mt5):
    x = mt5.initialize()
    assert x


def test_account_info(mt5):
    x = mt5.account_info()
    assert x.__class__.__name__ == "AccountInfo"


def test_terminal_info(mt5):
    x = mt5.terminal_info()
    assert x.__class__.__name__ == "TerminalInfo"


def test_symbols_total(mt5):
    x = mt5.symbols_total()
    assert isinstance(x, int)


def test_symbols_get_all(mt5):
    x = mt5.symbols_get()
    for i in x:
        assert i.__class__.__name__ == "SymbolInfo"


def test_symbols_get_by_group(mt5):
    x = mt5.symbols_get("*WIN*")
    for i in x:
        assert i.__class__.__name__ == "SymbolInfo"


def test_symbol_info(mt5, symbol):
    mt5.symbol_select(symbol)
    x = mt5.symbol_info(symbol=symbol)
    assert x.__class__.__name__ == "SymbolInfo"


def test_symbol_info_tick(mt5, symbol):
    x = mt5.symbol_info_tick(symbol)
    assert x.__class__.__name__ == "Tick"


def test_market_book(mt5, symbol):
    mt5.market_book_add(symbol)
    items = mt5.market_book_get(symbol)

    if items:
        for it in items:
            assert it.__class__.__name__ == "BookInfo"

    mt5.market_book_release(symbol)


def test_copy_rates_from(mt5, symbol):
    date_from = datetime.now() + timedelta(days=-3)
    x = mt5.copy_rates_from(symbol, date_from)
    assert x.__class__.__name__ == "ndarray"


def test_copy_rates_from_pos(mt5, symbol):
    x = mt5.copy_rates_from_pos(symbol)
    assert x.__class__.__name__ == "ndarray"


def test_copy_rates_range(mt5, symbol):
    date_to = datetime.now()
    date_from = date_to + timedelta(days=-3)
    x = mt5.copy_rates_range(symbol, date_from, date_to)
    assert x.__class__.__name__ == "ndarray"


def test_copy_ticks_from(mt5, symbol):
    date_from = datetime.now() + timedelta(days=-3)
    x = mt5.copy_ticks_from(symbol, date_from)
    assert x.__class__.__name__ == "ndarray"


def test_copy_ticks_range(mt5, symbol):
    date_to = datetime.now()
    date_from = date_to + timedelta(days=-3)
    x = mt5.copy_ticks_range(symbol, date_from, date_to)
    assert x.__class__.__name__ == "ndarray"


def test_orders_total(mt5):
    x = mt5.orders_total()
    assert isinstance(x, int)


def test_orders_get(mt5, symbol):
    x = mt5.orders_get(symbol=symbol)
    for i in x:
        assert i.__class__.__name__ == "TradeOrder"


def test_order_calc_margin(mt5, symbol):
    x = mt5.order_calc_margin(enums.EOrderType.ORDER_TYPE_BUY, symbol, 1.0)
    assert isinstance(x, float)


def test_order_calc_profit(mt5, symbol):
    x = mt5.order_calc_profit(enums.EOrderType.ORDER_TYPE_BUY, symbol, 2.0, 113_000, 114_000)
    assert isinstance(x, float)


def test_order_check(mt5, symbol):
    # TODO: implement order check function
    ...


def test_order_send(mt5, symbol):
    order = {
        "action": enums.ETradeActions.TRADE_ACTION_PENDING.value,
        "symbol": symbol,
        "volume": 2.0,
        "type": enums.EOrderType.ORDER_TYPE_BUY_LIMIT.value,
        "type_time": enums.EOrderTime.ORDER_TIME_DAY.value,
        "type_filling": enums.EOrderFilling.ORDER_FILLING_RETURN.value,
        "price": 100_000.0,
        "sl": 0.0,
        "tp": 0.0,
        "deviation": 10,
        "comment": "",
        "magic": 12345,
    }
    # x = mt5.order_send(order)
    # assert x.__class__.__name__ == "OrderSendResult"


def test_positions_total(mt5):
    x = mt5.positions_total()
    assert isinstance(x, int)


def test_positions_get(mt5):
    x = mt5.positions_get(ticket=1561726116)
    for i in x:
        assert i.__class__.__name__ == "TradePosition"


def test_history_orders_total(mt5):
    date_to = datetime.now()
    date_from = date_to + timedelta(days=-1)
    x = mt5.history_orders_total(date_from, date_to)
    assert isinstance(x, int)


def test_history_orders_get(mt5, symbol):
    date_to = datetime.now()
    date_from = date_to + timedelta(days=-1)
    x = mt5.history_orders_get(date_from=date_from, date_to=date_to, group=f"*{symbol}*")

    for i in x:
        assert i.__class__.__name__ == "TradeOrder"


def test_history_deals_total(mt5):
    date_to = datetime.now()
    date_from = date_to + timedelta(days=-1)
    x = mt5.history_deals_total(date_from=date_from, date_to=date_to)
    assert isinstance(x, int)


def test_history_deals_get(mt5, symbol):
    date_to = datetime.now()
    date_from = date_to + timedelta(days=-1)
    x = mt5.history_deals_get(date_from=date_from, date_to=date_to, group=f"*{symbol}*")

    for i in x:
        assert i.__class__.__name__ == "TradeDeal"
