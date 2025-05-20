from enum import Enum


class EOrderReason(Enum):
    ORDER_REASON_CLIENT = 0  # The order was placed from a desktop terminal
    ORDER_REASON_MOBILE = 1  # The order was placed from a mobile application
    ORDER_REASON_WEB = 2  # The order was placed from a web platform
    ORDER_REASON_EXPERT = 3  # The order was placed by an Expert Advisor
    ORDER_REASON_SL = 4  # The order was placed as a result of Stop Loss activation
    ORDER_REASON_TP = 5  # The order was placed as a result of Take Profit activation
    ORDER_REASON_SO = 6  # The order was placed as a result of the Stop Out event
