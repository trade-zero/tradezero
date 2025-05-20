import numpy as np
import tensorflow as tf


class CustomDataGenerator(tf.keras.utils.Sequence):
    def __init__(
            self,
            dataset: np.array,
            num_bins: int,
            batch_size: int = 64,
            look_back: int = 3,
            shuffle=True,
            noise_std: float = 0.01,
            use_digitized: bool = False,
    ):
        bins = np.linspace(-3.2, 3.2, num_bins)

        self.dataset = dataset
        self.bins = bins
        self.batch_size = batch_size
        self.look_back = look_back
        self.shuffle = shuffle
        self.noise_std = noise_std
        self.use_digitized = use_digitized

        self.n = len(self.dataset)

    def on_epoch_end(self):
        pass

    def __getitem__(self, index):
        batches = []
        indexes = np.random.choice(np.arange(self.look_back, self.n - 1), size=self.batch_size, replace=False)
        for i in indexes:
            batch = self.dataset[i - self.look_back:i]
            noise = np.random.normal(loc=0.0, scale=self.noise_std, size=batch.shape)
            batch_with_noise = batch + noise

            if self.use_digitized:
                batch_with_noise = np.digitize(batch_with_noise, self.bins)

            batches.append(batch_with_noise)
        return np.array(batches), np.array(batches)

    def __len__(self):
        return self.n // self.batch_size
