import pyglet
from pong.scene.game_state import GameScene


class Game:
  dt = 1 / 60.0

  def __init__(self) -> None:
    self.window = pyglet.window.Window()
    pyglet.clock.schedule_interval(self.update, Game.dt)

    @self.window.event
    def on_draw() -> None:
      self.render()

    self.game_state = GameScene()

  def render(self) -> None:
    self.window.clear()
    for batch in self.game_state.batches:
      batch.draw()

  def update(self, dt: float) -> None:
    self.window.dispatch_event("on_draw")

  def start(self) -> None:
    pyglet.app.run(self.dt)
