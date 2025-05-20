from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from robots.core.domain.ports import ManagerAbstract
from robots.core.domain.enums import ETradeModeType

from ..dto import TransactionStatusInputDto
from ..entities import SettingsInputs
from ..ports import StarLordAbstract
from ..ports import SMActionsAbstract
from ..ports import SMTransactionAbstract
from ..ports import ReplaceCanceledOrdersAbstract
from ..mapper import from_settings_to_transaction


class StarLordService(StarLordAbstract):
    def __init__(
            self,
            sm_actions: SMActionsAbstract,
            sm_transactions: SMTransactionAbstract,
            manager: ManagerAbstract,
            replace_canceled_orders: ReplaceCanceledOrdersAbstract,
    ):
        self.actions = sm_actions
        self.transactions = sm_transactions
        self.manager = manager
        self.replace_canceled_orders = replace_canceled_orders

    def on_init(
            self,
            setting: SettingsInputs,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> TransactionStatusInputDto:
        if setting.globals.trade_mode == ETradeModeType.SWING_TRADE:
            self.replace_canceled_orders(
                settings=setting,
                deals_history=deals_history,
                orders_history=orders_history,
                orders_pending=orders_pending,
                positions_open=positions_open,
            )
        return from_settings_to_transaction(setting)

    def trade_timer(
            self,
            inner_transaction: TransactionStatusInputDto,
            deals_history: list[TradeDeal],
            orders_history: list[TradeOrder],
            orders_pending: list[TradeOrder],
            positions_open: list[TradePosition]
    ) -> TransactionStatusInputDto:
        magic_number = inner_transaction.setting.globals.magic_number
        deals_history = self.manager.filter_by_magic_number(iterable=deals_history, magic_number=magic_number)
        orders_history = self.manager.filter_by_magic_number(iterable=orders_history, magic_number=magic_number)
        orders_pending = self.manager.filter_by_magic_number(iterable=orders_pending, magic_number=magic_number)
        positions_open = self.manager.filter_by_magic_number(iterable=positions_open, magic_number=magic_number)

        status = self.transactions.from_buffer(
            sm=inner_transaction, orders=orders_pending, positions=positions_open)

        self.actions.update(
            sm=status,
            deals_history=deals_history,
            orders_history=orders_history,
            orders_pending=orders_pending,
            positions_open=positions_open,
        )

        return status

    def trade_transaction(self, orders: list[TradeOrder], positions: list[TradePosition]) -> None:
        ...
