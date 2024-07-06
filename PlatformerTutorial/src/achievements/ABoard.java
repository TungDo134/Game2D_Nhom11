package achievements;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ABoard implements Board ,Observer{
    protected BufferedImage[] numbers;
    protected int statusBarWidth = (int) (100 * Game.SCALE);
    protected int statusBarHeight = (int) (58 * Game.SCALE);
    protected int time ;
    protected int lives ;
    protected int score ;


    public ABoard(){
        loadImgsNu();
    }

    @Override
    public abstract void draw(Graphics g,int x ,int y) ;

    @Override
    public abstract void loadImage() ;
    protected void loadImgsNu() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.NUMBER);
        numbers = new BufferedImage[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = temp.getSubimage(i * 28, 0, 28, 30);
        }
    }
    @Override
    public void updateInput(int score ,int time , int lives){
        this.time = time ;
        this.score=score;
        this.lives= lives;
    }
}
