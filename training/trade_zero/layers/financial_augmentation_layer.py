import numpy as np
import tensorflow as tf


class FinancialAugmentationLayer(tf.keras.layers.Layer):
    def __init__(self, p=0.5, max_spread=0.0005):
        super().__init__()
        self.p = p
        self.max_spread = max_spread

    def call(self, inputs, training=True, **kwargs):
        if not training:
            return inputs

        # Aplica aumentação com probabilidade self.p
        if tf.random.uniform(()) < self.p:
            return self._apply_augmentation(inputs)

        return inputs

    def _apply_augmentation(self, x):
        # 1. Time Warping controlado por volatilidade
        x = self.volatility_aware_warp(x)
        # 2. Volume-based Masking
        x = self.volume_masking(x)
        # 3. Spread Simulation
        return self.spread_simulation(x)

    def volatility_aware_warp(self, x):
        # Calcula volatilidade local
        std = tf.math.reduce_std(x[:, :, :4], axis=1, keepdims=True)
        warp_factor = tf.random.uniform(shape=[], minval=0.9, maxval=1.1) * std
        return x * warp_factor

    def volume_masking(self, x):
        # Máscara baseada no volume relativo
        volume = x[:, :, 4:5]
        volume_np = volume.numpy()
        threshold = np.percentile(volume_np, 30)
        # mask = tf.cast(volume > threshold, tf.float32)
        mask = tf.where(volume > threshold, 1.0, 0.5)
        x[:, :, 4:5] = volume * mask
        return x

    def spread_simulation(self, x):
        # Adiciona ruído proporcional ao spread histórico
        spread = tf.random.normal(tf.shape(x), mean=0.0, stddev=self.max_spread)
        return x + spread * tf.constant([0.0001, 0.0001, 0.0001, 0.0001, 0.0])  # Apenas OHLC
