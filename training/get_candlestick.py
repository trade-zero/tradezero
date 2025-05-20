import numpy as np
import pandas as pd


def csv_repository(add_time: bool = True):
    day = 24 * 60 * 60
    df = pd.read_csv("candlestick.csv", sep="\t")
    df["DATETIME"] = df["<DATE>"] + " " + df["<TIME>"]
    df.drop(["<DATE>", "<TIME>", "<TICKVOL>", "<SPREAD>"], axis=1, inplace=True)
    df["DATETIME"] = pd.to_datetime(df["DATETIME"])
    df.set_index("DATETIME", inplace=True)
    df.sort_index(inplace=True)

    mapper = {"<OPEN>": "OPEN", "<HIGH>": "HIGH", "<LOW>": "LOW", "<CLOSE>": "CLOSE", "<VOL>": "VOL"}

    df.rename(mapper, axis=1, inplace=True)

    timestamp_s = df.index.map(pd.Timestamp.timestamp)
    df['DAY-SIN'] = np.sin(timestamp_s * (2 * np.pi / day))
    df['DAY-COS'] = np.cos(timestamp_s * (2 * np.pi / day))

    rolling = df[['OPEN', 'HIGH', 'LOW', 'CLOSE', 'VOL']].rolling(window=12, min_periods=1)
    df[['OPEN', 'HIGH', 'LOW', 'CLOSE', 'VOL']] = (df[['OPEN', 'HIGH', 'LOW', 'CLOSE',
                                                       'VOL']] - rolling.mean()) / rolling.std(ddof=1)
    df["VOL"] = df["VOL"].fillna(0)

    print(f"{rolling.std(ddof=1).mean(axis=None)}")
    print(f"\n{rolling.std(ddof=1).mean()}")
    # df[['OPEN', 'HIGH', 'LOW', 'CLOSE']]: pd.DataFrame = df[['OPEN', 'HIGH', 'LOW', 'CLOSE']].pct_change()
    df: pd.DataFrame = df.bfill()

    if add_time:
        candlestick_df = df[['DAY-SIN', 'DAY-COS', 'OPEN', 'HIGH', 'LOW', 'CLOSE', 'VOL']]
    else:
        candlestick_df = df[['OPEN', 'HIGH', 'LOW', 'CLOSE', 'VOL']]

    xs = candlestick_df.to_numpy()

    return xs
