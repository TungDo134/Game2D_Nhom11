package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;

import achievements.*;
import entities.EnemyManager;
import entities.Player;
import entities.Player1;
import entities.Player2;
import leaderboard.LeaderBoard;
import leaderboard.LeaderBoardObserver;
import levels.LevelManager;
import main.Game;
import object.ObjectManager;
import ui.*;
import utilz.LoadSave;
import effects.DialogueEffect;
import effects.Rain;

import static utilz.Constants.Dialogue.*;

public class Playing extends State {

    Player player;
    Character character;
    PlayingController controller;
    LevelManager levelManager;
    EnemyManager enemyManager;
    ObjectManager objectManager;
    PauseOverlay pauseOverlay;
    GameOverOverlay gameOverOverlay;
    GameCompletedOverlay gameCompletedOverlay;
    LevelCompletedOverlay levelCompletedOverlay;
    Rain rain;

    boolean paused = false;

    int xLvlOffset;
    int maxLvlOffsetX;

    BufferedImage backgroundImg, bigCloud, smallCloud, shipImgs[];
    BufferedImage[] questionImgs, exclamationImgs;
    int leftBorder = (int) (0.25 * Game.GAME_WIDTH);
    int rightBorder = (int) (0.75 * Game.GAME_WIDTH);
    ArrayList<DialogueEffect> dialogEffects = new ArrayList<>();

    int[] smallCloudsPos;
    Random rnd = new Random();

    boolean gameOver;
    boolean lvlCompleted;
    boolean gameCompleted;
    boolean playerDying;
    boolean drawRain;

    boolean drawShip = true;
    int shipAni, shipTick, shipDir = 1;
    float shipHeightDelta, shipHeightChange = 0.05f * Game.SCALE;

    // Board Part By Hung
    private CreateBoard createBoard;
    private Observer boardTime = new TimeBoard() ;
    private Observer boardLives = new LivesBoard();
    private Observer boardScore = new ScoreBoard();

