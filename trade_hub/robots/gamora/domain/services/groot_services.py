from shared.domain.enums import EOrderType
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from robots.core.domain.enums import EDirectionType

from ..ports import GrootAbstract


class GrootService(GrootAbstract):

    def trade_timer(self, orders: list[TradeOrder], positions: list[TradePosition]) -> None:
        orders = self.manager.filter_order_by_magic_number(orders=orders, magic_number=self.settings.magic_number)
        positions = self.manager.filter_position_by_magic_number(positions=positions, magic_number=self.settings.magic_number)

        sell_limit: list[TradeOrder] = [*filter(lambda x: x.type == EOrderType.ORDER_TYPE_SELL_LIMIT, orders)]

        if len(sell_limit) > 0:
            tick = self.gateway.symbol_info_tick(self.settings.symbol)
            diff = self.settings.freq + self.settings.gain + self.settings.slippage
            sell_limit.sort(key=lambda x: x.price_open)

            if abs(tick.ask - sell_limit[0].price_open) > diff:
                self.trade.order_modify(
                    ticket=sell_limit[-1].ticket,
                    price=sell_limit[0].price_open - self.settings.freq,
                )

            if self.settings.max_orders > len(sell_limit):
                price = sell_limit[-1].price_open + self.settings.freq
                take_profit = price - self.settings.gain
                self.trade.sell_limit(
                    volume=self.settings.volume,
                    price=price,
                    take_profit=take_profit,
                    symbol=self.settings.symbol,
                    magic_number=self.settings.magic_number
                )

        if len(orders) == 0:
            tick = self.gateway.symbol_info_tick(self.settings.symbol)
            for i in range(1, self.settings.max_orders + 1):

                if self.settings.direction_type == EDirectionType.DIRECTION_SELL:
                    price = tick.bid + ((i * self.settings.freq) + self.settings.slippage)
                    take_profit = price - self.settings.gain
                    self.trade.sell_limit(
                        volume=self.settings.volume,
                        price=price,
                        take_profit=take_profit,
                        symbol=self.settings.symbol,
                        magic_number=self.settings.magic_number
                    )

                elif self.settings.direction_type == EDirectionType.DIRECTION_BUY:
                    self.trade.buy_limit(
                        volume=self.settings.volume,
                        price=tick.ask - ((i * self.settings.freq) + self.settings.slippage),
                        symbol=self.settings.symbol,
                        magic_number=self.settings.magic_number
                    )

    def trade_transaction(self, orders: list[TradeOrder], positions: list[TradePosition]) -> None:
        ...
