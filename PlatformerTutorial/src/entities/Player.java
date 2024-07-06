package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;
import static utilz.Constants.Directions.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import achievements.Observer;
import audio.AudioPlayer;
import gamestates.Playing;
import leaderboard.PlayerSubject;
import leaderboard.TimeScoreObserver;
import main.Game;
import utilz.LoadSave;

public abstract class Player extends Entity implements Subject, PlayerSubject {
    public boolean moving = false, attacking = false;
    public boolean left, right, jump;
    public int[][] lvlData;
    public float xDrawOffset = 21 * Game.SCALE;
    public float yDrawOffset = 4 * Game.SCALE;

    // Jumping / Gravity
    public float jumpSpeed = -2.25f * Game.SCALE;
    public float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    // StatusBarUI
    public BufferedImage statusBarImg;

    public int statusBarWidth = (int) (192 * Game.SCALE);
    public int statusBarHeight = (int) (58 * Game.SCALE);
    public int statusBarX = (int) (10 * Game.SCALE);
    public int statusBarY = (int) (10 * Game.SCALE);

    public int healthBarWidth = (int) (150 * Game.SCALE);
    public int healthBarHeight = (int) (4 * Game.SCALE);
    public int healthBarXStart = (int) (34 * Game.SCALE);
    public int healthBarYStart = (int) (14 * Game.SCALE);
    public int healthWidth = healthBarWidth;

    public int powerBarWidth = (int) (104 * Game.SCALE);
    public int powerBarHeight = (int) (2 * Game.SCALE);
    public int powerBarXStart = (int) (44 * Game.SCALE);
    public int powerBarYStart = (int) (34 * Game.SCALE);
    public int powerWidth = powerBarWidth;
    public int powerMaxValue = 200;
    public int powerValue = powerMaxValue;

    public int flipX = 0;
    public int flipW = 1;

    public boolean attackChecked;
    public Playing playing;

    public int tileY = 0;

    public boolean powerAttackActive;
    public int powerAttackTick;
    public int powerGrowSpeed = 15;
    public int powerGrowTick;

    // By Hung
    protected List<Observer> boards = new ArrayList<>();
    protected int score;
    protected int time = 0;
    protected int lives =3;

    //By Huy
    private List<TimeScoreObserver> observers = new ArrayList<>();

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = Game.SCALE * 1.0f;

    }


    public abstract void initDraw();

    public abstract void initAttackBox();

    public abstract void loadAnimations();


    public abstract void setSpawn(Point spawn);

    public abstract void update();

    public abstract void checkInsideWater();

    public abstract void checkSpikesTouched();

    public abstract void checkPotionTouched();

    public abstract void checkAttack();

    public abstract void setAttackBoxOnRightSide();

    public abstract void setAttackBoxOnLeftSide();

    public abstract void updateAttackBox();

    public abstract void updateHealthBar();

    public abstract void updatePowerBar();

    public abstract void render(Graphics g, int lvlOffset);

    public abstract void updateAnimationTick();

    public abstract void setAnimation();

    public abstract void resetAniTick();

    public abstract void updatePos();

    public abstract void jump();

    public abstract void resetInAir();

    public abstract void updateXPos(float xSpeed);

    public abstract void changeHealth(int value);

    public abstract void changeHealth(int value, Enemy e);

    public abstract void kill();

    public abstract void changePower(int value);


    public abstract void loadLvlData(int[][] lvlData);

    public abstract void resetDirBooleans();

    public abstract float getxDrawOffset();


    public abstract BufferedImage getStatusBarImg();

    public abstract int getStatusBarWidth();

    public abstract int getStatusBarHeight();

    public abstract int getStatusBarX();

    public abstract int getStatusBarY();

    public abstract int getHealthBarWidth();

    public abstract int getHealthBarHeight();

    public abstract int getHealthBarYStart();

    public abstract int getHealthWidth();

    public abstract int getPowerBarHeight();

    public abstract int getPowerBarXStart();

    public abstract int getPowerBarYStart();

    public abstract int getPowerWidth();


    public abstract int getFlipX();

    public abstract int getFlipW();

    public abstract float getyDrawOffset();

    public abstract void setAttacking(boolean attacking);


    public abstract void setLeft(boolean left);


    public abstract void setRight(boolean right);

    public abstract void setJump(boolean jump);

    public abstract void resetAll();

    public abstract void resetAttackBox();

    public abstract int getTileY();

    public abstract void powerAttack();

    ///by Hung
    public void plusScore(int point){
        score+=point;
    }
    public int getTime() {
        return time;
    }
    public void resetime(){
        time =0;
    }
    public void resetScore(){
        score=0;
    }
    public int getScore(){
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void increaseTime(){
        time +=1;
    }
    @Override
    public void addObserver(Observer board) {
        boards.add(board);
    }

    @Override
    public void removeObserver(Observer board) {
        boards.remove(board);
    }

    @Override
    public void notifyObserver() {
        for (Observer board : this.boards) {
            board.updateInput(getScore(), getTime(),getLives());
        }
    }

    // Huy
    @Override
    public void addObserver(TimeScoreObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TimeScoreObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyTimeScore() {
        for (TimeScoreObserver observer : observers) {
            observer.updateTimeScore(this.time, this.score);
        }
    }

}