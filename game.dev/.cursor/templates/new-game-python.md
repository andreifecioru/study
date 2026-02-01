# Pyglet Game Template

Template for creating a new Pyglet game project.

## Folder Structure

```
pyglet/
  <game_name>/
    src/
      __init__.py
      main.py
      config.py           # Game constants and configuration
      resources.py        # Asset loading and management
      entities/
        __init__.py
      scenes/
        __init__.py
        game_scene.py
    tests/
      __init__.py
      test_game_logic.py
    assets/
      sprites/
        .gitkeep
      sounds/
        .gitkeep
    README.md
```

## Core Files Content

### `src/main.py`
Entry point with basic game window and loop structure:
- Window setup (resolution, title)
- Event handlers (on_draw, on_update)
- Basic game loop
- Entry point: `if __name__ == "__main__"`

### `src/config.py`
Game constants:
- WINDOW_WIDTH, WINDOW_HEIGHT
- TARGET_FPS
- Game-specific constants (PLAYER_SPEED, etc.)

### `src/resources.py`
Resource management:
- Asset loading functions
- Sprite/texture management
- Sound loading (if needed)

### `src/scenes/game_scene.py`
Main game scene with:
- Initialize method
- Update method (game logic)
- Draw method (rendering)

### `README.md`
- Game description
- Controls
- How to run: `uv run python -m src.main`
- Development notes

## Dependencies

Add to root `pyproject.toml` if not already present:
```toml
[project]
dependencies = [
    "pyglet>=2.0",
]
```

## Setup Notes

1. Create folder structure
2. Initialize with basic game loop
3. Set up asset directories with .gitkeep files
4. Create minimal working game (window opens, basic rendering)
5. Player implements from there

**Remember**: Start minimal. Add complexity as needed.
