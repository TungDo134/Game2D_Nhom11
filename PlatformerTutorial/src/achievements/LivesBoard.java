package achievements;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LivesBoard extends ABoard {
    private BufferedImage board;
    public LivesBoard(){
        loadImage();
    }
    @Override
    public  void draw(Graphics g,int x,int y){
        g.drawImage(board, x, y, statusBarWidth, statusBarHeight, null);
        g.drawImage(numbers[lives],x+50, y+50,(int) (30 * Game.SCALE), (int) (30 * Game.SCALE), null);
    }

    @Override
    public  void loadImage() {

        board = LoadSave.GetSpriteAtlas(LoadSave.LIVES_BOARD);
    }

}
