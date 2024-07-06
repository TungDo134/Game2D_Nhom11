package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameCompletedOverlay extends Rank implements DrawMethodRank{
	private final GameCompletedOverlayController gameCompletedOverlayController;
	final Playing playing;
	BufferedImage img;
	MenuButton_Model quit, credit;
	int imgX, imgY, imgW, imgH;

	// Huy
	private BufferedImage[] numbers;
	private BufferedImage rankImg;

	public GameCompletedOverlay(Playing playing) {
		this.playing = playing;
		gameCompletedOverlayController = new GameCompletedOverlayController(this, playing);
		rankImg = LoadSave.GetSpriteAtlas(LoadSave.RANK);
		loadImgs();
		createImg();
		createButtons();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.NUMBER);
		numbers = new BufferedImage[10];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = temp.getSubimage(i * 28, 0, 28, 30);
		}
	}

	private void createButtons() {
		quit = new MenuButton_Model(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 2, Gamestate.MENU);
		credit = new MenuButton_Model(Game.GAME_WIDTH / 2, (int) (200 * Game.SCALE), 3, Gamestate.CHARACTER);
	}

	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAME_COMPLETED);
		imgW = (int) (img.getWidth() * Game.SCALE);
		imgH = (int) (img.getHeight() * Game.SCALE);
		imgX = Game.GAME_WIDTH / 2 - imgW / 2;
		imgY = (int) (100 * Game.SCALE);

	}

	public void draw(Graphics g) {
		gameCompletedOverlayController.draw(g);
		drawMethodRank(g);
	}

	public void update() {
		gameCompletedOverlayController.update();
	}

	public void mouseMoved(MouseEvent e) {
		gameCompletedOverlayController.mouseMoved(e);
	}

	public void mouseReleased(MouseEvent e) {
		gameCompletedOverlayController.mouseReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		gameCompletedOverlayController.mousePressed(e);
	}

	@Override
	public void drawMethodRank(Graphics g) {
		g.drawImage(rankImg, 150, 10, 400, 800, null);
		drawTimeAndScore(g);
	}

	@Override
	public void drawTimeAndScore(Graphics g) {
		System.out.println(timeScoreMap.size());
		int y = 155;
		for (Map.Entry<Integer, Integer> entry : timeScoreMap.entrySet()) {
			String timeStr = String.valueOf(entry.getKey());
			String scoreStr = String.valueOf(entry.getValue());
			System.out.println(timeStr + " " + scoreStr);

			char[] timeChars = timeStr.toCharArray();
			char[] scoreChars = scoreStr.toCharArray();

			// ve thoi gian
			int xW = 250;
			for (char c : timeChars) {
				int num = Character.getNumericValue(c);
				g.drawImage(numbers[num], xW, y, (int) (28 * Game.SCALE), (int) (30 * Game.SCALE), null);
				xW += numbers[num].getWidth() + 5;
			}
			// ve diem
			int x = 450;
			for (char c : scoreChars) {
				int num = Character.getNumericValue(c);
				g.drawImage(numbers[num], x, y, (int) (28 * Game.SCALE), (int) (30 * Game.SCALE), null);
				x += numbers[num].getWidth() + 5;
			}
			y += 40;
		}
	}
}
