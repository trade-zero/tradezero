from tensorflow.keras import models

from . import transform_model

model_path = "files/encoder.keras"


def build():
    latent_layer_name = "latent-vector"
    model = transform_model.load()
    latent_model = models.Model(inputs=model.input, outputs=model.get_layer(latent_layer_name).output)
    return latent_model
