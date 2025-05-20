import typing as t

import uuid
import inspect
import traceback

from pprint import pprint
from functools import wraps
from contextlib import ContextDecorator


T = t.Union[list | tuple]


class DecoratorAbstract(ContextDecorator):
    def __gen_wrapper(self, f, *args, **kwargs):
        with self:
            for res in f(*args, **kwargs):
                yield res

    def __call__(self, f):
        @wraps(f)
        def wrapper(*args, **kwargs):
            with self:
                if inspect.isgeneratorfunction(f):
                    return self.__gen_wrapper(f, *args, **kwargs)
                else:
                    return f(*args, **kwargs)
        return wrapper


class class_decorator(DecoratorAbstract):
    def __init__(self, function_decorator, include: T = None, exclude: T = None):
        self.include = include
        self.exclude = exclude
        self._decorator = function_decorator

    def __call__(self, children):

        @wraps(children)
        def wrapper(*args, **kwargs):

            for attr in children.__dict__:
                func = getattr(children, attr)
                if callable(func):
                    if self.include is None and self.exclude is None:
                        setattr(children, attr, self._decorator(func))
                    elif self.include and attr in self.include:
                        setattr(children, attr, self._decorator(func))
                    elif self.exclude and attr not in self.exclude and not isinstance(self.include, (list, tuple)):
                        setattr(children, attr, self._decorator(func))

            return children(*args, **kwargs)

        return wrapper


class wrap_exceptions(DecoratorAbstract):
    def __init__(
            self,
            wrapper_exc,
            *,
            wrapped_exc=Exception,
            code: int = -1,
            callback=lambda doc: pprint(doc)
    ):
        self.code = code
        self.callback = callback
        self.wrapper_exc = wrapper_exc
        self.wrapped_exc = wrapped_exc

    def __enter__(self):
        pass

    def __exit__(self, exc_type, exc_val, exc_tb):
        if not exc_type:
            return
        try:
            raise exc_val
        except self.wrapped_exc as exc:
            trace = traceback.format_exception(type(exc), exc, exc.__traceback__)
            self.callback({
                "code": self.code,
                "message": exc.__str__(),
                "args": exc.args,
                "type": self.wrapper_exc.__name__,
                "trace": trace,
                "trace_id": uuid.uuid4().__str__()
            })
            raise self.wrapper_exc from exc_val


class wrap_info_decorator(DecoratorAbstract):
    def __init__(
            self,
            on_init,
            on_exit,
            on_except,
            *,
            wrapper_exc=Exception,
            wrapped_exc=Exception,
            code: int = -1,
            callback=lambda doc: doc
    ):
        self.on_init = on_init
        self.on_exit = on_exit
        self.on_except = on_except
        self.code = code
        self.callback = callback
        self.wrapper_exc = wrapper_exc
        self.wrapped_exc = wrapped_exc

    def get_contact(self):
        return self.kwargs.get("contact")

    def __enter__(self):
        contact = self.get_contact()
        contact.progress.append(self.on_init)
        self.callback(contact)

    def __exit__(self, exc_type, exc_val, exc_tb):
        contact = self.get_contact()
        if not exc_type:
            contact.progress.append(self.on_exit)
            self.callback(contact)
            return
        try:
            raise exc_val
        except self.wrapped_exc as exc:
            trace = traceback.format_exception(type(exc), exc, exc.__traceback__)
            self.callback({
                "code": self.code,
                "message": exc.__str__(),
                "type": self.wrapper_exc.__name__,
                "trace": trace,
                "trace_id": uuid.uuid4().__str__()
            })
            contact.progress.append(self.on_except)
            self.callback(contact)
            raise self.wrapper_exc from exc_val

    def __call__(self, f):
        @wraps(f)
        def wrapper(*args, **kwargs):
            self.args = args
            self.kwargs = kwargs
            with self:
                if inspect.isgeneratorfunction(f):
                    return self.__gen_wrapper(f, *args, **kwargs)
                else:
                    return f(*args, **kwargs)
        return wrapper
