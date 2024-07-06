package ui;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay extends Rank implements DrawMethodRank {

    final Playing playing;
    GameOverOverlayController gameOverOverlayController;
    BufferedImage img;
    int imgX, imgY, imgW, imgH;
    UrmButton menu, play;

    // Huy
    private BufferedImage[] numbers;
    private BufferedImage rankImg;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        gameOverOverlayController = new GameOverOverlayController(this, playing);
        rankImg = LoadSave.GetSpriteAtlas(LoadSave.RANK);
        loadImgs();
        createImg();
        createButtons();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.NUMBER);
        numbers = new BufferedImage[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = temp.getSubimage(i * 28, 0, 28, 30);
        }
    }

    private void createButtons() {
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);

    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW = (int) (img.getWidth() * Game.SCALE);
        imgH = (int) (img.getHeight() * Game.SCALE);
        imgX = Game.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (100 * Game.SCALE);

    }

    public void draw(Graphics g) {
        gameOverOverlayController.draw(g);
        drawMethodRank(g);
    }

    public void update() {
        gameOverOverlayController.update();
    }


    public void mouseMoved(MouseEvent e) {
        gameOverOverlayController.mouseMoved(e);
    }

    public void mouseReleased(MouseEvent e) {
        gameOverOverlayController.mouseReleased(e);
    }

    public void mousePressed(MouseEvent e) {
        gameOverOverlayController.mousePressed(e);
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
