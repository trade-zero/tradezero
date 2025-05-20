import tensorflow as tf


def candlestick_loss(y_true, y_pred):
    # Ponderar erros diferente para cada componente
    open_loss = tf.keras.losses.mae(y_true[..., 0], y_pred[..., 0]) * 0.8
    high_loss = tf.keras.losses.mae(y_true[..., 1], y_pred[..., 1]) * 1.2
    low_loss = tf.keras.losses.mae(y_true[..., 2], y_pred[..., 2]) * 1.2
    close_loss = tf.keras.losses.mae(y_true[..., 3], y_pred[..., 3]) * 1.0
    volume_loss = tf.keras.losses.mae(y_true[..., 4], y_pred[..., 4]) * 0.5

    # Perda composta
    return open_loss + high_loss + low_loss + close_loss + volume_loss