    //Huy
    private LeaderBoard leaderBoard;
    private GameCompletedOverlayController gameCompletedOverlayController;
    private GameOverOverlayController gameOverOverlayController;
    private LevelCompletedOverlayController levelCompletedOverlayController;
    private LeaderBoardObserver gameOverOverlayObserver, gameCompletedOverlayObserver, levelCompletedOverlayObserver;


    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));

        shipImgs = new BufferedImage[4];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SHIP);
        for (int i = 0; i < shipImgs.length; i++)
            shipImgs[i] = temp.getSubimage(i * 78, 0, 78, 72);

        loadDialogue();
        calcLvlOffset();
        loadStartLevel();
        setDrawRainBoolean();
    }


    private void loadDialogue() {
        loadDialogueImgs();

        for (int i = 0; i < 10; i++)
            dialogEffects.add(new DialogueEffect(0, 0, EXCLAMATION));
        for (int i = 0; i < 10; i++)
            dialogEffects.add(new DialogueEffect(0, 0, QUESTION));

        for (DialogueEffect de : dialogEffects)
            de.deactive();
    }

    private void loadDialogueImgs() {
        BufferedImage qtemp = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_ATLAS);
        questionImgs = new BufferedImage[5];
        for (int i = 0; i < questionImgs.length; i++)
            questionImgs[i] = qtemp.getSubimage(i * 14, 0, 14, 12);

        BufferedImage etemp = LoadSave.GetSpriteAtlas(LoadSave.EXCLAMATION_ATLAS);
        exclamationImgs = new BufferedImage[5];
        for (int i = 0; i < exclamationImgs.length; i++)
            exclamationImgs[i] = etemp.getSubimage(i * 14, 0, 14, 12);
    }

    public void loadNextLevel() {
        levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
        drawShip = false;
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }


    public void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        controller = new PlayingController(this);
        character = new Character(game,this);
        resetPlayer(1); // Initialize with Player1 by default

        //By Hung
        player.addObserver(this.boardLives);
        player.addObserver(this.boardScore);
        player.addObserver(this.boardTime);
        createBoard = new Creator(this);

        //Huy
        leaderBoard = new LeaderBoard();
        player.addObserver(leaderBoard);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);

        gameCompletedOverlay = new GameCompletedOverlay(this);
        gameCompletedOverlayController = new GameCompletedOverlayController(gameCompletedOverlay, this);
        //gameCompletedOverlayObserver = new GameCompletedOverlay(this);
        leaderBoard.addObserver(this.gameCompletedOverlay);

        levelCompletedOverlay = new LevelCompletedOverlay(this);
        levelCompletedOverlayController = new LevelCompletedOverlayController(levelCompletedOverlay, this);
        //levelCompletedOverlayObserver = new LevelCompletedOverlay(this);
        leaderBoard.addObserver(this.levelCompletedOverlay);

        gameOverOverlay = new GameOverOverlay(this);
        gameOverOverlayController = new GameOverOverlayController(gameOverOverlay, this);
        //gameOverOverlayObserver = new GameOverOverlay(this);
        leaderBoard.addObserver(this.gameOverOverlay);

        rain = new Rain();
    }

    public void resetGameWithPlayer(int playerNumber) {
        // Logic to reset the game state
        resetPlayer(playerNumber);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.notifyTimeScore();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        // Repaint or revalidate the game panel if necessary
        this.update();
    }

    private void resetPlayer(int playerNumber) {
        if (playerNumber == 1) {
            player = new Player1(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        } else if (playerNumber == 2) {
            player = new Player2(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        }
    }

    public void addDialogue(int x, int y, int type) {
        // Not adding a new one, we are recycling. #ThinkGreen lol
        dialogEffects.add(new DialogueEffect(x, y - (int) (Game.SCALE * 15), type));
        for (DialogueEffect de : dialogEffects)
            if (!de.isActive())
                if (de.getType() == type) {
                    de.reset(x, -(int) (Game.SCALE * 15));
                    return;
                }
    }

    public void setGameCompleted() {
        gameCompleted = true;
    }

    public void resetGameCompleted() {
        gameCompleted = false;
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        drawRain = false;

        setDrawRainBoolean();

        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
        dialogEffects.clear();
    }

    private void setDrawRainBoolean() {
        // This method makes it rain 20% of the time you load a level.
        if (rnd.nextFloat() >= 0.8f)
            drawRain = true;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox,player);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    // player2
    public void checkSpikesTouched(Player2 p2) {
        objectManager.checkSpikesTouched(p2);
    }


    public void mouseDragged(MouseEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        game.getAudioPlayer().lvlCompleted();
        if (levelManager.getLevelIndex() + 1 >= levelManager.getAmountOfLevels()) {
            // No more levels
            gameCompleted = true;
            levelManager.setLevelIndex(0);
            levelManager.loadNextLevel();
            resetAll();
            return;
        }
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }


    public void update() {
        controller.update();
    }


    public void draw(Graphics g) {
        controller.draw(g);
    }


    public void mouseClicked(MouseEvent e) {
        controller.mouseClicked(e);
    }


    public void mousePressed(MouseEvent e) {
        controller.mousePressed(e);
    }


    public void mouseReleased(MouseEvent e) {
        controller.mouseReleased(e);
    }


    public void mouseMoved(MouseEvent e) {
        controller.mouseMoved(e);
    }


    public void keyPressed(KeyEvent e) {
        controller.keyPressed(e);
    }


    public void keyReleased(KeyEvent e) {
        controller.keyReleased(e);
    }
    //By Hung
    public Observer getBoardTime() {
        return boardTime;
    }

    public Observer getBoardLives() {
        return boardLives;
    }

    public Observer getBoardScore() {
        return boardScore;
    }
    public CreateBoard getCreateBoard() {
        return createBoard;
    }
    public void counting(){
        player.increaseTime();
    }
}