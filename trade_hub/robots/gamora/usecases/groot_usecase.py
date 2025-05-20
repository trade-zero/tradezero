from router import TradeUseCase
from gateway import mt5
from gateway.usecases import PyMt5

from robots.core.domain.services import ManagerService

from ..domain.entities import GrootSetting
from ..domain.services import GrootService


class GrootUseCase(GrootService):
    def __init__(
            self,
            settings: GrootSetting,
            trade: TradeUseCase = TradeUseCase(),
            manager: ManagerService = ManagerService(),
            gateway: PyMt5 = mt5,
    ) -> None:
        self.settings = settings
        self.trade = trade
        self.manager = manager
        self.gateway = gateway
