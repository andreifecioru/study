import Phaser from 'phaser';
import { AssetKeys } from '@globals';


class PreloadScene extends Phaser.Scene {
  constructor() {
    super({ key: 'PreloadScene' });
  }

  preload(): void {
    this.load.image(AssetKeys.Game.PADDLE, '/assets/paddle-h64.png');
    this.load.image(AssetKeys.Game.BALL, '/assets/ball-h64.png');
    this.load.image(AssetKeys.Game.WALL, '/assets/wall.png');
    this.load.image(AssetKeys.Game.WALL_CORNER, '/assets/wall-corner.png');
  }

  create(): void {
    // Start the first scene after loading
    this.scene.start('GameScene');
  }
}


export { PreloadScene };