import multiprocessing

from .singleton import Singleton


class EventDispatcher(metaclass=Singleton):
    def __init__(self):
        self.listeners = {}
        self.queue = multiprocessing.Queue()

    def add_listener(self, event, listener):
        if event not in self.listeners:
            self.listeners[event] = []
        self.listeners[event].append(listener)

    def remove_listener(self, event, listener):
        if event in self.listeners:
            self.listeners[event].remove(listener)

    def dispatch(self, event, *args, **kwargs):
        if event in self.listeners:
            for listener in self.listeners[event]:
                self.queue.put_nowait((listener, args, kwargs))

    def process_queue(self):
        while True:
            listener, args, kwargs = self.queue.get()
            listener(*args, **kwargs)
            self.queue.cancel_join_thread()

    def start(self):
        process = multiprocessing.Process(target=self.process_queue)
        process.daemon = True
        process.start()
        process.join()
