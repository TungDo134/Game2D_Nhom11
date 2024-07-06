package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton_Model;
import utilz.LoadSave;

public class Menu extends State {
    private MenuController menuController;
    private MenuButton_Model[] buttons = new MenuButton_Model[4];
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        menuController = new MenuController(this, game); // Instantiate MenuController with Menu and Game references
    }

    public void loadButtons() {
        buttons[0] = new MenuButton_Model(Game.GAME_WIDTH / 2, (int) (130 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton_Model(Game.GAME_WIDTH / 2, (int) (200 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton_Model(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 3, Gamestate.CHARACTER);
        buttons[3] = new MenuButton_Model(Game.GAME_WIDTH / 2, (int) (340 * Game.SCALE), 2, Gamestate.QUIT);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    public MenuButton_Model[] getButtons() {
        return buttons;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public BufferedImage getBackgroundImg() {
        return backgroundImg;
    }

    public BufferedImage getBackgroundImgPink() {
        return backgroundImgPink;
    }

    public int getMenuX() {
        return menuX;
    }

    public int getMenuY() {
        return menuY;
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }


}
