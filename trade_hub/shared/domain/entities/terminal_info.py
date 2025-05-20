from dataclasses import dataclass


@dataclass
class TerminalInfo:
    build: int
    codepage: int
    commondata_path: str
    community_account: bool
    community_balance: float
    community_connection: bool
    company: str
    connected: bool
    data_path: str
    dlls_allowed: bool
    email_enabled: bool
    ftp_enabled: bool
    language: str
    maxbars: int
    mqid: bool
    name: str
    notifications_enabled: bool
    path: str
    ping_last: int
    retransmission: float
    trade_allowed: bool
    tradeapi_disabled: bool
