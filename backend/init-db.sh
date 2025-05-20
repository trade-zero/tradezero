#!/bin/bash

# Script para inicializar o schema alpha_zero e tabelas necessárias
# Este script será executado automaticamente quando o container PostgreSQL iniciar

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
   CREATE OR REPLACE FUNCTION add_datetime_dim()
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    last_dt    TIMESTAMP;
    start_year INT;
    start_ts   TIMESTAMP;
    end_ts     TIMESTAMP;
BEGIN
    SELECT MAX(datetime)
    INTO last_dt
    FROM datetime_dim;

    IF last_dt IS NULL THEN
        start_year := 2017;
    ELSE
        start_year := EXTRACT(YEAR FROM last_dt)::INT + 1;
    END IF;

    start_ts := MAKE_TIMESTAMP(start_year, 1, 1, 0, 0, 0);
    end_ts := MAKE_TIMESTAMP(start_year, 12, 31, 23, 59, 0);

    INSERT INTO datetime_dim (datetime_id,
                              datetime,
                              epoch,
                              day_of_week,
                              day_of_month,
                              day_of_year,
                              week_of_month,
                              week_of_year,
                              month_,
                              quarter_,
                              year,
                              start_of_week,
                              start_of_month,
                              is_weekend,
                              hour,
                              minute)
    SELECT TO_CHAR(datum, 'yyyymmddHH24MI')::BIGINT AS datetime_id,
           datum                                    AS datetime,
           EXTRACT(EPOCH FROM datum)::BIGINT        AS epoch,
           EXTRACT(ISODOW FROM datum)::SMALLINT     AS day_of_week,
           EXTRACT(DAY FROM datum)::SMALLINT        AS day_of_month,
           EXTRACT(DOY FROM datum)::SMALLINT        AS day_of_year,
           TO_CHAR(datum, 'W')::SMALLINT            AS week_of_month,
           EXTRACT(WEEK FROM datum)::SMALLINT       AS week_of_year,
           EXTRACT(MONTH FROM datum)::SMALLINT      AS month_,
           EXTRACT(QUARTER FROM datum)::SMALLINT    AS quarter_,
           EXTRACT(YEAR FROM datum)::SMALLINT       AS year,
           DATE_TRUNC('week', datum)::DATE          AS start_of_week,
           DATE_TRUNC('month', datum)::DATE         AS start_of_month,
           (EXTRACT(ISODOW FROM datum) IN (6, 7))   AS is_weekend,
           EXTRACT(HOUR FROM datum)::SMALLINT       AS hour,
           EXTRACT(MINUTE FROM datum)::SMALLINT     AS minute
    FROM GENERATE_SERIES(start_ts, end_ts, INTERVAL '1 minute') AS series(datum)
    ON CONFLICT (datetime_id) DO NOTHING;
END;
$$;

CREATE TABLE datetime_dim
(
    datetime_id    BIGINT PRIMARY KEY,
    datetime       TIMESTAMP NOT NULL,
    epoch          BIGINT    NOT NULL,
    day_of_week    SMALLINT  NOT NULL,
    day_of_month   SMALLINT  NOT NULL,
    day_of_year    SMALLINT  NOT NULL,
    week_of_month  SMALLINT  NOT NULL,
    week_of_year   SMALLINT  NOT NULL,
    month_         SMALLINT  NOT NULL,
    quarter_       SMALLINT  NOT NULL,
    year           SMALLINT  NOT NULL,
    start_of_week  DATE      NOT NULL,
    start_of_month DATE      NOT NULL,
    is_weekend     BOOLEAN   NOT NULL,
    hour           SMALLINT  NOT NULL,
    minute         SMALLINT  NOT NULL
);

DO
$$
    BEGIN
        FOR ano IN 2017..2025
            LOOP
                RAISE NOTICE 'Processando ano: %', ano;
                PERFORM add_datetime_dim();
            END LOOP;
    END
