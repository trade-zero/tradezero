import tensorflow as tf
from tensorflow.keras import layers


class TransformerLayer(tf.keras.layers.Layer):

    def __init__(self, num_heads, dim_embedding, dim_feedforward, dropout_rate=0.1, **kwargs):
        super(TransformerLayer, self).__init__(**kwargs)
        self.attention_layer = layers.MultiHeadAttention(num_heads, dim_embedding, dropout=dropout_rate)
        self.attention_dropout_layer = tf.keras.layers.Dropout(dropout_rate)
        self.attention_residual_connection_layer = layers.Add()
        self.attention_normalization_layer = tf.keras.layers.LayerNormalization(epsilon=1e-6)

        self.feedforward = tf.keras.Sequential([
            tf.keras.layers.Dense(dim_feedforward, activation='gelu'),
            tf.keras.layers.Dropout(dropout_rate),
            tf.keras.layers.Dense(dim_embedding)
        ])
        self.feedforward_dropout_layer = tf.keras.layers.Dropout(dropout_rate)
        self.feedforward_residual_connection_layer = layers.Add()
        self.feedforward_normalization_layer = tf.keras.layers.LayerNormalization(epsilon=1e-6)

        self.num_heads = num_heads
        self.dim_embedding = dim_embedding
        self.dim_feedforward = dim_feedforward
        self.dropout_rate = dropout_rate

    def call(self, inputs, training: bool = False, **kwargs):
        attention_output = self.attention_layer(inputs, inputs)
        attention_output = self.attention_dropout_layer(attention_output, training=training)
        attention_output = self.attention_residual_connection_layer([inputs, attention_output])
        attention_output = self.attention_normalization_layer(attention_output)

        feedforward_output = self.feedforward(attention_output)
        feedforward_output = self.feedforward_dropout_layer(feedforward_output, training=training)
        feedforward_output = self.feedforward_residual_connection_layer([attention_output, feedforward_output])
        feedforward_output = self.feedforward_normalization_layer(feedforward_output)

        return feedforward_output

    def get_config(self):
        config = {
            "num_heads": self.num_heads,
            "dim_embedding": self.dim_embedding,
            "dim_feedforward": self.dim_feedforward,
            "dropout_rate": self.dropout_rate,
        }
        return config

    @classmethod
    def from_config(cls, config):
        return cls(**config)
