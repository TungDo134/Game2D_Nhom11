package object;

import main.Game;

public class Cannon extends GameObject {
	private GameObjectController gameObjectController;
	private int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / Game.TILES_SIZE;
		initHitbox(40, 26);
//		hitbox.x -= (int) (1 * Game.SCALE);
		hitbox.y += (int) (6 * Game.SCALE);
		this.gameObjectController = new GameObjectController(this);
	}

	public void update() {
		gameObjectController.update();
	}
	
	public int getTileY() {
		return tileY;
	}

}
