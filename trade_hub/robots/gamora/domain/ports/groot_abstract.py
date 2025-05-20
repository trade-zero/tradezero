from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from router.domain.ports import TradeAbstract
from gateway.domain.ports import GatewayAbstract

from ..entities import GrootSetting
from robots.core.domain.ports import ManagerAbstract


class GrootAbstract:
    trade: TradeAbstract
    gateway: GatewayAbstract
    manager: ManagerAbstract
    settings: GrootSetting

    def trade_timer(self, orders: list[TradeOrder], positions: list[TradePosition]) -> None:
        ...

    def trade_transaction(self, orders: list[TradeOrder], positions: list[TradePosition]) -> None:
        ...
