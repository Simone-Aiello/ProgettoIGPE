package application.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Settings;

public class LevelChanger implements Runnable {
	private List<Enemy> nextEnemies;
	private List<Tile> nextTiles;
	private int currentLevel;
	private Player playerOne;
	private Player playerTwo;
	public LevelChanger(Player playerOne,Player playerTwo) {
		this.currentLevel = -1;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.run();
	}

	@Override
	public void run() {
		try {
			currentLevel++;
			nextEnemies = new ArrayList<Enemy>();
			nextTiles = new ArrayList<Tile>();
			BufferedReader in = new BufferedReader(
					new FileReader(getClass().getResource("/application/resources/level/Level"+ currentLevel + ".csv").getFile()));
			int j = 0;
			while (in.ready()) {
				String s = in.readLine();
				String[] v = s.split(",");
				for (int i = 0; i < v.length; i++) {
					if (v[i].equals("0")) {
						nextTiles.add(new Tile(i * Settings.TILE_WIDHT, j * Settings.TILE_HEIGHT, Settings.TILE_WIDHT,
								Settings.TILE_HEIGHT));						
					}
					else if (v[i].equals("2")) {
						nextEnemies.add(new RobotEnemy(i * Settings.TILE_WIDHT, j * Settings.TILE_HEIGHT));						
					}
					else if(v[i].equals("3")) {
						nextEnemies.add(new MageEnemy(i * Settings.TILE_WIDHT, j * Settings.TILE_HEIGHT,playerOne,playerTwo));	
					}
					else if(v[i].equals("4")) {
						nextEnemies.add(new SpringEnemy(i * Settings.TILE_WIDHT, j * Settings.TILE_HEIGHT));	
					}
				}
				j++;
			}
			in.close();
		} catch (Exception e) {
			nextEnemies = null;
			nextTiles = null;
			return;
		}
	}

	public List<Enemy> getNextEnemies() {
		return nextEnemies;
	}

	public List<Tile> getNextTiles() {
		return nextTiles;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}
}
