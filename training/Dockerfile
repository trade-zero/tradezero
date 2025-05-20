FROM tensorflow/tensorflow:2.14.0-gpu

WORKDIR /app
ENV APP_PORT=8099
ENV APP_HOST="0.0.0.0"

ADD .. /app

RUN mkdir files

RUN pip install -r requirements.txt

ENTRYPOINT ["python", "train_market.py"]
