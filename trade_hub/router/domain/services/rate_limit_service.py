import time

from shared.utils import Singleton

from ..ports import RateLimitAbstract


class RateLimitService(RateLimitAbstract, metaclass=Singleton):
    def __init__(self, rate_limit: int = 6, rate_wait: float = 1.0):
        self.rate_limit = rate_limit
        self.rate_wait = rate_wait
        self.tokens = rate_limit
        self.last_token_refill = time.time()

    def limit(self):
        current_time = time.time()
        time_since_last_refill = current_time - self.last_token_refill

        # if rate wait is completed. restart tokens and last_token_refill.
        if time_since_last_refill > self.rate_wait:
            self.tokens = self.rate_limit
            self.last_token_refill = current_time

        # if tokens equal zero sleep to await rate_wait.
        if self.tokens <= 0:
            time_to_wait = self.last_token_refill + self.rate_wait - time.time()
            if time_to_wait > 0:
                time.sleep(time_to_wait)
            self.tokens = self.rate_limit

        # decrease the quantity that can be sent.
        self.tokens -= 1
