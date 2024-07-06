package achievements;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScoreBoard extends ABoard {
    private BufferedImage board;
    public ScoreBoard (){
        loadImage();
    }


    @Override
    public void draw(Graphics g,int xLoca , int yloca){
        g.drawImage(board, xLoca, yloca, statusBarWidth, statusBarHeight, null);
        drawNumber(g,xLoca,yloca);
    }
    public void drawNumber(Graphics g,int xLoca , int yloca){
        String scoreStr = String.valueOf(score);
        int digitWidth = (int) (30 * Game.SCALE);
        int x = xLoca - 30+ digitWidth;
        int y = yloca + 50;
        for (int i = 0; i < scoreStr.length(); i++) {
            char character = scoreStr.charAt(i);
            int digitInt = Character.getNumericValue(character);
            g.drawImage(numbers[digitInt],x+(30*i), y,(int) (30 * Game.SCALE), (int) (30 * Game.SCALE), null);
        }
    }
    @Override
    public void loadImage() {
        board = LoadSave.GetSpriteAtlas(LoadSave.SCORE_BOARD);
    }

}
