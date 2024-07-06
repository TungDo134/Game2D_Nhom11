package entities;

import static utilz.Constants.Dialogue.EXCLAMATION;
import static utilz.Constants.Dialogue.QUESTION;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.GetEnemyDmg;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

public class EnemyController {
	Enemy enemy;
	Pinkstar pinkstar;

	public EnemyController(Enemy enemy) {
		this.enemy=enemy;
	}

	// crabby, pinkstar, shark
	public void update(int[][] lvlData, Playing playing) {
		updateBehavior(lvlData, playing);
		enemy.updateAnimationTick();
		enemy.updateAttackBox();
		
	}

	public void updateBehavior(int[][] lvlData, Playing playing) {
		if (enemy.firstUpdate)
			enemy.firstUpdateCheck(lvlData);

		if (enemy.inAir) {
			enemy.inAirChecks(lvlData, playing);
		} else {
			switch (enemy.state) {
			case IDLE:
				if (IsFloor(enemy.hitbox, lvlData))
					enemy.newState(RUNNING);
				else
					enemy.inAir = true;
				break;
			case RUNNING:
				if (enemy.canSeePlayer(lvlData, playing.getPlayer())) {
					enemy.turnTowardsPlayer(playing.getPlayer());
					if (enemy.isPlayerCloseForAttack(playing.getPlayer()))
						enemy.newState(ATTACK);
				}
				enemy.move(lvlData);

				if (enemy.inAir)
					playing.addDialogue((int) enemy.hitbox.x, (int) enemy.hitbox.y, EXCLAMATION);

				break;
			case ATTACK:
				if (enemy.aniIndex == 0)
					enemy.attackChecked = false;
				if (enemy.aniIndex == 3 && !enemy.attackChecked)
					enemy.checkPlayerHit(enemy.attackBox, playing.getPlayer());
				break;
			case HIT:
				if (enemy.aniIndex <= GetSpriteAmount(enemy.enemyType, enemy.state) - 2)
					enemy.pushBack(enemy.pushBackDir, lvlData, 2f);
				enemy.updatePushBackDrawOffset();
				break;
			}
		}
	}

	// attackMove (Class Shark)
	public void attackMove(int[][] lvlData, Playing playing) {
		float xSpeed = 0;

		if (enemy.walkDir == LEFT)
			xSpeed = -enemy.walkSpeed;
		else
			xSpeed = enemy.walkSpeed;

		if (CanMoveHere(enemy.hitbox.x + xSpeed * 4, enemy.hitbox.y, enemy.hitbox.width, enemy.hitbox.height, lvlData))
			if (IsFloor(enemy.hitbox, xSpeed * 4, lvlData)) {
				enemy.hitbox.x += xSpeed * 4;
				return;
			}
		enemy.newState(IDLE);
		playing.addDialogue((int) enemy.hitbox.x, (int) enemy.hitbox.y, EXCLAMATION);
	}

	// class Pinkstar
	public void checkDmgToPlayer(Player player) {
		if (enemy.hitbox.intersects(player.getHitbox()))
			if (pinkstar.getTickSinceLastDmgToPlayer() >= 60) {
				pinkstar.setTickSinceLastDmgToPlayer(0);
				player.changeHealth(-GetEnemyDmg(enemy.enemyType), pinkstar);
			} else
				pinkstar.setTickSinceLastDmgToPlayer(pinkstar.getTickSinceLastDmgToPlayer() + 1);
	}

	public void setWalkDir(Player player) {
		if (player.getHitbox().x > enemy.hitbox.x)
			enemy.walkDir = RIGHT;
		else
			enemy.walkDir = LEFT;

	}

	public void move(int[][] lvlData, Playing playing) {
		float xSpeed = 0;

		if (enemy.walkDir == LEFT)
			xSpeed = -enemy.walkSpeed;
		else
			xSpeed = enemy.walkSpeed;

		if (enemy.state == ATTACK)
			xSpeed *= 2;

		if (CanMoveHere(enemy.hitbox.x + xSpeed, enemy.hitbox.y, enemy.hitbox.width, enemy.hitbox.height, lvlData))
			if (IsFloor(enemy.hitbox, xSpeed, lvlData)) {
				enemy.hitbox.x += xSpeed;
				return;
			}

		if (enemy.state == ATTACK) {
			rollOver(playing);
			pinkstar.setRollDurationTick(0);
			;
		}

		enemy.changeWalkDir();

	}

	public void checkRollOver(Playing playing) {
		pinkstar.setRollDurationTick(pinkstar.getRollDurationTick() + 1);
		if (pinkstar.getRollDurationTick() >= pinkstar.getRollDuration()) {
			rollOver(playing);
			pinkstar.setRollDurationTick(0);
		}
	}
	public void rollOver(Playing playing) {
		enemy.newState(IDLE);
		playing.addDialogue((int) enemy.hitbox.x, (int) enemy.hitbox.y, QUESTION);
	}

}
