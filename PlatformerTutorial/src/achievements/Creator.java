package achievements;

import gamestates.Playing;
import main.Game;

import java.awt.*;


public class Creator implements CreateBoard{
    private int maxAchievementX = (int) (700 * Game.SCALE);
    private int achievementY = (int) (10 * Game.SCALE);
    private int widthAchievement = (int) (100 * Game.SCALE);
        private ABoard board;
    private Playing playing;

    public Creator ( Playing playing){
        this.playing=playing;
    }
    @Override
    public void create(Graphics g) {
        createScoreBoard(g,maxAchievementX-(widthAchievement),achievementY);
        createTimeBoard(g,maxAchievementX-(widthAchievement*2),achievementY);
        createSLivesBoard(g,maxAchievementX-(widthAchievement*3),achievementY);
    }


    public void createScoreBoard(Graphics g,int x, int y) {
        board =(ABoard) playing.getBoardScore();
        board.draw(g,x,y);
    }


    public void createTimeBoard(Graphics g,int x, int y) {
        board =(ABoard) playing.getBoardTime();
        board.draw(g,x,y);
    }


    public void createSLivesBoard(Graphics g,int x, int y) {
        board = (ABoard) playing.getBoardLives();
        board.draw(g,x,y);
    }



}
