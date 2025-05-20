import numpy as np

from get_candlestick import csv_repository
from alpha_zero.models import transform_model
from alpha_zero.models import latent_model

model_path = "../files/encoder.keras"
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

latent_layer_name = "latent-vector"
model1 = transform_model.load()
transform_model.build_encoder(config)
model2 = latent_model.build()

cols = 7
rows = 60
batch_size = 256
shape = (batch_size, rows, cols)

sample_input = csv_repository()

batches = []
for i in range(sample_input.shape[0] - rows):
    batches.append(sample_input[i:i + rows])

sample_input = np.array(batches)

latent_vector = model2.predict(sample_input, batch_size=batch_size)

print("Latent Vector Shape:", latent_vector.shape)
print("Latent Vector:\n", latent_vector.round(3))

reconstructed = model1.predict(sample_input, batch_size=batch_size)
erro = np.abs(reconstructed - sample_input)
# print(erro.round(2))
print("axis=0")
print(erro.mean(axis=(0, 1)).round(2))
