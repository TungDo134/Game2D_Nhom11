package entities;

import java.awt.*;

public class PlayerView_2 {
    private Player2 player2;

    public PlayerView_2(Player2 player2) {

        this.player2 = player2;
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(player2.animations[player2.state][player2.aniIndex], (int) (player2.hitbox.x - player2.getxDrawOffset()) - lvlOffset + player2.getFlipX(), (int) (player2.hitbox.y - player2.getyDrawOffset() + (int) (player2.pushDrawOffset)), player2.width * player2.getFlipW(), player2.height, null);
//		drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        // Background ui
        g.drawImage(player2.getStatusBarImg(), player2.getStatusBarX(), player2.getStatusBarY(), player2.getStatusBarWidth(), player2.getStatusBarHeight(), null);

        // Health bar
        g.setColor(Color.red);
        g.fillRect(player2.getStatusBarX() + player2.getStatusBarX(), player2.getHealthBarYStart() + player2.getStatusBarY(), player2.getHealthWidth(), player2.getHealthBarHeight());

        // Power Bar
        g.setColor(Color.yellow);
        g.fillRect(player2.getPowerBarXStart() + player2.getStatusBarX(), player2.getPowerBarYStart() + player2.getStatusBarY(), player2.getPowerWidth(), player2.getPowerBarHeight());
    }
}
