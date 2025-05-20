import os

from tensorflow.keras import layers
from tensorflow.keras import models

from ..layers import TransformerLayer, PositionalEncodingLayer, MarketPositionalEncodingLayer, \
    FinancialAugmentationLayer

MODEL_PATH = "files/auto-encoder.keras"


def exists_model_trained() -> bool:
    return os.path.exists(MODEL_PATH)


def build_encoder(input_shape, num_layers, num_heads, dim_embedding, dim_feedforward, dropout):
    inputs = layers.Input(shape=input_shape)
    x = layers.Dense(dim_embedding)(inputs)
    x = MarketPositionalEncodingLayer(input_shape[0], dim_embedding)(x)

    for _ in range(num_layers):
        x = TransformerLayer(num_heads, dim_embedding, dim_feedforward, dropout)(x)

    x = layers.GlobalAveragePooling1D()(x)
    x = layers.Flatten()(x)
    return models.Model(inputs, x, name='encoder')


def build_decoder(output_shape, num_layers, num_heads, dim_embedding, dim_feedforward, dropout):
    latent_input = layers.Input(shape=(dim_embedding,))
    # x = layers.RepeatVector(output_shape[0])(latent_input)
    x = layers.Dense(dim_embedding * output_shape[0])(latent_input)
    x = layers.Reshape((output_shape[0], dim_embedding))(x)
    x = MarketPositionalEncodingLayer(output_shape[0], dim_embedding)(x)

    for _ in range(num_layers):
        x = TransformerLayer(num_heads, dim_embedding, dim_feedforward, dropout)(x)

    x = layers.TimeDistributed(layers.Dense(output_shape[1]))(x)
    return models.Model(latent_input, x, name='decoder')


def build_complete_model(config):
    # Encoder
    encoder = build_encoder(
        input_shape=(config['lookback'], config['n_features']),
        num_layers=config['num_layers'],
        num_heads=config['num_heads'],
        dim_embedding=config['dim_embedding'],
        dim_feedforward=config['dim_feedforward'],
        dropout=config['dropout_rate']
    )

    # Decoder
    decoder = build_decoder(
        output_shape=(config['lookback'], config['n_features']),
        num_layers=config['num_layers'],
        num_heads=config['num_heads'],
        dim_embedding=config['dim_embedding'],
        dim_feedforward=config['dim_feedforward'],
        dropout=config['dropout_rate']
    )

    # Conexão com espaço latente explícito
    inputs = encoder.input
    encoded = encoder(inputs)

    # Camada de projeção latente (aqui o espaço latente é criado)
    latent_projection = layers.Dense(config['latent_dim'], name='latent_space')(encoded)

    # Reconversão para dimensão esperada pelo decoder
    projection_back = layers.Dense(config['dim_embedding'], name="projection_back")(latent_projection)

    # Decodificação
    decoded = decoder(projection_back)

    return models.Model(encoder.input, decoded, name='autoencoder')


def build(
        lookback: int,
        n_features: int,
        latent_dim: int,
        num_layers=4,
        num_heads=8,
        dim_embedding=256,
        dim_feedforward=1024,
        dropout_rate=0.1
):
    # Entrada
    input_layer = layers.Input(shape=(lookback, n_features,))

    # Pré-processamento
    x = layers.Dense(dim_embedding)(input_layer)
    x = PositionalEncodingLayer(lookback, dim_embedding)(x)

    # Encoder Stack
    for i in range(num_layers):
        x = TransformerLayer(
            num_heads,
            dim_embedding,
            dim_feedforward,
            dropout_rate,
            name=f"encoder_layer_{i}"
        )(x)

    # Latent Space
    x = layers.GlobalAveragePooling1D()(x)
    x = layers.Dense(512, activation='gelu', kernel_regularizer='l2')(x)
    latent_vector = layers.Dense(latent_dim, activation='gelu', name="latent-vector")(x)

    # Decoder
    x = layers.RepeatVector(lookback)(latent_vector)
    x = layers.TimeDistributed(
        layers.Dense(dim_embedding, activation='gelu')  # Adaptação dimensional
    )(x)

    # Decoder Stack
    for i in range(num_layers):
        x = TransformerLayer(
            num_heads,
            dim_embedding,
            dim_feedforward,
            dropout_rate,
            name=f"decoder_layer_{i}"
        )(x)

    # Saída
    output_layer = layers.TimeDistributed(layers.Dense(n_features, activation='linear'), name="Output")(x)

    model = models.Model(inputs=input_layer, outputs=output_layer)
    return model


def load() -> models.Model:
    custom_objects = {
        "TransformerLayer": TransformerLayer,
        "PositionalEncodingLayer ": PositionalEncodingLayer,
    }
    model = models.load_model(MODEL_PATH, custom_objects=custom_objects)
    return model


def save_model(model: models):
    model.save(MODEL_PATH, save_format="h5")
