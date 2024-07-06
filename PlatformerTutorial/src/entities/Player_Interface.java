package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Player_Interface {
    void setSpawn(Point spawn);

    void update();

    void checkInsideWater();

    void checkSpikesTouched();

    void checkPotionTouched();

    void checkAttack();

    void setAttackBoxOnRightSide();

    void setAttackBoxOnLeftSide();

    void updateAttackBox();

    void updateHealthBar();

    void updatePowerBar();

    void updateAnimationTick();

    void render(Graphics g, int lvlOffset);

    void setAnimation();

    void resetAniTick();

    void updatePos();

    void jump();

    void resetInAir();

    void updateXPos(float xSpeed);

    void changeHealth(int value);

    void changeHealth(int value, Enemy e);

    void kill();

    void changePower(int value);

    void loadLvlData(int[][] lvlData);

    void resetDirBooleans();

    float getxDrawOffset();

    BufferedImage getStatusBarImg();

    int getStatusBarWidth();

    int getStatusBarHeight();

    int getStatusBarX();

    int getStatusBarY();

    int getHealthBarWidth();

    int getHealthBarHeight();

    int getHealthBarYStart();

    int getHealthWidth();

    int getPowerBarHeight();

    int getPowerBarXStart();

    int getPowerBarYStart();

    int getPowerWidth();

    int getFlipX();

    int getFlipW();

    float getyDrawOffset();

    void setAttacking(boolean attacking);

    void setLeft(boolean left);

    void setRight(boolean right);

    void setJump(boolean jump);

    void resetAll();

    void resetAttackBox();

    int getTileY();

    void powerAttack();
}
