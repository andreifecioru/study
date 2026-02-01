# Project Structure Conventions

This workspace contains multiple small game experiments organized by game library.

## Structure

```
game.dev/
  pyglet/           # Python-based games
    game_name_1/
    game_name_2/
  phaser.js/        # TypeScript-based games
    game_name_1/
    game_name_2/
```

## Key Principles

- Each game is an **independent small experiment**
- Games share the same root folder for convenience
- Avoid duplication of setup (package managers, configs)
- Each game should be self-contained within its folder

## Python/Pyglet Structure

For Python-based game development:
```
game_name/
  src/
    __init__.py
    main.py
    ...
  tests/
    ...
pyproject.toml
uv.lock
CLAUDE.md
```

## TypeScript/Phaser Structure

For TypeScript-based game development:

_TBD - will define as we build our first Phaser game_
