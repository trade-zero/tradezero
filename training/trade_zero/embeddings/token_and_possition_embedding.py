import tensorflow as tf
from tensorflow.keras import layers


class TokenAndPositionEmbedding(layers.Layer):
    def __init__(self, max_len, vocab_size, embed_dim, **kwargs):
        super().__init__(**kwargs)
        self.token_emb = layers.Embedding(input_dim=vocab_size, output_dim=embed_dim)
        self.pos_emb = layers.Embedding(input_dim=max_len, output_dim=embed_dim)
        self.max_len = max_len
        self.vocab_size = vocab_size
        self.embed_dim = embed_dim

    def call(self, x, trainable: bool = False, **kwargs):
        max_len = tf.shape(x)[-1]
        # positions = np.arange(0, max_len)
        # positions = tf.arange(start=0, stop=max_len, step=1)
        positions = tf.range(start=0, limit=max_len, delta=1)
        positions = self.pos_emb(positions)
        x = self.token_emb(x)
        return x + positions

    def get_config(self):
        config = {
            "max_len": self.max_len,
            "vocab_size": self.vocab_size,
            "embed_dim": self.embed_dim,
        }

        return config

    @classmethod
    def from_config(cls, config):
        return cls(**config)
