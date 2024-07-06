package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay extends Rank implements DrawMethodRank {

    final Playing playing;
    LevelCompletedOverlayController levelCompletedOverlayController;
    UrmButton menu, next;
    BufferedImage img;
    int bgX, bgY, bgW, bgH;

    // Huy
    private BufferedImage[] numbers;
    private BufferedImage rankImg;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        levelCompletedOverlayController = new LevelCompletedOverlayController(this, playing);
        rankImg = LoadSave.GetSpriteAtlas(LoadSave.RANK);
        loadImgs();
        initImg();
        initButtons();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.NUMBER);
        numbers = new BufferedImage[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = temp.getSubimage(i * 28, 0, 28, 30);
        }
    }

    private void initButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {
        levelCompletedOverlayController.draw(g);
        drawMethodRank(g);
    }

    public void update() {
        levelCompletedOverlayController.update();
    }


    public void mouseMoved(MouseEvent e) {
        levelCompletedOverlayController.mouseMoved(e);
    }

    public void mouseReleased(MouseEvent e) {
        levelCompletedOverlayController.mouseReleased(e);
    }

    public void mousePressed(MouseEvent e) {
        levelCompletedOverlayController.mousePressed(e);
    }

    @Override
    public void drawMethodRank(Graphics g) {
        g.drawImage(rankImg, 150, 10, 400, 800, null);
        drawTimeAndScore(g);
    }

    @Override
    public void drawTimeAndScore(Graphics g) {
        System.out.println(timeScoreMap.size());
        int y = 155;
        for (Map.Entry<Integer, Integer> entry : timeScoreMap.entrySet()) {
            String timeStr = String.valueOf(entry.getKey());
            String scoreStr = String.valueOf(entry.getValue());
            System.out.println(timeStr + " " + scoreStr);

            char[] timeChars = timeStr.toCharArray();
            char[] scoreChars = scoreStr.toCharArray();

            // ve thoi gian
            int xW = 250;
            for (char c : timeChars) {
                int num = Character.getNumericValue(c);
                g.drawImage(numbers[num], xW, y, (int) (28 * Game.SCALE), (int) (30 * Game.SCALE), null);
                xW += numbers[num].getWidth() + 5;
            }
            // ve diem
            int x = 450;
            for (char c : scoreChars) {
                int num = Character.getNumericValue(c);
                g.drawImage(numbers[num], x, y, (int) (28 * Game.SCALE), (int) (30 * Game.SCALE), null);
                x += numbers[num].getWidth() + 5;
            }
            y += 50;
        }
    }
}
