package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Dialogue.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Directions.*;

import gamestates.Playing;

public class Pinkstar extends Enemy {
	EnemyController enemyController;

	private boolean preRoll = true;
	private int tickSinceLastDmgToPlayer;
	private int tickAfterRollInIdle;
	private int rollDurationTick, rollDuration = 300;

	public Pinkstar(float x, float y) {
		super(x, y, PINKSTAR_WIDTH, PINKSTAR_HEIGHT, PINKSTAR);
		this.point = 30;
		initHitbox(17, 21);
		this.enemyController=new EnemyController(this);
	}

	public void update(int[][] lvlData, Playing playing) {
		enemyController.update(lvlData, playing);
	}

	public void updateBehavior(int[][] lvlData, Playing playing) {
		enemyController.updateBehavior(lvlData, playing);
	}

	public void checkDmgToPlayer(Player player) {
		enemyController.checkDmgToPlayer(player);
	}

	public void setWalkDir(Player player) {
		enemyController.setWalkDir(player);
	}

	public void move(int[][] lvlData, Playing playing) {
		enemyController.move(lvlData, playing);
	}

	public void checkRollOver(Playing playing) {
		enemyController.checkRollOver(playing);
	}

	public void rollOver(Playing playing) {
		enemyController.rollOver(playing);
	}

	public boolean isPreRoll() {
		return preRoll;
	}

	public void setPreRoll(boolean preRoll) {
		this.preRoll = preRoll;
	}

	public int getTickSinceLastDmgToPlayer() {
		return tickSinceLastDmgToPlayer;
	}

	public void setTickSinceLastDmgToPlayer(int tickSinceLastDmgToPlayer) {
		this.tickSinceLastDmgToPlayer = tickSinceLastDmgToPlayer;
	}

	public int getTickAfterRollInIdle() {
		return tickAfterRollInIdle;
	}

	public void setTickAfterRollInIdle(int tickAfterRollInIdle) {
		this.tickAfterRollInIdle = tickAfterRollInIdle;
	}

	public int getRollDurationTick() {
		return rollDurationTick;
	}

	public void setRollDurationTick(int rollDurationTick) {
		this.rollDurationTick = rollDurationTick;
	}

	public int getRollDuration() {
		return rollDuration;
	}

	public void setRollDuration(int rollDuration) {
		this.rollDuration = rollDuration;
	}

}
