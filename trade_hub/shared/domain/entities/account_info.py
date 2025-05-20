import typing as t

from dataclasses import dataclass, field
from typing import Generic
from typing import TypeVar

from shared.domain.entities.entity import IEntity
from shared.domain.enums.account_margin_mode import EAccountMarginMode
from shared.domain.enums.account_trade_mode import EAccountTradeMode

T = TypeVar("T", covariant=False)


@dataclass
class AccountInfo(Generic[T], IEntity):
    assets: float
    balance: float
    commission_blocked: float
    company: str
    credit: float
    currency: str
    currency_digits: int
    equity: float
    fifo_close: bool
    leverage: int
    liabilities: float
    limit_orders: int
    login: int
    margin: float
    margin_free: float
    margin_initial: float
    margin_level: float
    margin_maintenance: float
    margin_mode: t.Union[EAccountMarginMode, str, int]
    margin_so_call: float
    margin_so_mode: float
    margin_so_so: float
    name: str
    profit: float
    server: str
    trade_allowed: bool
    trade_expert: bool
    trade_mode: t.Union[EAccountTradeMode, str, int]
    _id: T = field(default=None)

    def set_ref(self, other: 'AccountInfo'):
        self._id = other._id

    def to_document(self) -> dict:
        if self._id is not None:
            return {"account_info": self.to_json()}
        return {
            "name": self.name,
            "account": self.login,
            "account_info": self.to_json(),
        }

    def __post_init__(self):
        if isinstance(self.trade_mode, int):
            self.trade_mode = EAccountTradeMode(self.trade_mode)
        elif isinstance(self.trade_mode, str):
            self.trade_mode = EAccountTradeMode[self.trade_mode]
        elif not isinstance(self.trade_mode, EAccountTradeMode):
            raise ValueError("trade_mode must be type EAccountTradeMode")

        if isinstance(self.margin_mode, int):
            self.margin_mode = EAccountMarginMode(self.margin_mode)
        elif isinstance(self.margin_mode, str):
            self.margin_mode = EAccountMarginMode[self.margin_mode]
        elif not isinstance(self.margin_mode, EAccountMarginMode):
            raise ValueError("trade_mode must be type EAccountMarginMode")
