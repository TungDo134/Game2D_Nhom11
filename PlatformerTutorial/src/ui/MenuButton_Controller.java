package ui;

public class MenuButton_Controller {
    MenuButton_Model menuButton_model;
    boolean mouseOver, mousePressed;
    int index;

    public MenuButton_Controller(MenuButton_Model menuButton_model) {
        this.menuButton_model = menuButton_model;
        this.index = menuButton_model.getIndex();
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
