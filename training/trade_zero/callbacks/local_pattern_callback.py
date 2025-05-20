import tensorflow as tf


class LocalPatternCallback(tf.keras.callbacks.Callback):
    def on_batch_begin(self, batch, logs=None):
        # Aplica aumento de dados agressivo
        self.model.get_layer('financial_augmentation').p = 0.7

    def on_epoch_end(self, epoch, logs=None):
        # Congela camadas profundas
        if epoch > 15:
            for layer in self.model.layers[-4:]:
                layer.trainable = False
