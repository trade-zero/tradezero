from enum import Enum


class EOrderTime(Enum):
    ORDER_TIME_GTC = 0  # Good till cancel order
    ORDER_TIME_DAY = 1  # Good till current trade day order
    ORDER_TIME_SPECIFIED = 2  # Good till expired order
    ORDER_TIME_SPECIFIED_DAY = 3  # The order will be effective till 23:59:59 of the day.
