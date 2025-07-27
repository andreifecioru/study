from app import App
from level import Level

class Game:
    def __init__(self):
        self.level = Level()

    def update_scene(self, dt):
        pass

    def render(self):
        App.window.clear()
        self.level.render()

    def start(self):
        App.add_event_listener('on_draw', self.render)
        App.add_event_listener('on_clock_tick', self.update_scene)
