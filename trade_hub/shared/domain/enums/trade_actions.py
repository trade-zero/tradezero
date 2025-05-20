from enum import Enum


class ETradeActions(Enum):
    TRADE_ACTION_DEAL = 1  # Place an order for an instant deal with the specified parameters (set a market order)
    TRADE_ACTION_PENDING = 5  # Place an order for performing a deal at specified conditions (pending order)
    TRADE_ACTION_SLTP = 6  # Change open position Stop Loss and Take Profit
    TRADE_ACTION_MODIFY = 7  # Change parameters of the previously placed trading order
    TRADE_ACTION_REMOVE = 8  # Remove previously placed pending order
    TRADE_ACTION_CLOSE_BY = 10  # Close a position by an opposite one
