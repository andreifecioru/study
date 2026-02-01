import pyglet
from pyglet.sprite import Sprite
from pyglet.graphics import Batch

PLAYER_IMAGE = pyglet.resource.image('graphics/player.png')

class GameScene:
  def __init__(self):
    self._main_batch = Batch()
    self.batches = [
      self._main_batch
    ]
    self._player = Sprite(img=PLAYER_IMAGE, batch=self._main_batch, x=50, y=50)

  def update(self, dt: float):
    pass
