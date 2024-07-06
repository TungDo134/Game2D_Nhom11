package entities;

import java.awt.*;

public class PlayerView {
    private Player1 player1;


    public PlayerView(Player1 player1) {

        this.player1 = player1;
    }



    public void render(Graphics g, int lvlOffset) {
        g.drawImage(player1.animations[player1.state][player1.aniIndex], (int) (player1.hitbox.x - player1.getxDrawOffset()) - lvlOffset + player1.getFlipX(), (int) (player1.hitbox.y - player1.getyDrawOffset() + (int) (player1.pushDrawOffset)), player1.width * player1.getFlipW(), player1.height, null);
//		drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        // Background ui
        g.drawImage(player1.getStatusBarImg(), player1.getStatusBarX(), player1.getStatusBarY(), player1.getStatusBarWidth(), player1.getStatusBarHeight(), null);

        // Health bar
        g.setColor(Color.red);
        g.fillRect(player1.getStatusBarX() + player1.getStatusBarX(), player1.getHealthBarYStart() + player1.getStatusBarY(), player1.getHealthWidth(), player1.getHealthBarHeight());

        // Power Bar
        g.setColor(Color.yellow);
        g.fillRect(player1.getPowerBarXStart() + player1.getStatusBarX(), player1.getPowerBarYStart() + player1.getStatusBarY(), player1.getPowerWidth(), player1.getPowerBarHeight());
    }


}
