import numpy as np
import tensorflow as tf
from tensorflow.keras import layers


class PositionalEncodingLayer(layers.Layer):
    def __init__(self, max_len, d_model):
        super().__init__()
        self.max_len = max_len
        self.d_model = d_model
        self.pos_encoding = self.positional_encoding()

    def positional_encoding(self):
        position = np.arange(self.max_len)[:, np.newaxis]
        div_term = np.exp(np.arange(0, self.d_model, 2) * (-np.log(10000.0) / self.d_model))
        pe = np.zeros((self.max_len, self.d_model))
        pe[:, 0::2] = np.sin(position * div_term)
        pe[:, 1::2] = np.cos(position * div_term)
        return tf.cast(pe, dtype=tf.float32)

    def call(self, x, training: bool = False, **kwargs):
        return x + self.pos_encoding[:tf.shape(x)[1], :]

    def get_config(self):
        return {"max_len": self.max_len, "d_model": self.d_model}

    @classmethod
    def from_config(cls, config):
        return cls(**config)
