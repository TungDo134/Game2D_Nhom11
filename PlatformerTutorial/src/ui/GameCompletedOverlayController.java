package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameCompletedOverlayController implements UiMethods{
    private final Playing playing;
    GameCompletedOverlay gameCompletedOverlay;

    public GameCompletedOverlayController(GameCompletedOverlay gameCompletedOverlay, Playing playing) {
        this.gameCompletedOverlay = gameCompletedOverlay;
        this.playing = playing;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(gameCompletedOverlay.img, gameCompletedOverlay.imgX, gameCompletedOverlay.imgY, gameCompletedOverlay.imgW, gameCompletedOverlay.imgH, null);

        gameCompletedOverlay.credit.draw(g);
        gameCompletedOverlay.quit.draw(g);
    }

    @Override
    public void update() {
        gameCompletedOverlay.credit.getMenuButton_controller().update();
        gameCompletedOverlay.quit.getMenuButton_controller().update();
    }
    private boolean isIn(MenuButton_Model b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameCompletedOverlay.credit.getMenuButton_controller().setMouseOver(false);
        gameCompletedOverlay.quit.getMenuButton_controller().setMouseOver(false);

        if (isIn(gameCompletedOverlay.quit, e))
            gameCompletedOverlay.quit.getMenuButton_controller().setMouseOver(true);
        else if (isIn(gameCompletedOverlay.credit, e))
            gameCompletedOverlay.credit.getMenuButton_controller().setMouseOver(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(gameCompletedOverlay.quit, e)) {
            if (gameCompletedOverlay.quit.getMenuButton_controller().isMousePressed()) {
                playing.resetAll();
                playing.resetGameCompleted();
                playing.setGamestate(Gamestate.MENU);

            }
        } else if (isIn(gameCompletedOverlay.credit, e))
            if (gameCompletedOverlay.credit.getMenuButton_controller().isMousePressed()) {
                playing.resetAll();
                playing.resetGameCompleted();
                playing.setGamestate(Gamestate.CHARACTER);
            }

        gameCompletedOverlay.quit.getMenuButton_controller().resetBools();
        gameCompletedOverlay.credit.getMenuButton_controller().resetBools();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(gameCompletedOverlay.quit, e))
            gameCompletedOverlay.quit.getMenuButton_controller().setMousePressed(true);
        else if (isIn(gameCompletedOverlay.credit, e))
            gameCompletedOverlay.credit.getMenuButton_controller().setMousePressed(true);
    }
}
