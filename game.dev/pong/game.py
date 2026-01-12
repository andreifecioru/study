import pyglet
from game_state import GameState

class Game:
  dt = 1 / 60.0

  def __init__(self) -> None:
    self.window = pyglet.window.Window()
    pyglet.clock.schedule_interval(self.update, Game.dt)

    @self.window.event
    def on_draw():
      self.render()

    self.game_state = GameState()

  def render(self) -> None:
    self.window.clear()
    for batch in self.game_state.batches:
      batch.draw()

  def update(self, dt: float) -> None:
    self.window.dispatch_event('on_draw')

  @staticmethod
  def start():
    pyglet.app.run(Game.dt)

