package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.UI.URMButtons.*;

public class PauseOverlay {

    private final PauseOverlayController pauseOverlayController;
    final Playing playing;
    BufferedImage backgroundImg;
    int bgX, bgY, bgW, bgH;
    final AudioOptions audioOptions;
    UrmButton menuB, replayB, unpauseB;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        pauseOverlayController = new PauseOverlayController(this, playing);
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
        pauseOverlayController.update();
    }

    public void draw(Graphics g) {
        pauseOverlayController.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
        pauseOverlayController.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        pauseOverlayController.mouseReleased(e);

    }

    public void mouseMoved(MouseEvent e) {
        pauseOverlayController.mouseMoved(e);
    }


}
