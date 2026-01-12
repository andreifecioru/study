import pyglet
from pyglet.sprite import Sprite
from pyglet.graphics import Batch

PLAYER_IMAGE = pyglet.resource.image('graphics/player.png')

class GameState:
  def __init__(self):
    self._main_batch = Batch()
    self.batches = [
      self._main_batch
    ]
    self._player = Sprite(img=PLAYER_IMAGE, batch=self._main_batch, x=50, y=50)