$$;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE trade_direction_type AS ENUM ('LONG', 'SHORT', 'WAIT');
CREATE TYPE trade_action_type AS ENUM ('hold', 'open', 'close');
CREATE TYPE trade_asset_type AS ENUM ('WIN$', 'WDO$');
CREATE TYPE trade_time_frame_type AS ENUM ('m1', 'm5', 'm15', 'm30', 'H1', 'H4', 'D1', 'W1');
CREATE TYPE order_status_type AS ENUM ('pending', 'filled', 'canceled', 'rejected', 'partially_filled');
CREATE TYPE order_type AS ENUM ('market', 'limit', 'stop');

CREATE TABLE IF NOT EXISTS stock_dim
(
    stock_uuid  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    asset_type  trade_asset_type NOT NULL,
    name        VARCHAR(50)      NOT NULL,
    tick_size   DOUBLE PRECISION NOT NULL CHECK (tick_size > 0),
    tick_value  DOUBLE PRECISION NOT NULL CHECK (tick_value >= 0),
    volume_size DOUBLE PRECISION NOT NULL CHECK (volume_size > 0),
    UNIQUE (asset_type)
);
CREATE INDEX IF NOT EXISTS stock_dim_asset_idx ON stock_dim (asset_type);
INSERT INTO stock_dim(asset_type, name, tick_size, tick_value, volume_size)
VALUES ('WDO$', 'Mini Dólar', 0.5, 5, 1),
       ('WIN$', 'Mini Índice', 5.0, 1, 1);

CREATE TABLE IF NOT EXISTS time_frame_dim
(
    time_frame  trade_time_frame_type PRIMARY KEY,
    description VARCHAR(50) NOT NULL
);
CREATE INDEX IF NOT EXISTS time_frame_dim_time_frame_idx ON stock_dim (asset_type);
INSERT INTO time_frame_dim (time_frame, description)
VALUES ('m1', '1 Minuto'),
       ('m5', '5 Minutos'),
       ('m15', '15 Minutos'),
       ('m30', '30 Minutos'),
       ('H1', '1 Hora'),
       ('H4', '4 Horas'),
       ('D1', '1 Dia'),
       ('W1', '1 Semana');

CREATE TABLE IF NOT EXISTS action_dim
(
    action_dim_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    asset_type      trade_asset_type     NOT NULL,
    direction_type  trade_direction_type NOT NULL,
    action_type     trade_action_type    NOT NULL,
    volume          DOUBLE PRECISION     NOT NULL,
    UNIQUE (asset_type, direction_type, action_type, volume)
);
INSERT INTO action_dim(asset_type, direction_type, action_type, volume)
VALUES ('WIN$', 'LONG', 'open', 0),
       ('WIN$', 'LONG', 'open', 1),
       ('WIN$', 'LONG', 'open', 2),
       ('WIN$', 'LONG', 'open', 3),
       ('WIN$', 'LONG', 'open', 4),
       ('WIN$', 'LONG', 'open', 5),
       ('WIN$', 'LONG', 'close', 0),
       ('WIN$', 'LONG', 'close', 1),
       ('WIN$', 'LONG', 'close', 2),
       ('WIN$', 'LONG', 'close', 3),
       ('WIN$', 'LONG', 'close', 4),
       ('WIN$', 'LONG', 'close', 5),
       ('WIN$', 'SHORT', 'open', 0),
       ('WIN$', 'SHORT', 'open', 1),
       ('WIN$', 'SHORT', 'open', 2),
       ('WIN$', 'SHORT', 'open', 3),
       ('WIN$', 'SHORT', 'open', 4),
       ('WIN$', 'SHORT', 'open', 5),
       ('WIN$', 'SHORT', 'close', 0),
       ('WIN$', 'SHORT', 'close', 1),
       ('WIN$', 'SHORT', 'close', 2),
       ('WIN$', 'SHORT', 'close', 3),
       ('WIN$', 'SHORT', 'close', 4),
       ('WIN$', 'SHORT', 'close', 5),
       ('WIN$', 'WAIT', 'hold', 0),
       ('WIN$', 'WAIT', 'hold', 1),
       ('WIN$', 'WAIT', 'hold', 2),
       ('WIN$', 'WAIT', 'hold', 3),
       ('WIN$', 'WAIT', 'hold', 4),
       ('WIN$', 'WAIT', 'hold', 5),
       ('WIN$', 'WAIT', 'hold', 6),
       ('WIN$', 'WAIT', 'hold', 7),
       ('WIN$', 'WAIT', 'hold', 8),
       ('WIN$', 'WAIT', 'hold', 9),
       ('WIN$', 'WAIT', 'hold', 10),
       ('WIN$', 'WAIT', 'hold', 11),
       ('WIN$', 'WAIT', 'hold', 12);

