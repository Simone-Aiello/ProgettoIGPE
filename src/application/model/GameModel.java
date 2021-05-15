package application.model;

import application.Settings;

public class GameModel {
	private Player player;
	// Lista di nemici
	// Lista delle tile
	private static GameModel game = null;
	private GameModel() {
		player = new Player(Settings.INITIAL_POSITION_X, Settings.INITIAL_POSITION_Y);
	}
	public static GameModel getInstance() {
		if(game == null) game = new GameModel();
		return game;
	}
	public void update() {
		player.update();
	}

	public void movePlayer(int direction) {
		if (direction == PlayerSettings.MOVE_RIGHT) {
			player.direction = direction;
		}
		else if(direction == PlayerSettings.MOVE_LEFT) {
			player.direction = direction;
		}
		else {
			player.direction = 100;
		}
	}
	public Player getPlayer() {
		return player;
	}
}
