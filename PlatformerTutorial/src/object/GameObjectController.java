package object;

public class GameObjectController {
	private GameObject gameObject;

	public GameObjectController(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public void update() {
		if (gameObject.doAnimation)
			gameObject.updateAnimationTick();
	}

}
