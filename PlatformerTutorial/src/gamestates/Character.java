package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import entities.Player;
import entities.Player2;
import main.Game;
import utilz.LoadSave;

public class Character extends State implements Statemethods {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private float bgYFloat;
    private int bgX1, bgY1, bgW1, bgH1;
    private int bgX2, bgY2, bgW2, bgH2;
    private Rectangle hitbox1;
    BufferedImage charImg1, charImg2;
//    private Player player;
//    private Player2 player2;
//    Entity entity;
    Playing playing;
    private ArrayList<ShowEntity> entitiesList;

    public Character(Game game,Playing playing) {
        super(game);
        this.playing = playing;
        charImg1 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_OP2);
        charImg2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_OP1_NEW);
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

        // Set dimensions for the first image
        bgW1 = (int) (charImg1.getWidth() * Game.SCALE);
        bgH1 = (int) (charImg1.getHeight() * Game.SCALE);
        bgX1 = Game.GAME_WIDTH / 2 - bgW1 / 2 - 50; // Adjusted for centering both images
        bgY1 = Game.GAME_HEIGHT / 2 - bgH1 / 2;
        // Set dimensions for the second image
        bgW2 = (int) (charImg2.getWidth() * Game.SCALE);
        bgH2 = (int) (charImg2.getHeight() * Game.SCALE);
        bgX2 = Game.GAME_WIDTH / 2 - bgW2 / 2 + 50; // Adjusted for centering both images
        bgY2 = Game.GAME_HEIGHT / 2 - bgH2 / 2;

        hitbox1 = new Rectangle(bgX1, bgY1, bgW1, bgH1);
        loadEntities();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bgYFloat = 0;
            setGamestate(Gamestate.MENU);
        }
        if (e.getKeyCode() == KeyEvent.VK_1) {
            playing.resetGameWithPlayer(1);
        } else if(e.getKeyCode() == KeyEvent.VK_2){
            playing.resetGameWithPlayer(2);
        }
    }


    private void loadEntities() {
        entitiesList = new ArrayList<>();
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.NEW_PLAYER), 5, 64, 40), (int) (Game.GAME_WIDTH * 0.05), (int) (Game.GAME_HEIGHT * 0.8)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS), 5, 64, 40), (int) (Game.GAME_WIDTH * 0.05), (int) (Game.GAME_HEIGHT * 0.8)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE), 9, 72, 32), (int) (Game.GAME_WIDTH * 0.15), (int) (Game.GAME_HEIGHT * 0.75)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.7), (int) (Game.GAME_HEIGHT * 0.75)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.8), (int) (Game.GAME_HEIGHT * 0.8)));
    }

    private BufferedImage[] getIdleAni(BufferedImage atlas, int spritesAmount, int width, int height) {
        BufferedImage[] arr = new BufferedImage[spritesAmount];
        for (int i = 0; i < spritesAmount; i++)
            arr[i] = atlas.getSubimage(width * i, 0, width, height);
        return arr;
    }

    @Override
    public void update() {
//		bgYFloat -= 0.2f;
        for (ShowEntity se : entitiesList)
            se.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(charImg1, bgX1, bgY1, bgW1, bgH1, null);
        g.drawImage(charImg2, bgX2, bgY2, bgW2, bgH2, null);

        for (ShowEntity se : entitiesList)
            se.draw(g);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    private class ShowEntity {
        private BufferedImage[] idleAnimation;
        private int x, y, aniIndex, aniTick;

        public ShowEntity(BufferedImage[] idleAnimation, int x, int y) {
            this.idleAnimation = idleAnimation;
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.drawImage(idleAnimation[aniIndex], x, y, (int) (idleAnimation[aniIndex].getWidth() * 4), (int) (idleAnimation[aniIndex].getHeight() * 4), null);
        }

        public void update() {
            aniTick++;
            if (aniTick >= 25) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= idleAnimation.length)
                    aniIndex = 0;
            }

        }
    }

}
