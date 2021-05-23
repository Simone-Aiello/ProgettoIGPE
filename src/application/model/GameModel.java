package application.model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.Settings;

public class GameModel implements Serializable{

	private static final long serialVersionUID = -2605783339471189943L;
	
	private Player player;
	private List<Tile> tiles;
	private int gravity;
	public GameModel() {
		gravity = 12;
		player = new Player(Settings.INITIAL_POSITION_X, Settings.INITIAL_POSITION_Y);
		player.yspeed = gravity;
		tiles = new ArrayList<Tile>();
		tilesInitForTestPurposes();
	}
	//Getters
	public Player getPlayer() {
		return player;
	}
	public List<Tile> getTiles() {
		return tiles;
	}
	public int getGravity() {
		return gravity;
	}
	//Level handler
	private void tilesInitForTestPurposes() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(getClass().getResource("/application/resources/level/Level0.csv").getFile()));
			int j = 0;
			while(in.ready()) {
				String s = in.readLine();
				String [] v = s.split(",");
				for(int i = 0; i < v.length;i++) {
					if(v[i].equals("0")) tiles.add(new Tile(i*Settings.TILE_WIDHT,j*Settings.TILE_HEIGHT,Settings.TILE_WIDHT,Settings.TILE_HEIGHT));
				}
				j++;
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Cannot load the level");
		}
	}
	//Funzioni di update chiamate dal controller
	public void update() {
		updatePlayer(); 
	}
	public void movePlayer(int direction) {
		if(direction == PlayerSettings.IDLE_LEFT) {
			if(player.xState == PlayerSettings.MOVE_LEFT) player.xState = direction;
			return;
		}
		else if(direction == PlayerSettings.IDLE_RIGHT) {
			if(player.xState == PlayerSettings.MOVE_RIGHT) player.xState = direction;
			return;
		}
		else player.xState = direction;
	}
	public void handlePlayerJump() {
		if (!player.jumping) {
			if (WallCollisionHandler.touchingGround(player, tiles) && !WallCollisionHandler.currentlyInsideTiles(player, tiles)) {
				player.jumping = true;
				player.preJumpPos = player.y;
			}
		}
	}
	//Prima di muovere il player in una direzione, si calcolano le collisioni con un sistema di hitbox
	private void updatePlayer() {
		player.yState = PlayerSettings.Y_IDLE;
		if (player.jumping) { 
			if (player.y > 0 && player.y + player.yspeed > player.preJumpPos - 2.3 * Settings.PLAYER_DIMENSION ) {
				player.jump();
				player.yState = PlayerSettings.JUMPING;
			}
			else {
				player.jumping = false;				
			}
		}
		// Se non tocca terra applichiamo la gravit�
		else if (!WallCollisionHandler.touchingGround(player, tiles) || WallCollisionHandler.currentlyInsideTiles(player, tiles)) {			
			Tile t = WallCollisionHandler.collideForGravity(player, gravity, tiles);
			//Se t non è nullo allora con la prossima "iterazione" della gravità collidiamo con una tile, reset della posizione in base alla tile stessa
			if(t != null && !WallCollisionHandler.currentlyInsideTiles(player, tiles)) {
				player.y = t.y - Settings.PLAYER_DIMENSION;
				player.hitbox.y = t.y - Settings.PLAYER_DIMENSION;
			}
			else {
				player.fall();				
			}
			player.yState = PlayerSettings.FALLING;
		}
		//se sono in una tile a causa di un salto voglio ignorare le collisioni a dx e sx perche voglio potermi muovere attraverso le tile
		boolean collidingWithRoof = WallCollisionHandler.currentlyInsideTiles(player, tiles);
		//Controllo se esco fuori dai bordi con il prossimo step (ignorando le collisioni nel salto potrebbe succedere
		if(WallCollisionHandler.collidingWithBorder(player, player.xState)) return;
		switch (player.xState) {
		case PlayerSettings.MOVE_LEFT: {		
			Tile t = WallCollisionHandler.collideWithWall(player, player.xState, tiles);
			if (t != null && !collidingWithRoof) {
				player.hitbox.x = t.x + Settings.TILE_WIDHT;  
				player.x = t.x + Settings.TILE_WIDHT; 
			} 
			else {
				player.move();
			}
			break;
		}
		case PlayerSettings.MOVE_RIGHT: {
			Tile t = WallCollisionHandler.collideWithWall(player, player.xState, tiles);			
			if (t != null && !collidingWithRoof) {
				player.hitbox.x = t.x - player.hitbox.width;
				player.x = t.x - player.hitbox.width;
			}
			else {
				player.move();
			}
			break;
		}
		default:
			return;
		}
	}
}
