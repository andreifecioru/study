# Pong

A classic Pong game built with Phaser.js and TypeScript.

## Setup

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

## Project Structure

```
pong/
├── src/
│   ├── main.ts          # Entry point, Phaser game config
│   └── scenes/
│       └── GameScene.ts # Main game scene
├── public/
│   └── assets/          # Game assets (images, sounds)
├── index.html           # HTML shell
├── package.json
├── tsconfig.json        # Strict TypeScript configuration
└── vite.config.ts       # Vite build configuration
```

## TypeScript Configuration

This project uses strict TypeScript settings:
- All strict checks enabled
- No unused variables/parameters
- No implicit returns
- No fallthrough cases
- And more...

## Development

The dev server will open automatically at `http://localhost:3000` with hot module replacement.
