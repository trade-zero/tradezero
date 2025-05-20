import numpy as np
import tensorflow as tf


class MarketRegimeAnalyzerCallback(tf.keras.callbacks.Callback):
    def __init__(self, validation_data):
        super().__init__()
        self.val_data = validation_data
        self.regime_profiles = {
            'high_volatility': {'threshold': 0.015, 'lr_multiplier': 1.5},
            'low_volatility': {'threshold': 0.005, 'lr_multiplier': 0.7},
            'trending': {'correlation_threshold': 0.8}
        }

    def on_epoch_end(self, epoch, logs=None):
        # Analisa dados de validação
        x_val, y_val = self.val_data
        preds = self.model.predict(x_val)

        # Calcula regime atual
        volatility = tf.math.reduce_std(y_val[..., 3] - y_val[..., 0]).numpy()
        corr_open_close = np.corrcoef(y_val[..., 0].flatten(), y_val[..., 3].flatten())[0, 1]

        # Ajusta hiperparâmetros
        if volatility > self.regime_profiles['high_volatility']['threshold']:
            new_lr = self.model.optimizer.learning_rate * 1.5
            self.model.optimizer.learning_rate.assign(new_lr)
        elif corr_open_close > self.regime_profiles['trending']['correlation_threshold']:
            for layer in self.model.layers:
                if 'attention' in layer.name:
                    layer.trainable = True
