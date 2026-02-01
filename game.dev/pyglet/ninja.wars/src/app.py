import sys
import pyglet
from pyglet import clock
from settings import *

class App:
    window = pyglet.window.Window(width=WINDOW_WIDTH, height=WINDOW_HEIGHT)
    window.set_caption(APP_NAME)
    clock = pyglet.clock.get_default()

    _event_listeners = {
        'on_draw': [],
        'on_key_press': [],
        'on_clock_tick': []
    }

    @staticmethod
    def add_event_listener(event_name, listener):
        App._event_listeners[event_name].append(listener)

    @staticmethod
    @window.event
    def on_draw():
        [cbk() for cbk in App._event_listeners['on_draw']]

    @staticmethod
    @window.event
    def on_key_press(symbol, modifiers):
        if symbol == pyglet.window.key.ESCAPE:
            App.quit()
        else:
            [cbk(symbol, modifiers) for cbk in App._event_listeners['on_key_press']]

    @staticmethod
    def _on_tick(*args, **kwargs):
        [cbk(*args, **kwargs) for cbk in App._event_listeners['on_clock_tick']]

    @staticmethod
    def start():
        clock.schedule_interval(App._on_tick, 1/FPS)
        pyglet.app.run()

    @staticmethod
    def quit():
        pyglet.app.exit()
        sys.exit()

