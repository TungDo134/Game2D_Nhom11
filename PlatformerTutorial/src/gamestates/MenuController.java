package gamestates;

import main.Game;
import ui.MenuButton_Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MenuController implements Statemethods {
    private Menu menu;
    private Game game;
    private MenuButton_Model[] buttons;
    private BufferedImage backgroundImgPink;
    private BufferedImage backgroundImg;
    private int menuX;
    private int menuY;
    private int menuWidth;
    private int menuHeight;

    public MenuController(Menu menu, Game game) {
        this.menu = menu;
        this.game = game;
        this.buttons = menu.getButtons();
        this.backgroundImgPink = menu.getBackgroundImgPink();
        this.backgroundImg = menu.getBackgroundImg();
        this.menuX = menu.getMenuX();
        this.menuY = menu.getMenuY();
        this.menuWidth = menu.getMenuWidth();
        this.menuHeight = menu.getMenuHeight();

    }

    @Override
    public void update() {
        for (MenuButton_Model mb : buttons)
            mb.getMenuButton_controller().update();
    }

    private void resetButtons() {
        for (MenuButton_Model mb : buttons)
            mb.getMenuButton_controller().resetBools();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton_Model mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e1) {
        for (MenuButton_Model mb1 : buttons) {
            if (menu.isIn(e1, mb1)) {

                mb1.getMenuButton_controller().setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton_Model mb : buttons) {
            if (menu.isIn(e, mb)) {
                if (mb.getMenuButton_controller().isMousePressed())
                    mb.applyGamestate();
                if (mb.getState() == Gamestate.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton_Model mb : buttons)
            mb.getMenuButton_controller().setMouseOver(false);

        for (MenuButton_Model mb : buttons)
            if (menu.isIn(e, mb)) {
                mb.getMenuButton_controller().setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
