package application.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.Settings;

public class GameModel {
	private Player player;
	// Lista di nemici
	private List<Tile> tiles;
	private static GameModel game = null;
	private int gravity;
	
	private GameModel() {
		gravity = 12;
		player = new Player(Settings.INITIAL_POSITION_X, Settings.INITIAL_POSITION_Y);
		tiles = new ArrayList<Tile>();
		tilesInitForTestPurposes();
	}
	//Va rimpiazzato con il load dei livelli
	private void tilesInitForTestPurposes() {
		for(int i = 0; i < 20;i++) {
			tiles.add(new Tile(i*Settings.TILE_WIDHT, 500, Settings.TILE_WIDHT, Settings.TILE_HEIGHT));
			tiles.add(new Tile(500,i*32,32,32));
			tiles.add(new Tile(0, i*Settings.TILE_WIDHT, Settings.TILE_WIDHT, Settings.TILE_HEIGHT));
			if(i > 5 && i < 10) {
				tiles.add(new Tile(i*Settings.TILE_WIDHT, 300, Settings.TILE_WIDHT, Settings.TILE_HEIGHT));
			}
		}
	}
	public List<Tile> getTiles() {
		return tiles;
	}
	public static GameModel getInstance() {
		if(game == null) game = new GameModel();
		return game;
	}
	public int getGravity() {
		return gravity;
	}
	/*Funzione chiamata dall'update del controller per avere un movimento più fluido*/
	public void update() {
		player.update();
	}
	
	public void movePlayer(int direction) {
		player.direction = direction;
	}
	public Player getPlayer() {
		return player;
	}
}
