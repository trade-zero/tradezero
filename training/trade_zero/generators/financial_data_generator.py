import numpy as np
import tensorflow as tf


class FinancialDataGenerator(tf.keras.utils.Sequence):
    def __init__(self, data, lookback, batch_size, shuffle=True, augment=False):
        self.data = data
        self.lookback = lookback
        self.batch_size = batch_size
        self.shuffle = shuffle
        self.augment = augment
        self.indices = np.arange(len(data) - lookback)

    def __len__(self):
        return len(self.indices) // self.batch_size

    def __getitem__(self, idx):
        batch_indices = self.indices[idx * self.batch_size:(idx + 1) * self.batch_size]
        x = np.zeros((self.batch_size, self.lookback, self.data.shape[1]))
        y = np.zeros((self.batch_size, self.lookback, self.data.shape[1]))

        for i, start in enumerate(batch_indices):
            sequence = self.data[start:start + self.lookback]
            x[i] = sequence
            y[i] = sequence  # Autoencoder

            if self.augment:
                x[i] = self._augment_sequence(x[i])

        return x, y

    def _augment_sequence(self, sequence):
        # Adiciona ruído proporcional à volatilidade
        noise = np.random.normal(0, sequence.std(axis=0) * 0.1, sequence.shape)
        return sequence + noise
