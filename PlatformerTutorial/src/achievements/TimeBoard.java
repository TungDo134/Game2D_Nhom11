package achievements;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TimeBoard extends ABoard  {
    private BufferedImage timeBoard;

    public TimeBoard(){
        loadImage();
    }


    @Override
    public void draw(Graphics g,int xloca,int yloca) {
        g.drawImage(timeBoard, xloca, yloca, statusBarWidth, statusBarHeight, null);
        drawNumber(g,xloca,yloca);
    }
    public void drawNumber(Graphics g,int xloca,int yloca) {
            if (time != 0) {
                int nuSecond = time % 60;
                String scoreStr = String.valueOf(nuSecond);
                int digitWidth = (int) (30 * Game.SCALE);

                int x = xloca + 45 + (digitWidth);
                int y = yloca + 50;
                for (int i = 0; i < scoreStr.length(); i++) {
                    char character = scoreStr.charAt(i);
                    int digitInt = Character.getNumericValue(character);
                    g.drawImage(numbers[digitInt], x + (30 * i), y, (int) (30 * Game.SCALE), (int) (30 * Game.SCALE), null);
                }
            }
        if (time != 0) {
            int nuSecond = time / 60;
            String scoreStr = String.valueOf(nuSecond);
            int digitWidth = (int) (30 * Game.SCALE);

            int x = xloca -40 + (digitWidth);
            int y = yloca + 50;
            for (int i = 0; i < scoreStr.length(); i++) {
                char character = scoreStr.charAt(i);
                int digitInt = Character.getNumericValue(character);
                g.drawImage(numbers[digitInt], x + (30 * i), y, (int) (30 * Game.SCALE), (int) (30 * Game.SCALE), null);
            }
        }

    }


    @Override
    public void loadImage() {
        timeBoard = LoadSave.GetSpriteAtlas(LoadSave.TIME_BOARD);
    }


}
