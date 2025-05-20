import tensorflow as tf


class MarketRegularizer(tf.keras.regularizers.Regularizer):
    def __init__(self, strength=0.01, correlation_matrix=None):
        self.strength = strength
        self.correlations = correlation_matrix  # Matriz de correlação entre ativos

    def __call__(self, weights):
        if self.correlations is not None:
            # Penaliza diferenças nas direções de correlação histórica
            sector_penalty = tf.tensordot(weights, tf.linalg.matvec(self.correlations, weights), axes=1)
            return self.strength * sector_penalty
        return 0.0
