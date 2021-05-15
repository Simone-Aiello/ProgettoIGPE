package application.model;

import java.util.List;

import application.Settings;

public class Player extends Entity {
	int hp; // health point

	public Player(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		hp = 3;
	}

	// Metodo chiamato dal thread del gameloop, prima di muovere il player in una
	// direzione si calcolano le collisioni con un sistema di hitbox
	@Override
	public void move() {
		List<Tile> tiles = GameModel.getInstance().getTiles();

		// Se non tocca terra applichiamo la gravità
		if (!WallCollisionHandler.touchingGround(this, tiles)) {
			super.y += GameModel.getInstance().getGravity();
			super.getHitbox().y += GameModel.getInstance().getGravity();
		}
		switch (direction) {
		case PlayerSettings.MOVE_LEFT: {
			Tile t = WallCollisionHandler.collideWithWall(this, direction, tiles);
			if (t != null) {
				hitbox.x = t.x + Settings.TILE_WIDHT;
				x = t.x + Settings.TILE_WIDHT;
			} 
			else {
				hitbox.x -= xspeed;
				x -= xspeed;
			}
			break;
		}
		case PlayerSettings.MOVE_RIGHT: {
			Tile t = WallCollisionHandler.collideWithWall(this, direction, tiles);
			if (t != null) {
				hitbox.x = t.x - hitbox.width;
				x = t.x - hitbox.width;
			}
			else {
				hitbox.x += xspeed;
				x += xspeed;
			}
			break;
		}
		case PlayerSettings.IDLE:
			break;
		default:
			throw new IllegalArgumentException("INVALID DIRECTION");
		}
	}
}
