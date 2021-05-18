package application.model;


import java.util.ArrayList;
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
		player.yspeed = gravity;
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
		updatePlayer(); 
	}
	
	public void movePlayer(int direction) {
		player.xState = direction;
	}
	
	// Metodo chiamato dal thread del gameloop, prima di muovere il player in una
	// direzione si calcolano le collisioni con un sistema di hitbox
	private void updatePlayer() {
		player.yState = PlayerSettings.Y_IDLE;
		if (player.jumping) { // vanno aggiunti i limiti all' altezza del salto  
			if (player.y + player.yspeed > player.preJumpPos - 3 * Settings.PLAYER_DIMENSION ) {
				player.jump();
				if(player.xState == PlayerSettings.MOVE_RIGHT || player.xspeed == PlayerSettings.IDLE_RIGHT) player.yState = PlayerSettings.JUMPING;
				else player.yState = PlayerSettings.JUMPING;
			}
			else
				player.jumping = false;
		}
		// Se non tocca terra applichiamo la gravit�
		else if (!WallCollisionHandler.touchingGround(player, tiles)) {			
			Tile t = WallCollisionHandler.collideForGravity(player, gravity, tiles);
			//Se t non è nullo allora con la prossima "iterazione" della gravità collidiamo con una tile, reset della posizione in base alla tile stessa
			if(t != null ) {
				player.y = t.y - Settings.PLAYER_DIMENSION;
				player.hitbox.y = t.y - Settings.PLAYER_DIMENSION;
			}
			else {
				player.fall();				
			}
			if(player.xState == PlayerSettings.MOVE_RIGHT || player.xspeed == PlayerSettings.IDLE_RIGHT) player.yState = PlayerSettings.FALLING;
			else player.yState = PlayerSettings.FALLING;
		}
		//se sono in una tile a causa di un salto voglio ignorare le collisioni a dx e sx perche voglio potermi muovere attraverso i tile
		boolean collidingWithRoof = WallCollisionHandler.currentlyCollidingWithRoof(player, tiles);

		switch (player.xState) {
		case PlayerSettings.MOVE_LEFT: {		
			Tile t = WallCollisionHandler.collideWithWall(player, player.xState, tiles);
			if ((t != null && !collidingWithRoof)|| WallCollisionHandler.collidingWithBorder(player, player.xState)) {
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
			if ((t != null && !collidingWithRoof)|| WallCollisionHandler.collidingWithBorder(player, player.xState)) {
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

	public void handlePlayerJump(boolean isJumping) {
		if (isJumping && !player.jumping) {
			if (WallCollisionHandler.touchingGround(player, tiles)) {
				player.jumping = isJumping;
				player.preJumpPos = player.y;
			}  
			return;
		}
		player.jumping = isJumping;
	}
	
	public Player getPlayer() {
		return player;
	}
}
