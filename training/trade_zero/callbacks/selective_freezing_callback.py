import tensorflow as tf


class SelectiveFreezingCallback(tf.keras.callbacks.Callback):
    def __init__(self, num_layers: int, num_step: int = 10):
        super().__init__()
        self.num_layers = num_layers - 1
        self.num_step = num_step

    def on_epoch_begin(self, epoch, logs=None):
        if self.num_layers < 0:
            return

        updated: bool = False
        if epoch % self.num_step == 0:
            for layer in self.model.layers:
                if f"encoder_layer_{self.num_layers}" in layer.name or f"decoder_layer_{self.num_layers}" in layer.name:
                    print(f"released layer: {layer.name}")
                    updated = True
                    layer.trainable = True

        if updated:
            self.num_layers -= 1
