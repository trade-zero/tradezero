from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..dto import TransactionStatusInputDto
from ..enums import ETransactionStatus
from ..ports import SMTransactionAbstract


class SMTransactionService(SMTransactionAbstract):

    def from_buffer(
            self,
            sm: TransactionStatusInputDto,
            orders: list[TradeOrder],
            positions: list[TradePosition],
    ) -> TransactionStatusInputDto:

        t = TransactionStatusInputDto.from_buffer(setting=sm.setting, orders=orders, positions=positions)

        if t.total_positions == 0 and t.total_orders_position_backward == 0 and t.total_orders_position_forward == 0:
            t.position_status = ETransactionStatus.ENTER_THE_MARKET

        elif t.total_positions == 0 and (t.total_orders_position_backward > 0 or t.total_orders_position_forward > 0):
            t.position_status = ETransactionStatus.REMOVE_ORDERS

        elif t.total_positions > 0 and t.total_orders_position_backward == 0:
            t.position_status = ETransactionStatus.PLACE_ORDERS_TO_BACKWARD

        elif t.total_positions > 0 and t.total_orders_position_forward == 0:
            t.position_status = ETransactionStatus.PLACE_ORDERS_TO_FORWARD

        elif t.total_positions > 0 and t.total_orders_position_backward > 0 and t.total_orders_position_forward > 0:
            t.position_status = ETransactionStatus.VERIFY_UPDATE_ON_ORDERS_AND_POSITIONS

        if (t.total_positions > 0 or t.total_hedge > 0) and t.total_orders_hedge_backward == 0:
            t.hedge_status = ETransactionStatus.PLACE_ORDERS_TO_BACKWARD

        elif (t.total_positions > 0 or t.total_hedge > 0) and t.total_orders_hedge_forward == 0:
            t.hedge_status = ETransactionStatus.PLACE_ORDERS_TO_FORWARD

        elif t.total_positions == 0 and (t.total_orders_hedge_backward > 0 or t.total_orders_hedge_forward > 0):
            t.hedge_status = ETransactionStatus.REMOVE_ORDERS

        elif (t.total_positions > 0 or t.total_hedge > 0) and t.total_orders_hedge_backward > 0 and t.total_orders_hedge_forward > 0:
            t.hedge_status = ETransactionStatus.VERIFY_UPDATE_ON_ORDERS_AND_POSITIONS

        t.updated_hedge = sm.total_hedge != t.total_hedge
        t.updated_positions = sm.total_positions != t.total_positions

        t.updated_orders_position_forward = sm.total_orders_position_forward != t.total_orders_position_forward
        t.updated_orders_position_backward = sm.total_orders_position_backward != t.total_orders_position_backward
        t.updated_orders_hedge_backward = sm.total_orders_hedge_backward != t.total_orders_hedge_backward
        t.updated_orders_hedge_forward = sm.total_orders_hedge_forward != t.total_orders_hedge_forward

        return t
