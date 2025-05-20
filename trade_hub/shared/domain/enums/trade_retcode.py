from enum import Enum


class ETradeRetCode(Enum):
    TRADE_RETCODE_REQUOTE = 10004  # Requote
    TRADE_RETCODE_REJECT = 10006  # Request rejected
    TRADE_RETCODE_CANCEL = 10007  # Request canceled by trader
    TRADE_RETCODE_PLACED = 10008  # Order placed
    TRADE_RETCODE_DONE = 10009  # Request completed
    TRADE_RETCODE_DONE_PARTIAL = 10010  # Only part of the request was completed
    TRADE_RETCODE_ERROR = 10011  # Request processing error
    TRADE_RETCODE_TIMEOUT = 10012  # Request canceled by timeout
    TRADE_RETCODE_INVALID = 10013  # Invalid request
    TRADE_RETCODE_INVALID_VOLUME = 10014  # Invalid volume in the request
    TRADE_RETCODE_INVALID_PRICE = 10015  # Invalid price in the request
    TRADE_RETCODE_INVALID_STOPS = 10016  # Invalid stops in the request
    TRADE_RETCODE_TRADE_DISABLED = 10017  # Trade is disabled
    TRADE_RETCODE_MARKET_CLOSED = 10018  # Market is closed
    TRADE_RETCODE_NO_MONEY = 10019  # There is not enough money to complete the request
    TRADE_RETCODE_PRICE_CHANGED = 10020  # Prices changed
    TRADE_RETCODE_PRICE_OFF = 10021  # There are no quotes to process the request
    TRADE_RETCODE_INVALID_EXPIRATION = 10022  # Invalid order expiration date in the request
    TRADE_RETCODE_ORDER_CHANGED = 10023  # Order state changed
    TRADE_RETCODE_TOO_MANY_REQUESTS = 10024  # Too frequent requests
    TRADE_RETCODE_NO_CHANGES = 10025  # No changes in request
    TRADE_RETCODE_SERVER_DISABLES_AT = 10026  # Autotrading disabled by server
    TRADE_RETCODE_CLIENT_DISABLES_AT = 10027  # Autotrading disabled by client terminal
    TRADE_RETCODE_LOCKED = 10028  # Request locked for processing
    TRADE_RETCODE_FROZEN = 10029  # Order or position frozen
    TRADE_RETCODE_INVALID_FILL = 10030  # Invalid order filling type
    TRADE_RETCODE_CONNECTION = 10031  # No connection with the trade server
    TRADE_RETCODE_ONLY_REAL = 10032  # Operation is allowed only for live accounts
    TRADE_RETCODE_LIMIT_ORDERS = 10033  # The number of pending orders has reached the limit
    TRADE_RETCODE_LIMIT_VOLUME = 10034  # The volume of orders and positions for the symbol has reached the limit
    TRADE_RETCODE_INVALID_ORDER = 10035  # Incorrect or prohibited order type
    TRADE_RETCODE_POSITION_CLOSED = 10036  # Position with the specified POSITION_IDENTIFIER has already been closed
    TRADE_RETCODE_INVALID_CLOSE_VOLUME = 10038  # A close volume exceeds the current position volume
    TRADE_RETCODE_CLOSE_ORDER_EXIST = 10039 # A close order already exists for a specified position. This may happen when working in the hedging system: i) when attempting to close a position with an opposite one, while close orders for the position already exist; ii) when attempting to fully or partially close a position if the total volume of the already present close orders and the newly placed one exceeds the current position volume;
    TRADE_RETCODE_LIMIT_POSITIONS = 10040  # The number of open positions simultaneously present on an account can be limited by the server settings. After a limit is reached, the server returns the TRADE_RETCODE_LIMIT_POSITIONS error when attempting to place an order. The limitation operates differently depending on the position accounting type: Netting — number of open positions is considered. When a limit is reached, the platform does not let placing new orders whose execution may increase the number of open positions. In fact, the platform allows placing orders only for the symbols that already have open positions. The current pending orders are not considered since their execution may lead to changes in the current positions but it cannot increase their number. Hedging — pending orders are considered together with open positions, since a pending order activation always leads to opening a new position. When a limit is reached, the platform does not allow placing both new market orders for opening positions and pending orders.
    TRADE_RETCODE_REJECT_CANCEL = 10041  # The pending order activation request is rejected, the order is canceled
    TRADE_RETCODE_LONG_ONLY = 10042  # The request is rejected, because the "Only long positions are allowed" rule is set for the symbol (POSITION_TYPE_BUY)
    TRADE_RETCODE_SHORT_ONLY = 10043  # The request is rejected, because the "Only short positions are allowed" rule is set for the symbol (POSITION_TYPE_SELL)
    TRADE_RETCODE_CLOSE_ONLY = 10044  # The request is rejected, because the "Only position closing is allowed" rule is set for the symbol
    TRADE_RETCODE_FIFO_CLOSE = 10045  # The request is rejected, because "Position closing is allowed only by FIFO rule" flag is set for the trading account (ACCOUNT_FIFO_CLOSE=true)
    TRADE_RETCODE_HEDGE_PROHIBITED = 10046  # The request is rejected, because the "Opposite positions on a single symbol are disabled" rule is set for the trading account. For example, if the account has a Buy position, then a user cannot open a Sell position or place a pending sell order. The rule is only applied to accounts with hedging accounting system (ACCOUNT_MARGIN_MODE=ACCOUNT_MARGIN_MODE_RETAIL_HEDGING).
