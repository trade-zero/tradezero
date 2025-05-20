import typing as t

from datetime import datetime
from datetime import timedelta

from shared.domain.enums import ESymbolTradeMode
from shared.domain.entities import SymbolInfo

from .integration_adapter import IntegrationAdapter
from gateway.domain.ports import GatewayAbstract


class Mt5GatewayAdapter(IntegrationAdapter, GatewayAbstract):

    def connect(self, user_login: t.Optional[int] = None) -> None:
        self.initialize()

        if user_login:
            authorized = self.login(user_login.server, user_login.login, user_login.password)
            if not authorized:
                raise ValueError("Failed to connect to the MetaTrader 5 server")

    def disconnect(self) -> None:
        self.shutdown()

    def is_connected(self) -> bool:
        return self.initialize()

    def get_current_letter(self, symbol: str) -> SymbolInfo:

        if not self.is_connected():
            raise ValueError("Driver is not connected")

        today = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)

        symbols = self.symbols_get(group=f"*{symbol}*")
        active_symbols = [*filter(lambda s: s.trade_mode == ESymbolTradeMode.SYMBOL_TRADE_MODE_FULL.value, symbols)]
        active_symbols.sort(key=lambda x: x.expiration_time)

        expiration_time = datetime.fromtimestamp(active_symbols[0].expiration_time)
        expiration_time = expiration_time.replace(hour=0, minute=0, second=0, microsecond=0)

        if abs(today - expiration_time) > timedelta(days=0):
            position = 0
        else:
            position = 1

        return active_symbols[position]
