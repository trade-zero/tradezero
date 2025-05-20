import os


def fix(lines) -> list[list[str]]:
    return [*map(lambda doc: doc.replace("\n", "").split(","), lines)]


def test_data_raw_sales_meta() -> list:
    dirname = os.path.dirname(__file__)
    with open(f"{dirname}/test_raw_meta.csv") as f:
        lines = f.readlines()
        return fix(lines)


def test_data_raw_sales() -> list:
    dirname = os.path.dirname(__file__)
    with open(f"{dirname}/test_raw_sales.csv") as f:
        lines = f.readlines()
        return fix(lines)
