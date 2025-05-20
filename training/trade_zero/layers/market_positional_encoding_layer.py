import numpy as np
import tensorflow as tf
from tensorflow.keras import layers


class MarketPositionalEncodingLayer(layers.Layer):
    def __init__(self, max_len, d_model, market_hours=78, **kwargs):
        super().__init__(**kwargs)
        self.max_len = max_len
        self.d_model = d_model
        self.market_hours = market_hours  # 78 períodos de 5min (9h às 16h)
        position = np.arange(max_len)[:, np.newaxis]
        div_term = np.exp(np.arange(0, d_model, 2) * (-np.log(10000.0) / d_model))
        pe = np.zeros((max_len, d_model))
        pe[:, 0::2] = np.sin(position * div_term)
        pe[:, 1::2] = np.cos(position * div_term)
        self.pe = tf.cast(pe, tf.float32)

    def call(self, x, training: bool = False, **kwargs):
        seq_len = tf.shape(x)[1]
        x += self.pe[:seq_len, :]
        # Adiciona codificação de horário de mercado
        market_mask = tf.where(tf.range(seq_len) < self.market_hours, 1.0, 0.5)
        return x * market_mask[:, tf.newaxis]

    def get_config(self):
        return {"max_len": self.max_len, "d_model": self.d_model, "market_hours": self.market_hours}

    @classmethod
    def from_config(cls, config):
        return cls(**config)
