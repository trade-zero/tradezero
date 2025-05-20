import typing as t

from dataclasses import field
from dataclasses import dataclass

from robots.core.domain.enums import EDirectionType

from shared.domain.enums import EOrderType
from shared.domain.enums import EPositionType
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..enums import ETransactionStatus
from ..entities import SettingsInputs


@dataclass
class TransactionStatusInputDto:
    setting: SettingsInputs
    position_status: t.Optional[ETransactionStatus] = field(default=None)
    hedge_status: t.Optional[ETransactionStatus] = field(default=None)

    total_hedge: int = field(default=0)
    total_positions: int = field(default=0)

    total_orders_position_forward: int = field(default=0)
    total_orders_position_backward: int = field(default=0)
    total_orders_hedge_forward: int = field(default=0)
    total_orders_hedge_backward: int = field(default=0)

    updated_hedge: bool = field(default=False)
    updated_positions: bool = field(default=False)

    updated_orders_position_backward: bool = field(default=False)
    updated_orders_position_forward: bool = field(default=False)
    updated_orders_hedge_backward: bool = field(default=False)
    updated_orders_hedge_forward: bool = field(default=False)

    @classmethod
    def from_buffer(
            cls,
            setting: SettingsInputs,
            orders: list[TradeOrder],
            positions: list[TradePosition],
    ) -> "TransactionStatusInputDto":
        new_object = cls(setting=setting)
        positions_buy: int = 0
        positions_sell: int = 0
        orders_sell_limit: int = 0
        orders_sell_stop: int = 0
        orders_buy_limit: int = 0
        orders_buy_stop: int = 0

        for position in positions:
            match position.position_type:
                case EPositionType.POSITION_TYPE_BUY:
                    positions_buy += 1
                case EPositionType.POSITION_TYPE_SELL:
                    positions_sell += 1

        for order in orders:
            match order.type:
                case EOrderType.ORDER_TYPE_SELL_LIMIT:
                    orders_sell_limit += 1

                case EOrderType.ORDER_TYPE_SELL_STOP:
                    orders_sell_stop += 1

                case EOrderType.ORDER_TYPE_BUY_LIMIT:
                    orders_buy_limit += 1

                case EOrderType.ORDER_TYPE_BUY_STOP:
                    orders_buy_stop += 1

        if new_object.setting.globals.direction == EDirectionType.DIRECTION_BUY:

            new_object.total_hedge = positions_sell
            new_object.total_positions = positions_buy

            new_object.total_orders_position_backward = orders_buy_limit
            new_object.total_orders_position_forward = orders_buy_stop
            new_object.total_orders_hedge_backward = orders_sell_stop
            new_object.total_orders_hedge_forward = orders_sell_limit

        elif new_object.setting.globals.direction == EDirectionType.DIRECTION_SELL:

            new_object.total_hedge = positions_buy
            new_object.total_positions = positions_sell

            new_object.total_orders_position_backward = orders_sell_limit
            new_object.total_orders_position_forward = orders_sell_stop
            new_object.total_orders_hedge_backward = orders_buy_stop
            new_object.total_orders_hedge_forward = orders_sell_limit

        return new_object
