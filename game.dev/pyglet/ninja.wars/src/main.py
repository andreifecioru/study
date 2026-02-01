from app import App
from game import Game
from debug import DebugOverlay


if __name__ == '__main__':
    Game().start()
    DebugOverlay.start()
    App.start()

