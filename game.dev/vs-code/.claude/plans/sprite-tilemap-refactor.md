# Sprite & Tilemap Refactor Plan

**Created:** 2026-01-24  
**Status:** Planning

## Problem Statement

Current implementation has several issues:
1. Sprites have irregular dimensions (not aligned to a consistent grid)
2. Wall positions are hardcoded with manual pixel alignment
3. Corner sprite includes transparent/black background causing collision detection issues
4. Manual positioning is not scalable or maintainable

## Goals

1. Normalize all sprites to a consistent tile size (32px or 64px)
2. Replace hardcoded `wallPositions` array with a tilemap system
3. Fix corner sprite collision issues
4. Enable visual level design instead of code-based positioning

## Technical Approach

### Tool Chain

**Aseprite** → Create individual tile graphics  
**Tiled** → Arrange tiles into level layouts  
**Phaser** → Load and render tilemaps with collision

### Workflow

```
1. Design Phase (Aseprite)
   - Create normalized sprites (32x32 or 64x64)
   - Export as tileset PNG (single image with all tiles)

2. Level Design Phase (Tiled)
   - Import tileset into Tiled
   - Create map (dimensions based on screen size ÷ tile size)
   - Paint walls visually
   - Mark collision properties on tiles
   - Export as JSON

3. Integration Phase (Phaser)
   - Load tilemap JSON and tileset PNG
   - Replace wallPositions code with tilemap
   - Configure collision with tile layers
```

## Detailed Steps

### Step 1: Choose Tile Size

**Options:**
- **32px** - Good for Pong's simple graphics, fits screen evenly (1200÷32=37.5, 800÷32=25)
- **64px** - More detail possible, fewer tiles (1200÷64=18.75, 800÷64=12.5)

**Decision needed:** Which size? (32px recommended for Pong)

### Step 2: Install Tiled

- Download from: https://www.mapeditor.org/
- Free and open-source
- Cross-platform (Linux/Mac/Windows)

### Step 3: Redesign Sprites in Aseprite

**Required tiles:**
- Horizontal wall segment
- Vertical wall segment
- Corner pieces (may need 4 orientations, or use rotation)

**Key requirements:**
- Each tile exactly 32x32 (or 64x64)
- No transparent/empty space that would affect collision
- Consistent visual style
- Corner should be a single clean tile (no phantom collision areas)

**Output:**
- `pong-tileset.png` - Single image containing all tiles in a grid
- Document tile positions (e.g., tile 0 = horizontal wall, tile 1 = vertical wall, etc.)

### Step 4: Create Tilemap in Tiled

1. **New Map:**
   - Size: 37x25 tiles (for 1200x800 screen at 32px)
   - Orientation: Orthogonal
   - Tile size: 32x32

2. **Import Tileset:**
   - Add tileset from `pong-tileset.png`
   - Set tile size to match

3. **Paint Walls:**
   - Create layer "Walls"
   - Use paint tool to draw border
   - Place corners at corners
   - Visual feedback instead of guessing coordinates

4. **Configure Collision:**
   - Select wall tiles
   - Add custom property: `collides = true`
   - Phaser will use this for collision detection

5. **Export:**
   - Format: JSON
   - Save as: `public/assets/maps/pong-map.json`

### Step 5: Update Phaser Code

**PreloadScene changes:**

```typescript
// Remove individual sprite loads
// Add tilemap loads
this.load.tilemapTiledJSON('pong-map', '/assets/maps/pong-map.json');
this.load.image('pong-tileset', '/assets/tilesets/pong-tileset.png');
```

**GameScene changes:**

```typescript
// Replace wallPositions array and manual creation
// With tilemap loading:
const map = this.make.tilemap({ key: 'pong-map' });
const tileset = map.addTilesetImage('pong-tileset', 'pong-tileset');
const wallLayer = map.createLayer('Walls', tileset, 0, 0);

// Set up collision
wallLayer.setCollisionByProperty({ collides: true });
```

**Benefits:**
- No more hardcoded coordinates
- Collision shapes match visuals exactly
- Can redesign level without touching code
- Future-proof for multiple levels

## Open Questions

1. **Tile size:** 32px or 64px?
2. **Wall decoration:** Simple blocks or detailed graphics?
3. **Multiple variants:** Should walls have visual variety (different tiles that all collide)?
4. **Play area vs walls:** Should the play area also be tiles (background layer)?

## Success Criteria

- [ ] All sprites are consistent tile size
- [ ] Walls align perfectly on grid
- [ ] No collision detection artifacts
- [ ] Level layout defined in Tiled, not code
- [ ] Can modify level without code changes
- [ ] Game still renders correctly

## Future Enhancements

- Multiple levels (different JSON files)
- Animated tiles
- Decorative non-collidable tiles
- Power-ups placed via tilemap object layer

## Resources

- Tiled documentation: https://doc.mapeditor.org/
- Phaser tilemap tutorial: https://phaser.io/tutorials/making-your-first-phaser-3-game
- Phaser tilemap API: https://newdocs.phaser.io/docs/3.55.2/Phaser.Tilemaps.Tilemap