CREATE TABLE IF NOT EXISTS data_feed_fact
(
    data_feed_uuid UUID PRIMARY KEY,
    name           TEXT NOT NULL
);
INSERT INTO data_feed_fact (data_feed_uuid, "name")
VALUES ('c979e1be-2a9f-4b23-b760-af30aeb82186', 'backup');


CREATE TABLE IF NOT EXISTS candlestick_fact
(
    data_feed_uuid   UUID                  NOT NULL REFERENCES data_feed_fact (data_feed_uuid) ON DELETE CASCADE,
    trade_asset      trade_asset_type      NOT NULL,
    trade_time_frame trade_time_frame_type NOT NULL,
    datetime_id      BIGINT                NOT NULL REFERENCES datetime_dim (datetime_id),
    open             DOUBLE PRECISION      NOT NULL,
    high             DOUBLE PRECISION      NOT NULL,
    low              DOUBLE PRECISION      NOT NULL,
    close            DOUBLE PRECISION      NOT NULL,
    volume           DOUBLE PRECISION      NOT NULL,
    PRIMARY KEY (data_feed_uuid, trade_asset, trade_time_frame, datetime_id)
) PARTITION BY LIST (trade_time_frame);
CREATE TABLE candlestick_fact_m1 PARTITION OF candlestick_fact FOR VALUES IN ('m1');
CREATE TABLE candlestick_fact_m5 PARTITION OF candlestick_fact FOR VALUES IN ('m5');
CREATE TABLE candlestick_fact_m15 PARTITION OF candlestick_fact FOR VALUES IN ('m15');
CREATE TABLE candlestick_fact_m30 PARTITION OF candlestick_fact FOR VALUES IN ('m30');
CREATE TABLE candlestick_fact_h1 PARTITION OF candlestick_fact FOR VALUES IN ('H1');
CREATE TABLE candlestick_fact_h4 PARTITION OF candlestick_fact FOR VALUES IN ('H4');
CREATE TABLE candlestick_fact_d1 PARTITION OF candlestick_fact FOR VALUES IN ('D1');
CREATE TABLE candlestick_fact_w1 PARTITION OF candlestick_fact FOR VALUES IN ('W1');

CREATE INDEX IF NOT EXISTS candlestick_fact_data_feed_idx ON candlestick_fact (data_feed_uuid);
CREATE INDEX IF NOT EXISTS candlestick_fact_trade_asset_idx ON candlestick_fact (trade_asset);
CREATE INDEX IF NOT EXISTS candlestick_fact_trade_time_frame_idx ON candlestick_fact (trade_time_frame);
CREATE INDEX IF NOT EXISTS candlestick_fact_dt_idx ON candlestick_fact (datetime_id);
CREATE INDEX IF NOT EXISTS candlestick_fact_m5_dt_idx ON candlestick_fact_m5 (datetime_id);

CREATE TABLE IF NOT EXISTS agent_dim
(
    agent_dim_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name           VARCHAR(125) NOT NULL
);
INSERT INTO agent_dim(agent_dim_uuid, name)
VALUES ('b80e915a-af7a-45bd-963f-b25b85bfc67b', 'player'),
       ('ab2ea4a6-c615-4f3c-8202-63406c6d9c7a', 'opponent');

