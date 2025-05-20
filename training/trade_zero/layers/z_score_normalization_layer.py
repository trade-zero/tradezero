import tensorflow as tf
from tensorflow.keras import layers


class ZScoreNormalizationLayer(layers.Layer):
    def __init__(self, **kwargs):
        super(ZScoreNormalizationLayer, self).__init__(**kwargs)

    def call(self, x, trainable: bool = False, **kwargs):
        mean = tf.reduce_mean(x, axis=1, keepdims=True)
        std = tf.math.reduce_std(x, axis=1, keepdims=True)
        z_scores = (x - mean) / (std + 1e-6)
        return z_scores
