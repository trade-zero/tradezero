from ..domain.services import StarLordService
from ..domain.services import SMActionsService
from ..domain.services import SMTransactionService
from ..domain.services import EntreAtMarketService
from ..domain.services import PendingOrdersInitPositionBackwardService
from ..domain.services import PendingOrdersInitPositionForwardService
from ..domain.services import PendingOrdersInitHedgeBackwardService
from ..domain.services import PendingOrdersInitHedgeForwardService
from ..domain.services import PendingOrdersMovePositionPriceByFrequencyService
from ..domain.services import PendingOrdersMoveHedgePriceByFrequencyService
from ..domain.services import ReplaceCanceledOrdersService
from ..domain.services import PutTargetToReducePositionService
from ..domain.services import PutTargetToReduceHedgeService
from ..domain.services import CloseAllPositionService
from ..domain.services import CloseAllOrdersService

from router import TradeUseCase
from robots.core.domain.services import ManagerService
from shared.utils import ExponentialDistribution


class StarLordUseCase(StarLordService):
    def __init__(
            self,
    ):
        distribution = ExponentialDistribution()

        pending_orders_init_hedge_backward = PendingOrdersInitHedgeBackwardService(
            trade_use_case=TradeUseCase,
            distribution=distribution,
        )

        pending_orders_init_hedge_forward = PendingOrdersInitHedgeForwardService(
            trade_use_case=TradeUseCase,
            distribution=distribution,
        )

        pending_orders_init_position_backward = PendingOrdersInitPositionBackwardService(
            trade_use_case=TradeUseCase,
            distribution=distribution,
        )

        pending_orders_init_position_forward = PendingOrdersInitPositionForwardService(
            trade_use_case=TradeUseCase,
            distribution=distribution,
        )

        pending_orders_hedge_by_frequency = PendingOrdersMoveHedgePriceByFrequencyService(TradeUseCase)
        pending_orders_position_by_frequency = PendingOrdersMovePositionPriceByFrequencyService(TradeUseCase)
        enter_at_market = EntreAtMarketService(TradeUseCase)
        close_all_orders = CloseAllOrdersService(TradeUseCase)
        close_all_position = CloseAllPositionService(TradeUseCase)
        put_target_to_reduce_position = PutTargetToReducePositionService(TradeUseCase)
        put_target_to_reduce_hedge = PutTargetToReduceHedgeService(TradeUseCase)

        sm_actions = SMActionsService(
            pending_orders_init_hedge_backward=pending_orders_init_hedge_backward,
            pending_orders_init_hedge_forward=pending_orders_init_hedge_forward,
            pending_orders_init_position_backward=pending_orders_init_position_backward,
            pending_orders_init_position_forward=pending_orders_init_position_forward,
            pending_orders_position_by_frequency=pending_orders_position_by_frequency,
            pending_orders_hedge_by_frequency=pending_orders_hedge_by_frequency,
            enter_at_market=enter_at_market,
            close_all_orders=close_all_orders,
            close_all_position=close_all_position,
            put_target_to_reduce_position=put_target_to_reduce_position,
            put_target_to_reduce_hedge=put_target_to_reduce_hedge,
        )

        replace_canceled_orders = ReplaceCanceledOrdersService(
            trade_use_case=TradeUseCase,
        )

        StarLordService.__init__(
            self,
            sm_actions=sm_actions,
            sm_transactions=SMTransactionService(),
            manager=ManagerService(),
            replace_canceled_orders=replace_canceled_orders,
        )
        self.distribution = distribution
