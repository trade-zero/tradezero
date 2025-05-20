import math as mt


class ExponentialDistribution:
    def __init__(self):
        self._a = 0.
        self._A = 0.
        self._b = 0.
        self._B = 0.

    def fit(
            self,
            qtd_levels: int,
            qtd_target_by_levels: float,
            qtd_initial_lots: float,
            leverage_factor: float,
    ) -> None:
        if qtd_levels < 9:
            qtd_levels = 9

        y = []  # lots
        x = []  # points
        log_y = []
        x_log_y = []

        self._a = 0.
        self._A = 0.
        self._b = 0.
        self._B = 0.

        for i in range(qtd_levels):

            if i > 0:
                y += [y[i-1] + (y[i-1] * leverage_factor)]
            else:
                y += [qtd_initial_lots]

            log_y += [mt.log(y[i])]
            x += [i * qtd_target_by_levels]
            x_log_y += [x[i] * log_y[i]]

        sum_log_y = sum(log_y)
        sum_x = sum(x)
        sum_x_2 = sum([i*i for i in x])
        sum_x_log_y = sum(x_log_y)

        numerator = sum_x_log_y - (sum_x * sum_log_y / qtd_levels)
        denominator = sum_x_2 - (sum_x * sum_x / qtd_levels)

        self._a = numerator / denominator
        self._b = (sum_log_y / qtd_levels) - (self._a * sum_x / qtd_levels)
        self._A = mt.exp(self._a)
        self._B = mt.exp(self._b)

        print("model=", self._a, "*EXP(", self._b, ",x)\n")

    def predict(self, price: float) -> float:
        return self._B*mt.exp(self._a*price)