CREATE TABLE IF NOT EXISTS trade_zero_dim
(
    trade_zero_dim_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trade_asset         trade_asset_type[]    NOT NULL CHECK (array_length(trade_asset, 1) > 0),
    trade_time_frame    trade_time_frame_type NOT NULL,
    balance_initial     DOUBLE PRECISION      NOT NULL CHECK (balance_initial > 0),
    drawdown            DOUBLE PRECISION      NOT NULL CHECK (drawdown BETWEEN 0 AND 1),
    max_volume          DOUBLE PRECISION      NOT NULL CHECK (max_volume > 0),
    max_hold            INTEGER               NOT NULL CHECK (max_hold > 0),
    look_back           INTEGER               NOT NULL CHECK (look_back BETWEEN 1 AND 540),
    look_forward        INTEGER               NOT NULL CHECK (look_forward BETWEEN 1 AND 540),
    back_propagate_size INTEGER               NOT NULL CHECK (back_propagate_size BETWEEN 1 AND 108),
    max_episode         INTEGER               NOT NULL CHECK (max_episode > 0)
);
CREATE INDEX IF NOT EXISTS trade_zero_dim_trade_asset_idx ON trade_zero_dim (trade_asset);
CREATE INDEX IF NOT EXISTS trade_zero_dim_trade_time_frame_idx ON trade_zero_dim (trade_time_frame);
INSERT INTO trade_zero_dim
VALUES ('386adf36-0584-44e1-8ae4-0b8fc4686b04',
        ARRAY ['WIN$']::trade_asset_type[],
        'm5',
        30000,
        0.3,
        5,
        12,
        256,
        365,
        12,
        512);

