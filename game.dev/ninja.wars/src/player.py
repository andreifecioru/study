from pyglet.window import key
from pyglet.math import Vec2
from tile import Tile
from app import App


class Player(Tile):
    def __init__(self, x, y, batch=None, group=None):
        super().__init__('./graphics/test/player.png', x, y, batch, group)

        self.keys = key.KeyStateHandler()
        App.window.push_handlers(self.keys)
        App.add_event_listener('on_clock_tick', self._on_clock_tick)

        self.prev_pos = Vec2(x, y)
        self.direction = Vec2(0, 0)
        self.speed = 5

        self._event_handlers = {
            'on_move': []
        }

    def add_event_listener(self, event_name, listener):
        self._event_handlers[event_name].append(listener)

    def _on_clock_tick(self, _):
        self.prev_pos = Vec2(self.x, self.y)
        self._handle_key_press()
        self.move(self.direction, self.speed)
        [cbk(self.x, self.y) for cbk in self._event_handlers['on_move']]

    def _handle_key_press(self):
        x_delta = 0
        y_delta = 0

        if self.keys[key.LEFT]:
            x_delta = -1
        elif self.keys[key.RIGHT]:
            x_delta = 1

        if self.keys[key.UP]:
            y_delta = 1
        elif self.keys[key.DOWN]:
            y_delta = -1

        self.direction = Vec2(x_delta, y_delta)

        if self.direction.length() > 0:
            self.direction = self.direction.normalize()

    def move_back(self):
        self.x = self.prev_pos.x
        self.y = self.prev_pos.y


