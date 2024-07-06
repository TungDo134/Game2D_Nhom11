package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameOverOverlayController implements UiMethods{

    private final Playing playing;
    GameOverOverlay gameOverOverlay;

    public GameOverOverlayController(GameOverOverlay gameOverOverlay, Playing playing) {
        this.gameOverOverlay = gameOverOverlay;
        this.playing = playing;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(gameOverOverlay.img,  gameOverOverlay.imgX, gameOverOverlay.imgY, gameOverOverlay.imgW, gameOverOverlay.imgH, null);

        gameOverOverlay.menu.draw(g);
        gameOverOverlay.play.draw(g);
    }

    @Override
    public void update() {
        gameOverOverlay.menu.update();
        gameOverOverlay.play.update();
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameOverOverlay.play.setMouseOver(false);
        gameOverOverlay.menu.setMouseOver(false);

        if (isIn(gameOverOverlay.menu, e))
            gameOverOverlay.menu.setMouseOver(true);
        else if (isIn(gameOverOverlay.play, e))
            gameOverOverlay.play.setMouseOver(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(gameOverOverlay.menu, e)) {
            if (gameOverOverlay.menu.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
            }
        } else if (isIn(gameOverOverlay.play, e))
            if (gameOverOverlay.play.isMousePressed()) {
                playing.resetAll();
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }

        gameOverOverlay.menu.resetBools();
        gameOverOverlay.play.resetBools();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(gameOverOverlay.menu, e))
            gameOverOverlay.menu.setMousePressed(true);
        else if (isIn(gameOverOverlay.play, e))
            gameOverOverlay.play.setMousePressed(true);
    }
}