CREATE TABLE IF NOT EXISTS trade_zero_fact
(
    trade_zero_fact_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trade_zero_dim_uuid  UUID NOT NULL REFERENCES trade_zero_dim (trade_zero_dim_uuid),
    agent_dim_uuid       UUID NOT NULL REFERENCES agent_dim (agent_dim_uuid),
    epoch                INT CHECK (epoch >= 0),
    trained              BOOL             DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS trade_zero_fact_trade_zero_dim_idx ON trade_zero_fact (trade_zero_dim_uuid);
CREATE INDEX IF NOT EXISTS trade_zero_fact_agent_dim_idx ON trade_zero_fact (agent_dim_uuid);
CREATE INDEX IF NOT EXISTS trade_zero_fact_epoch_idx ON trade_zero_fact (epoch);
INSERT INTO trade_zero_fact (trade_zero_fact_uuid, trade_zero_dim_uuid, agent_dim_uuid, epoch, trained)
VALUES ('def4d891-3654-4121-8dbc-93b2bc1dac8e', '386adf36-0584-44e1-8ae4-0b8fc4686b04', 'b80e915a-af7a-45bd-963f-b25b85bfc67b', 0, FALSE);

CREATE TABLE IF NOT EXISTS portfolio_fact
(
    portfolio_uuid       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trade_zero_fact_uuid UUID         NOT NULL REFERENCES trade_zero_fact (trade_zero_fact_uuid),
    name                 VARCHAR(255) NOT NULL,
    description          TEXT
);
CREATE INDEX IF NOT EXISTS portfolio_fact_trade_idx ON portfolio_fact (trade_zero_fact_uuid);

CREATE TABLE IF NOT EXISTS order_venue_dim
(
    order_venue_dim_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    exchange             VARCHAR(50) NOT NULL CHECK (exchange <> ''),
    broker               VARCHAR(50) NOT NULL CHECK (broker <> ''),
    platform             VARCHAR(50) NOT NULL CHECK (platform <> '')
);
INSERT INTO order_venue_dim (exchange, broker, platform)
VALUES ('B3', 'XP', 'TradeZero');

CREATE TABLE IF NOT EXISTS order_dim
(
    order_dim_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_type     order_type           NOT NULL,
    direction_type trade_direction_type NOT NULL,
    action_type    trade_action_type    NOT NULL,
    asset_type     trade_asset_type     NOT NULL,
    volume         DOUBLE PRECISION     NOT NULL CHECK (volume > 0),
    UNIQUE (order_type, direction_type, action_type, asset_type, volume)
);
CREATE INDEX IF NOT EXISTS order_dim_order_type_idx ON order_dim (order_type);
CREATE INDEX IF NOT EXISTS order_dim_direction_idx ON order_dim (direction_type);
CREATE INDEX IF NOT EXISTS order_dim_asset_idx ON order_dim (asset_type);
INSERT INTO order_dim (order_type, direction_type, action_type, asset_type, volume)
VALUES ('market', 'LONG', 'open', 'WIN$', 1),
       ('market', 'LONG', 'close', 'WIN$', 1),
       ('market', 'LONG', 'hold', 'WIN$', 1),
       ('market', 'SHORT', 'open', 'WIN$', 1),
       ('market', 'SHORT', 'close', 'WIN$', 1),
       ('market', 'SHORT', 'hold', 'WIN$', 1),
       ('market', 'WAIT', 'open', 'WIN$', 1),
       ('market', 'WAIT', 'close', 'WIN$', 1),
       ('market', 'WAIT', 'hold', 'WIN$', 1);

CREATE TABLE IF NOT EXISTS order_fact
(
    order_fact_uuid      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_dim_uuid       UUID              NOT NULL REFERENCES order_dim (order_dim_uuid),
    order_venue_dim_uuid UUID              NOT NULL REFERENCES order_venue_dim (order_venue_dim_uuid),
    datetime_id          BIGINT            NOT NULL REFERENCES datetime_dim (datetime_id),
    portfolio_uuid       UUID              NOT NULL REFERENCES portfolio_fact (portfolio_uuid),
    order_status         order_status_type NOT NULL,
    executed_price       DOUBLE PRECISION,
    limit_price          DOUBLE PRECISION,
    stop_price           DOUBLE PRECISION,
    fees                 DOUBLE PRECISION CHECK (fees >= 0),
    slippage             DOUBLE PRECISION,
    latency_ms           INT CHECK (latency_ms >= 0)
);
CREATE INDEX IF NOT EXISTS order_routing_order_idx ON order_fact (order_dim_uuid);
CREATE INDEX IF NOT EXISTS order_venue_dim_idx ON order_fact (order_venue_dim_uuid);
CREATE INDEX IF NOT EXISTS order_routing_dt_idx ON order_fact (datetime_id);
CREATE INDEX IF NOT EXISTS order_routing_portfolio_idx ON order_fact (portfolio_uuid);
CREATE INDEX IF NOT EXISTS order_routing_status_idx ON order_fact (order_status);

CREATE TABLE IF NOT EXISTS balance_fact
(
    balance_uuid   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    portfolio_uuid UUID             NOT NULL REFERENCES portfolio_fact (portfolio_uuid),
    datetime_id    BIGINT           NOT NULL REFERENCES datetime_dim (datetime_id),
    initial        DOUBLE PRECISION NOT NULL CHECK (initial >= 0),
    current        DOUBLE PRECISION NOT NULL CHECK (current >= 0),
    max            DOUBLE PRECISION NOT NULL CHECK (max >= 0),
    min            DOUBLE PRECISION NOT NULL CHECK (min >= 0),
    UNIQUE (datetime_id, portfolio_uuid)
);
CREATE INDEX IF NOT EXISTS balance_fact_portfolio_idx ON balance_fact (portfolio_uuid);
CREATE INDEX IF NOT EXISTS balance_fact_dt_idx ON balance_fact (datetime_id);

CREATE TABLE IF NOT EXISTS position_dim
(
    position_dim_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    asset_type        trade_asset_type     NOT NULL,
    direction_type    trade_direction_type NOT NULL,
    value            DOUBLE PRECISION,
    UNIQUE (asset_type, direction_type, value)
);
CREATE INDEX IF NOT EXISTS position_dim_asset_idx ON position_dim (asset_type);
CREATE INDEX IF NOT EXISTS position_dim_direction_idx ON position_dim (direction_type);
INSERT INTO position_dim (asset_type, direction_type, value)
VALUES ('WIN$', 'LONG', 0),
       ('WIN$', 'LONG', 1),
       ('WIN$', 'LONG', 2),
       ('WIN$', 'LONG', 3),
       ('WIN$', 'LONG', 4),
       ('WIN$', 'LONG', 5),
       ('WIN$', 'SHORT', 0),
       ('WIN$', 'SHORT', 1),
       ('WIN$', 'SHORT', 2),
       ('WIN$', 'SHORT', 3),
       ('WIN$', 'SHORT', 4),
       ('WIN$', 'SHORT', 5),
       ('WIN$', 'WAIT', 0),
       ('WIN$', 'WAIT', 1),
       ('WIN$', 'WAIT', 2),
       ('WIN$', 'WAIT', 3),
       ('WIN$', 'WAIT', 4),
       ('WIN$', 'WAIT', 5),
       ('WIN$', 'WAIT', 6),
       ('WIN$', 'WAIT', 7),
       ('WIN$', 'WAIT', 8),
       ('WIN$', 'WAIT', 9),
       ('WIN$', 'WAIT', 10),
       ('WIN$', 'WAIT', 11),
       ('WIN$', 'WAIT', 12);

CREATE TABLE IF NOT EXISTS position_fact
(
    position_uuid     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    portfolio_uuid    UUID             NOT NULL REFERENCES portfolio_fact (portfolio_uuid),
    position_dim_uuid UUID             NOT NULL REFERENCES position_dim (position_dim_uuid),
    datetime_id       BIGINT           NOT NULL REFERENCES datetime_dim (datetime_id),
    entry_price       DOUBLE PRECISION NOT NULL CHECK (entry_price > 0)
);
CREATE INDEX IF NOT EXISTS position_fact_portfolio_idx ON balance_fact (portfolio_uuid);
CREATE INDEX IF NOT EXISTS position_fact_position_dim_idx ON position_dim (position_dim_uuid);
CREATE INDEX IF NOT EXISTS position_fact_dt_idx ON position_fact (datetime_id);

CREATE TABLE IF NOT EXISTS risk_management_fact
(
    risk_management_uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trade_zero_fact_uuid UUID                NOT NULL REFERENCES trade_zero_fact (trade_zero_fact_uuid),
    actions              INTEGER             NOT NULL CHECK (actions >= 0),
    valid_inputs         trade_action_type[] NOT NULL
);
CREATE INDEX IF NOT EXISTS risk_management_fact_trade_idx ON risk_management_fact (trade_zero_fact_uuid);

CREATE TABLE IF NOT EXISTS action_fact
(
    action_fact_uuid     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    risk_management_uuid UUID   NOT NULL REFERENCES risk_management_fact (risk_management_uuid),
    action_dim_uuid      UUID   NOT NULL REFERENCES action_dim (action_dim_uuid),
    datetime_id          BIGINT NOT NULL REFERENCES datetime_dim (datetime_id)
);
CREATE INDEX IF NOT EXISTS action_fact_trade_idx ON action_fact (risk_management_uuid);
CREATE INDEX IF NOT EXISTS action_fact_action_dim_idx ON action_fact (action_dim_uuid);
CREATE INDEX IF NOT EXISTS action_fact_dt_idx ON action_fact (datetime_id);

CREATE TABLE IF NOT EXISTS risk_metrics_fact
(
    risk_metrics_uuid    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    risk_management_uuid UUID             NOT NULL REFERENCES risk_management_fact (risk_management_uuid),
    datetime_id          BIGINT           NOT NULL REFERENCES datetime_dim (datetime_id),
    margin_used          DOUBLE PRECISION NOT NULL CHECK (margin_used >= 0),
    max_drawdown         DOUBLE PRECISION NOT NULL CHECK (max_drawdown BETWEEN 0 AND 1),
    sharpe_ratio         DOUBLE PRECISION NOT NULL,
    UNIQUE (risk_management_uuid, datetime_id)
);
CREATE INDEX IF NOT EXISTS risk_metrics_fact_portfolio_idx ON risk_metrics_fact (risk_management_uuid);
CREATE INDEX IF NOT EXISTS risk_metrics_fact_dt_idx ON risk_metrics_fact (datetime_id);

EOSQL
