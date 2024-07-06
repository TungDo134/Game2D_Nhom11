package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface UiMethods {
    void draw(Graphics g);
    void update();
    void mouseMoved(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mousePressed(MouseEvent e);
}
