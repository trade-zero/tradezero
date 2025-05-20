from .integration_abstract import IntegrationAbstract


class GatewayAbstract(IntegrationAbstract):
    def connect(self) -> None:
        pass

    def disconnect(self) -> None:
        pass

    def is_connected(self) -> bool:
        pass

    def get_current_letter(self, symbol: str):
        ...
