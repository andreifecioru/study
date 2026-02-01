from settings import *
from tile import *
from player import Player
from app import App
from debug import DebugOverlay

class Level:
    MAP = WORLD_MAP
    row_count = len(MAP)
    col_count = len(MAP[0])

    def __init__(self):
        self.batch = pyglet.graphics.Batch()
        self.background = pyglet.graphics.Group(order=0)
        self.foreground = pyglet.graphics.Group(order=1)
        self.player = None
        self.obstacles = []
        self.all_tiles = []
        self._load_map()
        self.camera_pos = pyglet.math.Vec2(0, 0)
        App.add_event_listener('on_clock_tick', self._on_clock_tick)

    def _on_clock_tick(self, _):
        DebugOverlay.display(0, f'Player: [{self.player.hitbox.x}, {self.player.hitbox.y}]')
        DebugOverlay.display(2, f'Camera pos: [{self.camera_pos.x}, {self.camera_pos.y}]')

    def _load_map(self):
        for row in range(Level.row_count):
            for col in range(Level.col_count):
                x = col * TILE_SIZE
                y = WINDOW_HEIGHT - (row + 1) * TILE_SIZE
                if Level.MAP[row][col] == 'x':
                    self.obstacles.append(Rock(x, y, self.batch, self.foreground))
                elif Level.MAP[row][col] == 'p':
                    self.player = Player(x - 0.1, y - 0.1, self.batch, self.background)
                    self.player.add_event_listener('on_move', self._detect_collision)
                    self.player.add_event_listener('on_move', self._update_camera)

        self.all_tiles = self.obstacles + [self.player]

    def _detect_collision(self, x, y):
        for obstacle in self.obstacles:
            if self.player.collides_with(obstacle):
                self.player.move_back()

    def _update_camera(self, x, y):
        self.camera_pos = pyglet.math.Vec2(x, y)
        offset_x = self.camera_pos.x - WINDOW_WIDTH / 2
        offset_y = self.camera_pos.y - WINDOW_HEIGHT / 2
        for tile in self.all_tiles:
            tile.x -= offset_x
            tile.y -= offset_y

    def render(self):
        self.batch.draw()