import Phaser from 'phaser';
import { AssetKeys } from '@globals';

type WallPosition = { x: number, y: number, angle: number, scale: number, key: string };

const wallPositions: WallPosition[] = [
  {x: 45, y: 40, angle: -90, scale: 0.3, key: AssetKeys.Game.WALL_CORNER},
  {x: 38, y: 710, angle: 180, scale: 0.3, key: AssetKeys.Game.WALL_CORNER},
  {x: 1124, y: 47, angle: 0, scale: 0.3, key: AssetKeys.Game.WALL_CORNER},
  {x: 1117, y: 721, angle: 90, scale: 0.3, key: AssetKeys.Game.WALL_CORNER},

  {x: 133, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 234, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 335, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 436, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 537, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 638, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 739, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 840, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 941, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 1042, y: 28, angle: 0, scale: 0.32, key: AssetKeys.Game.WALL},

  {x: 26, y: 120, angle: -90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 26, y: 220, angle: -90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 26, y: 320, angle: -90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 26, y: 420, angle: -90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 26, y: 520, angle: -90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 26, y: 620, angle: -90, scale: 0.32, key: AssetKeys.Game.WALL},

  {x: 1136, y: 135, angle: 90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 1136, y: 236, angle: 90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 1136, y: 337, angle: 90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 1136, y: 438, angle: 90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 1136, y: 539, angle: 90, scale: 0.32, key: AssetKeys.Game.WALL},
  {x: 1136, y: 640, angle: 90, scale: 0.32, key: AssetKeys.Game.WALL},
]

export class GameScene extends Phaser.Scene {
  constructor() {
    super({ key: 'GameScene' });
  }

  create(): void {
    const walls = this.physics.add.staticGroup();

    wallPositions.forEach(wallPos => {
      walls
      .create(wallPos.x, wallPos.y, wallPos.key)
      .setScale(wallPos.scale)
      .setAngle(wallPos.angle)
      .refreshBody();
    });

    this.physics.add.image(1010, 740, AssetKeys.Game.PADDLE).setScale(0.8);
    this.physics.add.image(300, 300, AssetKeys.Game.BALL).setScale(0.5);

  }

  override update(_time: number, _delta: number): void {
    // Game loop - called every frame
  }
}
