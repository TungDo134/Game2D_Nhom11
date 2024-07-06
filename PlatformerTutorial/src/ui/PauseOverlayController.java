package ui;

import gamestates.Gamestate;
import gamestates.Playing;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseOverlayController implements UiMethods{

    private final Playing playing;
    PauseOverlay pauseOverlay;

    public PauseOverlayController(PauseOverlay pauseOverlay, Playing playing) {
        this.pauseOverlay = pauseOverlay;
        this.playing = playing;
    }

    @Override
    public void draw(Graphics g) {
        // Background
        g.drawImage(pauseOverlay.backgroundImg, pauseOverlay.bgX, pauseOverlay.bgY, pauseOverlay.bgW, pauseOverlay.bgH, null);

        // UrmButtons
        pauseOverlay.menuB.draw(g);
        pauseOverlay.replayB.draw(g);
        pauseOverlay.unpauseB.draw(g);

        pauseOverlay.audioOptions.draw(g);
    }

    @Override
    public void update() {
        pauseOverlay.menuB.update();
        pauseOverlay.replayB.update();
        pauseOverlay.unpauseB.update();

        pauseOverlay.audioOptions.update();
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pauseOverlay.menuB.setMouseOver(false);
        pauseOverlay.replayB.setMouseOver(false);
        pauseOverlay.unpauseB.setMouseOver(false);

        if (isIn(e, pauseOverlay.menuB))
            pauseOverlay.menuB.setMouseOver(true);
        else if (isIn(e, pauseOverlay.replayB))
            pauseOverlay.replayB.setMouseOver(true);
        else if (isIn(e, pauseOverlay.unpauseB))
            pauseOverlay.unpauseB.setMouseOver(true);
        else
            pauseOverlay.audioOptions.mouseMoved(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, pauseOverlay.menuB)) {
            if (pauseOverlay.menuB.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
                playing.unpauseGame();
            }
        } else if (isIn(e, pauseOverlay.replayB)) {
            if (pauseOverlay.replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(e, pauseOverlay.unpauseB)) {
            if (pauseOverlay.unpauseB.isMousePressed())
                playing.unpauseGame();
        } else
            pauseOverlay.audioOptions.mouseReleased(e);

        pauseOverlay.menuB.resetBools();
        pauseOverlay.replayB.resetBools();
        pauseOverlay.unpauseB.resetBools();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, pauseOverlay.menuB))
            pauseOverlay.menuB.setMousePressed(true);
        else if (isIn(e, pauseOverlay.replayB))
            pauseOverlay.replayB.setMousePressed(true);
        else if (isIn(e, pauseOverlay.unpauseB))
            pauseOverlay.unpauseB.setMousePressed(true);
        else
            pauseOverlay.audioOptions.mousePressed(e);
    }
}
