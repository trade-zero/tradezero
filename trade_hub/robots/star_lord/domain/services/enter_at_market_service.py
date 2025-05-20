from robots.core.domain.enums import EDirectionType
from router.domain.ports import TradeAbstract

from ..ports import EntreAtMarketAbstract
from ..entities import SettingsInputs


class EntreAtMarketService(EntreAtMarketAbstract):

    def __init__(self, trade: type[TradeAbstract] = TradeAbstract):
        self.trade = trade()

    def __call__(
            self,
            settings: SettingsInputs,
    ) -> None:

        match settings.globals.direction:

            case EDirectionType.WITHOUT_DIRECTION:
                self.trade.sell(
                    symbol=settings.globals.symbol,
                    volume=settings.position.qtd_init_lots,
                    magic_number=settings.globals.magic_number,
                )
                self.trade.buy(
                    symbol=settings.globals.symbol,
                    volume=settings.position.qtd_init_lots,
                    magic_number=settings.globals.magic_number,
                )

            case EDirectionType.DIRECTION_BUY:
                self.trade.buy(
                    symbol=settings.globals.symbol,
                    volume=settings.position.qtd_init_lots,
                    magic_number=settings.globals.magic_number,
                )

            case EDirectionType.DIRECTION_SELL:
                self.trade.sell(
                    symbol=settings.globals.symbol,
                    volume=settings.position.qtd_init_lots,
                    magic_number=settings.globals.magic_number,
                )
