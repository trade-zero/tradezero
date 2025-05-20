from tensorflow.keras import optimizers

from get_candlestick import csv_repository
from alpha_zero.generators import CustomDataGenerator
from alpha_zero.models import transform_model
from alpha_zero.schedulers import WarmUpSineDecayScheduler
from alpha_zero.callbacks import SelectiveFreezingCallback

if __name__ == '__main__':
    xs = csv_repository()
    NUM_BINS = 100_000
    TEST_PERCENT = 0.1
    TEST_SIZE = int(xs.shape[0] * TEST_PERCENT)
    TRAIN_SIZE = xs.shape[0] - TEST_SIZE

    train_data, val_data = xs[TEST_SIZE:], xs[:TEST_SIZE]

    # Hiperparâmetros
    LOOK_BACK = 60
    NUM_FEATURES = xs.shape[1]
    LATENT_DIM = 128
    NUM_LAYERS = 1
    BATCH_SIZE = 256
    EPOCHS = 200
    NUM_HEADS = 8
    DIM_EMBEDDING = 512
    DIM_FEEDFORWARD = 1024
    DROPOUT_RATE = 0.15
    LEARNING_RATE = 0.001

    print("Construindo modelo")
    autoencoder_model = transform_model.build(
        lookback=LOOK_BACK,
        n_features=NUM_FEATURES,
        latent_dim=LATENT_DIM,
        num_layers=NUM_LAYERS,
        num_heads=NUM_HEADS,
        dim_embedding=DIM_EMBEDDING,
        dim_feedforward=DIM_FEEDFORWARD,
        dropout_rate=DROPOUT_RATE
    )

    if transform_model.exists_model_trained():
        print("Modelo encontrado! Carregando o modelo treinado...")
        autoencoder_model.load_weights(transform_model.MODEL_PATH, by_name=True, skip_mismatch=True)

        # Congela camadas pré-treinadas
        for layer in autoencoder_model.layers:
            if "encoder_layer_" in layer.name or "decoder_layer_" in layer.name:
                layer.trainable = False

    # Compila o modelo
    autoencoder_model.compile(
        optimizer=optimizers.Adam(learning_rate=LEARNING_RATE),
        loss="mse",  # Mean Squared Error
        metrics=["mae"]  # Mean Absolute Error
    )

    # Exibe o resumo do modelo
    autoencoder_model.summary()

    # Instancia o gerador de dados
    train_generator = CustomDataGenerator(
        dataset=train_data,
        num_bins=NUM_BINS,
        batch_size=BATCH_SIZE,
        look_back=LOOK_BACK
    )

    test_generator = CustomDataGenerator(
        dataset=val_data,
        num_bins=NUM_BINS,
        batch_size=BATCH_SIZE,
        look_back=LOOK_BACK
    )

    total_steps = (TRAIN_SIZE // BATCH_SIZE) * EPOCHS
    warmup_steps = int(0.1 * total_steps)

    callbacks = [
        SelectiveFreezingCallback(num_layers=NUM_LAYERS),
        WarmUpSineDecayScheduler(3e-3, warmup_steps, total_steps)
    ]

    # Treina o modelo
    history = autoencoder_model.fit(
        train_generator,
        epochs=EPOCHS,
        validation_data=test_generator,
        callbacks=[callbacks],
    )

    # Salva o modelo (opcional)
    transform_model.save_model(autoencoder_model)

    # for x, y in test_generator:
    #     predict = autoencoder_model.predict(x)
    #     print(predict)
