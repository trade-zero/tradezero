from tensorflow.keras import models

from training.trade_zero.callbacks import LocalPatternCallback


def phase_training(model: models.Model, train_data, val_data):
    # Fase 1 - Padrões Locais (30 épocas)
    model.fit(train_data, epochs=30, validation_data=val_data,
              callbacks=[LocalPatternCallback()])

    # Fase 2 - Dependências de Longo Prazo (20 épocas)
    for layer in model.layers:
        if 'attention' in layer.name:
            layer.trainable = True
    model.fit(train_data, epochs=20, validation_data=val_data,
              callbacks=[GlobalContextCallback()])

    # Fase 3 - Ajuste Fino (10 épocas)
    model.compile(optimizer=tf.keras.optimizers.Adam(1e-5))
    model.fit(train_data, epochs=10, validation_data=val_data,
              callbacks=[FineTuningCallback()])
