from shared.utils import Singleton

from router.domain.ports import GatewayAbstract
from router.domain.ports import FormatterSendOrderAbstract

from router.domain.services import TradeService
from router.domain.services import FormatterSendOrderService

from router.infrastructure.adapters import Mt5GatewayAdapter


class TradeUseCase(TradeService, metaclass=Singleton):
    def __init__(
            self,
            gateway: type[GatewayAbstract] = Mt5GatewayAdapter,
            formatter: type[FormatterSendOrderAbstract] = FormatterSendOrderService
    ):
        TradeService.__init__(
            self,
            gateway=gateway,
            formatter=formatter,
        )
