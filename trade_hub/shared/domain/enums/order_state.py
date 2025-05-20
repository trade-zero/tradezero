from enum import Enum


class EOrderState(Enum):
    ORDER_STATE_STARTED = 0  # Order checked, but not yet accepted by broker
    ORDER_STATE_PLACED = 1  # Order accepted
    ORDER_STATE_CANCELED = 2  # Order canceled by client
    ORDER_STATE_PARTIAL = 3  # Order partially executed
    ORDER_STATE_FILLED = 4  # Order fully executed
    ORDER_STATE_REJECTED = 5  # Order rejected
    ORDER_STATE_EXPIRED = 6  # Order expired
    ORDER_STATE_REQUEST_ADD = 7  # Order is being registered(placing to the trading system)
    ORDER_STATE_REQUEST_MODIFY = 8  # Order is being modified(changing its parameters)
    ORDER_STATE_REQUEST_CANCEL = 9  # Order is being deleted(deleting from trading system)
