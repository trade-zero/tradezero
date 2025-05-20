import tensorflow as tf


class BVLearningRateSchedule(tf.keras.optimizers.schedules.LearningRateSchedule):
    def __init__(self, dim_embedding, warmup_steps=10000):
        super().__init__()
        self.dim_embedding = tf.cast(dim_embedding, tf.float32)
        self.warmup_steps = warmup_steps
        self.volatility_adjustment = 0.1  # Coeficiente de volatilidade

    def __call__(self, step):
        step = tf.cast(step, tf.float32)
        lr = (self.dim_embedding ** -0.5) * tf.minimum(step ** -0.5, step * self.warmup_steps ** -1.5)

        # Ajuste baseado na volatilidade histórica do dia atual
        if hasattr(self, 'current_volatility'):
            lr *= tf.exp(-self.volatility_adjustment * self.current_volatility)

        return lr
