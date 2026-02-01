# Phaser Game Template

Template for creating a new Phaser game project.

## Folder Structure

```
phaser.js/
  <game_name>/
    src/
      main.ts              # Entry point, Phaser game config
      globals/
        AssetKeys.ts       # Constant keys for assets
        SceneKeys.ts       # Scene identifier constants
      scenes/
        PreloadScene.ts    # Asset loading scene
        GameScene.ts       # Main game scene
      entities/            # Game objects/entities
      config/
        GameConfig.ts      # Game configuration
    public/
      assets/
        sprites/
          .gitkeep
        audio/
          .gitkeep
      index.html
    package.json
    tsconfig.json
    vite.config.ts
    README.md
```

## Core Files Content

### `package.json`
```json
{
  "name": "<game_name>",
  "private": true,
  "version": "0.1.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "tsc && vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "phaser": "^3.80.0"
  },
  "devDependencies": {
    "typescript": "^5.0.0",
    "vite": "^5.0.0"
  }
}
```

### `tsconfig.json`
Strict TypeScript configuration:
- `strict: true`
- `noImplicitAny: true`
- Target ES2020+
- Module resolution: bundler

### `vite.config.ts`
Vite bundler config with proper asset handling

### `src/main.ts`
- Phaser game configuration (width, height, physics)
- Scene registration
- Game instantiation

### `src/scenes/PreloadScene.ts`
- Asset loading (images, spritesheets, audio)
- Loading progress (optional)
- Transition to GameScene

### `src/scenes/GameScene.ts`
Main game scene:
- `create()` - scene initialization
- `update()` - game loop logic

### `src/globals/AssetKeys.ts`
Typed constant keys for all assets:
```typescript
export const AssetKeys = {
  PLAYER: 'player',
  ENEMY: 'enemy',
  // ... etc
} as const;
```

### `public/index.html`
Minimal HTML with:
- Canvas container
- Script module reference
- Basic styling (fullscreen, centered canvas)

### `README.md`
- Game description
- Controls
- How to run: `npm run dev`
- Development notes

## Setup Notes

1. Create folder structure
2. Run `npm install` to install dependencies
3. Set up minimal working game (window opens, basic scene)
4. Add .gitkeep files to empty asset directories
5. Player implements from there

**Remember**: Start with PreloadScene → GameScene structure. Add more scenes as needed.
