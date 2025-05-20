from enum import Enum


class EPositionReason(Enum):
    POSITION_REASON_CLIENT = 0  # The position was opened by a desktop terminal
    POSITION_REASON_MOBILE = 1  # The position was opened by a mobile application
    POSITION_REASON_WEB = 2  # The position was opened by web platform
    POSITION_REASON_EXPERT = 3  # The position was opened by Expert Advisor
