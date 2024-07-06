package gamestates;

import effects.DialogueEffect;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utilz.Constants.Dialogue.*;
import static utilz.Constants.Environment.*;

public class PlayingController implements Statemethods{
    private final Playing playing;

    public PlayingController(Playing playing) {
        this.playing = playing;
    }

    private void updateDialogue() {
        for (DialogueEffect de : playing.dialogEffects)
            if (de.isActive())
                de.update();
    }

    private void updateShipAni() {
        playing.shipTick++;
        if (playing.shipTick >= 35) {
            playing.shipTick = 0;
            playing.shipAni++;
            if (playing.shipAni >= 4)
                playing.shipAni = 0;
        }

        playing.shipHeightDelta += playing.shipHeightChange * playing.shipDir;
        playing.shipHeightDelta = Math.max(Math.min(10 * Game.SCALE, playing.shipHeightDelta), 0);

        if (playing.shipHeightDelta == 0)
            playing.shipDir = 1;
        else if (playing.shipHeightDelta == 10 * Game.SCALE)
            playing.shipDir = -1;

    }

    private void checkCloseToBorder() {
        int playerX = (int) playing.player.getHitbox().x;
        int diff = playerX - playing.xLvlOffset;

        if (diff > playing.rightBorder)
            playing.xLvlOffset += diff - playing.rightBorder;
        else if (diff < playing.leftBorder)
            playing.xLvlOffset += diff - playing.leftBorder;

        playing.xLvlOffset = Math.max(Math.min(playing.xLvlOffset, playing.maxLvlOffsetX), 0);
    }

    @Override
    public void update() {
        if (playing.paused)
            playing.pauseOverlay.update();
        else if (playing.lvlCompleted)
            playing.levelCompletedOverlay.update();
        else if (playing.gameCompleted)
            playing.gameCompletedOverlay.update();
        else if (playing.gameOver)
            playing.gameOverOverlay.update();
        else if (playing.playerDying)
            playing.player.update();
        else {
            updateDialogue();
            if (playing.drawRain)
                playing.rain.update(playing.xLvlOffset);
            playing.levelManager.update();
            playing.objectManager.update(playing.levelManager.getCurrentLevel().getLevelData(), playing.player);
            playing.player.update();
            //By Hung
            playing.player.notifyObserver();

            playing.enemyManager.update(playing.levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder();
            if (playing.drawShip)
                updateShipAni();
        }
    }
    private void drawClouds(Graphics g) {
        for (int i = 0; i < 4; i++)
            g.drawImage(playing.bigCloud, i * BIG_CLOUD_WIDTH - (int) (playing.xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < playing.smallCloudsPos.length; i++)
            g.drawImage(playing.smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (playing.xLvlOffset * 0.7), playing.smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }
    private void drawDialogue(Graphics g, int xLvlOffset) {
        for (DialogueEffect de : playing.dialogEffects)
            if (de.isActive()) {
                if (de.getType() == QUESTION)
                    g.drawImage(playing.questionImgs[de.getAniIndex()], de.getX() - xLvlOffset, de.getY(), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, null);
                else
                    g.drawImage(playing.exclamationImgs[de.getAniIndex()], de.getX() - xLvlOffset, de.getY(), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, null);
            }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(playing.backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawClouds(g);
        if (playing.drawRain)
            playing.rain.draw(g, playing.xLvlOffset);

        if (playing.drawShip)
            g.drawImage(playing.shipImgs[playing.shipAni], (int) (100 * Game.SCALE) - playing.xLvlOffset, (int) ((288 * Game.SCALE) + playing.shipHeightDelta), (int) (78 * Game.SCALE), (int) (72 * Game.SCALE), null);



        playing.levelManager.draw(g, playing.xLvlOffset);
        playing.objectManager.draw(g, playing.xLvlOffset);
        //By Hung
        playing.getCreateBoard().create(g);
        playing.enemyManager.draw(g, playing.xLvlOffset);
        playing.player.render(g, playing.xLvlOffset);
        playing.objectManager.drawBackgroundTrees(g, playing.xLvlOffset);
        drawDialogue(g, playing.xLvlOffset);

        if (playing.paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            playing.pauseOverlay.draw(g);
        } else if (playing.gameOver)
            playing.gameOverOverlay.draw(g);
        else if (playing.lvlCompleted)
            playing.levelCompletedOverlay.draw(g);
        else if (playing.gameCompleted)
            playing.gameCompletedOverlay.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!playing.gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1)
                playing.player.setAttacking(true);
            else if (e.getButton() == MouseEvent.BUTTON3)
                playing.player.powerAttack();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (playing.gameOver)
            playing.gameOverOverlay.mousePressed(e);
        else if (playing.paused)
            playing.pauseOverlay.mousePressed(e);
        else if (playing.lvlCompleted)
            playing.levelCompletedOverlay.mousePressed(e);
        else if (playing.gameCompleted)
            playing.gameCompletedOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (playing.gameOver)
            playing.gameOverOverlay.mouseReleased(e);
        else if (playing.paused)
            playing.pauseOverlay.mouseReleased(e);
        else if (playing.lvlCompleted)
            playing.levelCompletedOverlay.mouseReleased(e);
        else if (playing.gameCompleted)
            playing.gameCompletedOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (playing.gameOver)
            playing.gameOverOverlay.mouseMoved(e);
        else if (playing.paused)
            playing.pauseOverlay.mouseMoved(e);
        else if (playing.lvlCompleted)
            playing.levelCompletedOverlay.mouseMoved(e);
        else if (playing.gameCompleted)
            playing.gameCompletedOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!playing.gameOver && !playing.gameCompleted && !playing.lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    playing.player.setLeft(true);
                    break;
                case KeyEvent.VK_D:

                    playing.player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    playing.player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    playing.paused = !playing.paused;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!playing.gameOver && !playing.gameCompleted && !playing.lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    playing.player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    playing.player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    playing.player.setJump(false);
                    break;
            }
    }
}
