package entities;

import static utilz.Constants.Dialogue.*;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

public class Shark extends Enemy {
	EnemyController enemyController;

	public Shark(float x, float y) {
		super(x, y, SHARK_WIDTH, SHARK_HEIGHT, SHARK);
		this.point = 40;
		initHitbox(18, 22);
		initAttackBox(20, 20, 20);
		this.enemyController=new EnemyController(this);
	}

	public void update(int[][] lvlData, Playing playing) {
		enemyController.update(lvlData, playing);
	}

	public void updateBehavior(int[][] lvlData, Playing playing) {
		enemyController.updateBehavior(lvlData, playing);
	}

	public void attackMove(int[][] lvlData, Playing playing) {
		enemyController.attackMove(lvlData, playing);
	}
}
