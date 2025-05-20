from enum import Enum


class EBookType(Enum):
    BOOK_TYPE_SELL = 1  # Sell order (Offer)
    BOOK_TYPE_BUY = 2  # Buy order (Bid)
    BOOK_TYPE_SELL_MARKET = 3  # Sell order by Market
    BOOK_TYPE_BUY_MARKET = 4  # Buy order by Market
