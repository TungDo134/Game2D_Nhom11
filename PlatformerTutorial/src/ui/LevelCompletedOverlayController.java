package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LevelCompletedOverlayController implements UiMethods{

    private final Playing playing;
    LevelCompletedOverlay levelCompletedOverlay;

    public LevelCompletedOverlayController(LevelCompletedOverlay levelCompletedOverlay, Playing playing) {
        this.levelCompletedOverlay = levelCompletedOverlay;
        this.playing = playing;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(levelCompletedOverlay.img, levelCompletedOverlay.bgX, levelCompletedOverlay.bgY, levelCompletedOverlay.bgW, levelCompletedOverlay.bgH, null);
        levelCompletedOverlay.next.draw(g);
        levelCompletedOverlay.menu.draw(g);
    }

    @Override
    public void update() {
        levelCompletedOverlay.next.update();
        levelCompletedOverlay.menu.update();
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        levelCompletedOverlay.next.setMouseOver(false);
        levelCompletedOverlay.menu.setMouseOver(false);

        if (isIn(levelCompletedOverlay.menu, e))
            levelCompletedOverlay.menu.setMouseOver(true);
        else if (isIn(levelCompletedOverlay.next, e))
            levelCompletedOverlay.next.setMouseOver(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(levelCompletedOverlay.menu, e)) {
            if (levelCompletedOverlay.menu.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
            }
        } else if (isIn(levelCompletedOverlay.next, e))
            if (levelCompletedOverlay.next.isMousePressed()) {
                playing.loadNextLevel();
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }

        levelCompletedOverlay.menu.resetBools();
        levelCompletedOverlay.next.resetBools();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(levelCompletedOverlay.menu, e))
            levelCompletedOverlay.menu.setMousePressed(true);
        else if (isIn(levelCompletedOverlay.next, e))
            levelCompletedOverlay.next.setMousePressed(true);
    }
}
