import tensorflow as tf
import numpy as np
from tensorflow.keras.callbacks import Callback


class WarmUpSineDecayScheduler(Callback):
    def __init__(self, initial_lr, warmup_steps, total_steps, update_interval=1):
        super().__init__()
        self.initial_lr = initial_lr
        self.warmup_steps = warmup_steps
        self.total_steps = total_steps
        self.update_interval = update_interval

    def on_train_batch_begin(self, batch, logs=None):
        step = self.model.optimizer.iterations.numpy()
        if step % self.update_interval == 0:
            if step < self.warmup_steps:
                # Fase de Warm-up (crescimento linear)
                lr = self.initial_lr * (step / self.warmup_steps)
            else:
                # Fase de Decaimento Senoidal
                progress = (step - self.warmup_steps) / (self.total_steps - self.warmup_steps)
                lr = self.initial_lr * 0.5 * (1 + np.cos(np.pi * progress))  # Senoide decaindo

            tf.keras.backend.set_value(self.model.optimizer.learning_rate, lr)

    def on_epoch_end(self, epoch, logs=None):
        current_lr = tf.keras.backend.get_value(self.model.optimizer.learning_rate)
        print(f"\nEpoch {epoch + 1}: Learning Rate = {current_lr:.6f}")


if __name__ == '__main__':
    import matplotlib.pyplot as plt

    batch_size = 256
    num_epochs = 50
    total_steps = (124_000 // batch_size) * num_epochs
    warmup_steps = int(0.1 * total_steps)

    steps = np.arange(total_steps)
    lr_values = []

    for step in steps:
        if step < warmup_steps:
            lr = 3e-3 * (step / warmup_steps)
        else:
            progress = (step - warmup_steps) / (total_steps - warmup_steps)
            lr = 3e-3 * 0.5 * (1 + np.cos(np.pi * progress))
        lr_values.append(lr)

    plt.plot(steps, lr_values)
    plt.title("Learning Rate Scheduler (Warm-Up + Senoide)")
    plt.xlabel("Steps")
    plt.ylabel("Learning Rate")
    plt.show()
