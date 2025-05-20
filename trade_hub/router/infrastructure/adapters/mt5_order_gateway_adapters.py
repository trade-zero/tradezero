import typing as t

from datetime import datetime

from gateway import mt5

from shared.utils import Singleton
from shared.domain.entities import TradeDeal
from shared.domain.entities import TradeOrder
from shared.domain.entities import TradePosition

from ...domain.entities import TradeRequest
from ...domain.entities import TradeResult
from ...domain.ports import GatewayAbstract
from ...domain.services import RateLimitService


class Mt5GatewayAdapter(GatewayAbstract, metaclass=Singleton):
    def __init__(self):
        self.rate_limit = RateLimitService()

    def place_order(self, order: TradeRequest) -> TradeResult:

        mt5.is_connected()

        self.rate_limit.limit()
        r = mt5.order_send(request=order)
        result = TradeResult(
            return_code=r.retcode,
            deal=r.deal,
            order=r.order,
            volume=r.volume,
            price=r.price,
            bid=r.bid,
            ask=r.ask,
            comment=r.comment,
            request_id=r.request_id,
            return_code_external=r.retcode_external,
        )

        return result

    def get_orders_pending(
            self,
            symbol: str = None,
            group: str = None,
            ticket: str = None,
    ) -> t.Union[list[TradeOrder], TradeOrder]:

        trade_orders: list[TradeOrder] = []
        orders = mt5.orders_get(symbol=symbol, group=group, ticket=ticket)
        if orders:
            for order in orders:
                trade_orders += [
                    TradeOrder(
                        ticket=order.ticket,
                        symbol=order.symbol,
                        time_setup=datetime.fromtimestamp(order.time_setup_msc / 1000),
                        time_done=datetime.fromtimestamp(order.time_done_msc / 1000),
                        time_expiration=datetime.fromtimestamp(order.time_expiration / 1000),
                        type=order.type,
                        type_time=order.type_time,
                        type_filling=order.type_filling,
                        state=order.state,
                        position_id=order.position_id,
                        position_by_id=order.position_by_id,
                        reason=order.reason,
                        volume_initial=order.volume_initial,
                        volume_current=order.volume_current,
                        price_open=order.price_open,
                        stop_loss=order.sl,
                        take_profit=order.tp,
                        price_stop_limit=order.price_stoplimit,
                        comment=order.comment,
                        magic_number=order.magic,
                    )
                ]

        return trade_orders

    def get_positions_open(
            self,
            symbol: str = None,
            group: str = None,
            ticket: str = None,
    ) -> t.Union[list[TradePosition], TradePosition]:

        positions_open: list[TradePosition] = []

        if symbol:
            positions = mt5.positions_get(symbol=symbol)
        elif group:
            positions = mt5.positions_get(group=group)
        elif ticket:
            positions = mt5.positions_get(ticket=ticket)
        else:
            positions = mt5.positions_get()

        if positions:
            for position in positions:
                positions_open += [
                    TradePosition(
                        ticket=position.ticket,
                        identifier=position.identifier,
                        external_id=position.external_id,
                        symbol=position.symbol,
                        time=datetime.fromtimestamp(position.time_msc / 1000),
                        time_update=datetime.fromtimestamp(position.time_update_msc / 1000),
                        price_open=position.price_open,
                        volume=position.volume,
                        stop_loss=position.sl,
                        take_profit=position.tp,
                        position_type=position.type,
                        reason_type=position.reason,
                        comment=position.comment,
                        magic_number=position.magic,
                    )
                ]

        return positions_open

    def get_history_orders(
            self,
            date_from: datetime,
            date_to: datetime,
            symbol: str = None,
            group: str = None,
            ticket: str = None
    ) -> list[TradeOrder]:
        orders: list[TradeOrder] = []
        orders_history = mt5.history_orders_get(date_from, date_to, symbol=symbol, group=group, ticket=ticket)

        # if not orders:
        #     return orders

        for order in orders_history:
            orders += [
                TradeOrder(
                    ticket=order.ticket,
                    symbol=order.symbol,
                    time_setup=datetime.fromtimestamp(order.time_setup_msc / 1000),
                    time_done=datetime.fromtimestamp(order.time_done_msc / 1000),
                    time_expiration=datetime.fromtimestamp(order.time_expiration),
                    type=order.type,
                    type_time=order.type_time,
                    type_filling=order.type_filling,
                    state=order.state,
                    position_id=order.position_id,
                    position_by_id=order.position_by_id,
                    reason=order.reason,
                    volume_initial=order.volume_initial,
                    volume_current=order.volume_current,
                    price_open=order.price_open,
                    stop_loss=order.sl,
                    take_profit=order.sl,
                    price_stop_limit=order.price_stoplimit,
                    comment=order.comment,
                    magic_number=order.magic,
                )
            ]

        return orders

    def get_deals_history(
            self,
            date_from: datetime,
            date_to: datetime,
            symbol: str = None,
            group: str = None,
            ticket: str = None,
    ) -> list[TradeDeal]:
        deals_history: list[TradeDeal] = []
        deals = mt5.history_deals_get(date_from, date_to, symbol=symbol, group=group, ticket=ticket)

        # if not deals:
        #     return deals_history

        for deal in deals:
            deals_history += [
                TradeDeal(
                    ticket=deal.ticket,
                    order=deal.order,
                    time=deal.time,
                    time_msc=deal.time_msc,
                    type=deal.type,
                    entry=deal.entry,
                    magic_number=deal.magic,
                    position_id=deal.position_id,
                    reason=deal.reason,
                    volume=deal.volume,
                    price=deal.price,
                    commission=deal.commission,
                    swap=deal.swap,
                    profit=deal.profit,
                    fee=deal.fee,
                    symbol=deal.symbol,
                    comment=deal.comment,
                    external_id=deal.external_id,
                )
            ]

        return deals_history
