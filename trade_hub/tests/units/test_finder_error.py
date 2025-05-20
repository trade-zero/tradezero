import pytest

from shared.finder_error import wrap_exceptions
from shared.finder_error import class_decorator


@class_decorator(function_decorator=wrap_exceptions(ZeroDivisionError, callback=lambda doc: doc))
class DecoratorOnInit:
    def __init__(self):
        self.x = 1 / 0

    @staticmethod
    def div(x, y):
        return x / y


@class_decorator(function_decorator=wrap_exceptions(ZeroDivisionError, callback=lambda doc: doc), exclude=["__init__"])
class DecoratorOnDiv:
    def __init__(self):
        self.i = 0

    @staticmethod
    def div(x, y):
        return x / y


@wrap_exceptions(ZeroDivisionError, code=10, callback=lambda doc: doc)
def decorator():
    x = 1 / 0


def test_func_divide_zero():
    with pytest.raises(ZeroDivisionError):
        decorator()


def test_class_divide_zero_on_init():
    with pytest.raises(ZeroDivisionError):
        DecoratorOnInit()


def test_class_divide_zero_on_div():
    with pytest.raises(ZeroDivisionError):
        obj = DecoratorOnDiv()
        obj.div(1, 0)

