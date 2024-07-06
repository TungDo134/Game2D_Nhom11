package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Dialogue.*;

import gamestates.Playing;

public class Crabby extends Enemy {
	EnemyController enemyController;

	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		this.point = 20;
		initHitbox(22, 19);
		initAttackBox(82, 19, 30);
		this.enemyController = new EnemyController(this);
	}

	public void update(int[][] lvlData, Playing playing) {

		enemyController.update(lvlData, playing);
	}

	public void updateBehavior(int[][] lvlData, Playing playing) {
		enemyController.updateBehavior(lvlData, playing);
	}

}