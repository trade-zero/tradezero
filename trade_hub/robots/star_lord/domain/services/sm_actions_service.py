from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ..dto import TransactionStatusInputDto
from ..enums import ETransactionStatus

from ..ports import SMActionsAbstract
from ..ports import CloseAllOrdersAbstract
from ..ports import CloseAllPositionAbstract
from ..ports import EntreAtMarketAbstract
from ..ports import IPendingOrdersInitPositionBackward
from ..ports import IPendingOrdersInitPositionForward
from ..ports import IPendingOrdersInitHedgeBackward
from ..ports import IPendingOrdersInitHedgeForward
from ..ports import IPendingOrdersMovePositionPriceByFrequency
from ..ports import IPendingOrdersMoveHedgePriceByFrequency
from ..ports import IPutTargetToReduceHedge
from ..ports import IPutTargetToReducePosition


class SMActionsService(SMActionsAbstract):

    def __init__(
            self,
            pending_orders_init_position_backward: IPendingOrdersInitPositionBackward,
            pending_orders_init_position_forward: IPendingOrdersInitPositionForward,
            pending_orders_init_hedge_backward: IPendingOrdersInitHedgeBackward,
            pending_orders_init_hedge_forward: IPendingOrdersInitHedgeForward,
            pending_orders_position_by_frequency: IPendingOrdersMovePositionPriceByFrequency,
            pending_orders_hedge_by_frequency: IPendingOrdersMoveHedgePriceByFrequency,
            enter_at_market: EntreAtMarketAbstract,
            close_all_orders: CloseAllOrdersAbstract,
            close_all_position: CloseAllPositionAbstract,
            put_target_to_reduce_hedge: IPutTargetToReduceHedge,
            put_target_to_reduce_position: IPutTargetToReducePosition,
    ) -> None:
        self.close_all_position = close_all_position
        self.close_all_orders = close_all_orders
        self.enter_at_market = enter_at_market
        self.pending_orders_init_hedge_forward = pending_orders_init_hedge_forward
        self.pending_orders_init_hedge_backward = pending_orders_init_hedge_backward
        self.pending_orders_init_position_forward = pending_orders_init_position_forward
        self.pending_orders_init_position_backward = pending_orders_init_position_backward
        self.pending_orders_position_by_frequency = pending_orders_position_by_frequency
        self.pending_orders_hedge_by_frequency = pending_orders_hedge_by_frequency
        self.put_target_to_reduce_hedge = put_target_to_reduce_hedge
        self.put_target_to_reduce_position = put_target_to_reduce_position

        self.last_message = ""

    def update(
            self,
            sm: TransactionStatusInputDto,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> None:

        msg = f"{sm.position_status=}: {sm.hedge_status=}"

        if self.last_message != msg:
            print(msg)
            self.last_message = msg
        # print(msg, end="\r")

        match sm.hedge_status:

            case ETransactionStatus.ENTER_THE_MARKET:
                ...

            case ETransactionStatus.PLACE_ORDERS_TO_BACKWARD:

                if sm.setting.hedge.is_active_backward:
                    self.pending_orders_init_hedge_backward(
                        settings=sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

            case ETransactionStatus.PLACE_ORDERS_TO_FORWARD:
                if sm.setting.hedge.is_active_forward:
                    self.pending_orders_init_hedge_forward(
                        settings=sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )
            case ETransactionStatus.VERIFY_UPDATE_ON_ORDERS_AND_POSITIONS:

                if sm.updated_hedge or sm.updated_orders_hedge_backward:

                    self.put_target_to_reduce_hedge(
                        settings=sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

                    self.pending_orders_hedge_by_frequency(
                        settings=sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

                if sm.updated_orders_hedge_forward:

                    self.pending_orders_init_hedge_forward(
                        sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

                    self.pending_orders_init_hedge_backward(
                        sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

        match sm.position_status:

            case ETransactionStatus.ENTER_THE_MARKET:
                self.enter_at_market(sm.setting)

            case ETransactionStatus.PLACE_ORDERS_TO_BACKWARD:
                if sm.setting.position.is_active_backward:
                    self.pending_orders_init_position_backward(
                        sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

            case ETransactionStatus.PLACE_ORDERS_TO_FORWARD:
                if sm.setting.position.is_active_forward:
                    self.pending_orders_init_position_forward(
                        sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

            case ETransactionStatus.REMOVE_ORDERS:
                self.close_all_position(positions=positions_open)
                self.close_all_orders(orders=orders_pending)

            case ETransactionStatus.VERIFY_UPDATE_ON_ORDERS_AND_POSITIONS:

                if sm.updated_positions or sm.updated_orders_position_backward:

                    self.put_target_to_reduce_position(
                        settings=sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

                    self.pending_orders_position_by_frequency(
                        settings=sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

                if sm.updated_orders_position_forward:

                    self.pending_orders_init_position_forward(
                        sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )

                    self.pending_orders_init_position_backward(
                        sm.setting,
                        deals_history=deals_history,
                        orders_history=orders_history,
                        orders_pending=orders_pending,
                        positions_open=positions_open,
                    )
