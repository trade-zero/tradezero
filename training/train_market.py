import os
import tensorflow as tf
from tensorflow.keras import optimizers, callbacks

from alpha_zero.models import transform_model
from alpha_zero.generators import FinancialDataGenerator
from get_candlestick import csv_repository

tf.config.optimizer.set_jit(True)


def configure_training(config):
    model = transform_model.build_complete_model(config)

    optimizer = optimizers.AdamW(
        learning_rate=config['learning_rate'],
        weight_decay=config['weight_decay']
    )

    model.compile(
        optimizer=optimizer,
        loss='huber',
        metrics=['mae', "mse"]
    )

    if os.path.exists("files/best_model.h5"):
        model.load_weights("files/best_model.h5", by_name=True, skip_mismatch=True)

    return model


def train_model(model, train_data, val_data, config):
    train_gen = FinancialDataGenerator(
        train_data,
        lookback=config['lookback'],
        batch_size=config['batch_size'],
        augment=True
    )

    val_gen = FinancialDataGenerator(
        val_data,
        lookback=config['lookback'],
        batch_size=config['batch_size']
    )

    lr_scheduler = callbacks.ReduceLROnPlateau(
        factor=0.5,
        patience=5,
        min_lr=1e-6
    )

    checkpoint = callbacks.ModelCheckpoint(
        'files/best_model.h5',
        save_best_only=True,
        monitor='val_loss',
    )

    early_stop = callbacks.EarlyStopping(
        patience=20,
        restore_best_weights=True
    )

    history = model.fit(
        train_gen,
        epochs=config['epochs'],
        validation_data=val_gen,
        callbacks=[lr_scheduler, checkpoint, early_stop],
        verbose=1
    )

    return history


if __name__ == '__main__':
    config = {
        'lookback': 60,
        'n_features': 7,
        'latent_dim': 128,
        'num_layers': 1,
        'num_heads': 8,
        'dim_embedding': 256,
        'dim_feedforward': 512,
        'dropout_rate': 0.15,
        'batch_size': 256,
        'epochs': 100,
        'learning_rate': 3e-4,
        'weight_decay': 1e-5
    }

    # Carregar dados
    data = csv_repository(add_time=True)

    # Split temporal
    split = int(0.9 * len(data))
    train_data = data[:split]
    val_data = data[split:]

    # Treinamento
    model = configure_training(config)
    model.summary()
    history = train_model(model, train_data, val_data, config)

    # Salvar modelo final
    model.save('files/auto-encoder.keras', save_format="h5")
