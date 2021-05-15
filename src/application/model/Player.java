package application.model;

import java.awt.Rectangle;
import java.util.List;

import application.Settings;

public class Player {
	int hp; // health point
	int x;
	int y;
	int xspeed;
	int yspeed;;
	int direction;
	Rectangle hitbox;

	public Player(int x, int y) {
		hitbox = new Rectangle(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		hp = 3;
		xspeed = 10;
		yspeed = 10;
		this.x = x;
		this.y = y;
		direction = PlayerSettings.IDLE;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	private boolean touchingGround(List<Tile> tiles) {
		hitbox.y += GameModel.getInstance().getGravity();
		for (Tile t : tiles) {
			if (hitbox.intersects(t)) {
				hitbox.y -= GameModel.getInstance().getGravity();
				return true;
			}
		}
		return false;
	}
	//Prima di muovere il player in una direzione si calcolano le collisioni con un sistema di hitbox
	public void move() {
		List<Tile> tiles = GameModel.getInstance().getTiles();
		//Se tocca terra non applichiamo la gravità
		if(!touchingGround(tiles)) {
			y += GameModel.getInstance().getGravity();
		}
		if (direction == PlayerSettings.MOVE_LEFT) {
			hitbox.x -= xspeed;
			for (Tile t : tiles) {
				if (hitbox.intersects(t)) {
					hitbox.x = t.x + hitbox.width;
					x = t.x + hitbox.width;
					return;
				}
			}
			x -= xspeed;
		} else if (direction == PlayerSettings.MOVE_RIGHT) {
			hitbox.x += xspeed;
			for (Tile t : tiles) {
				if (hitbox.intersects(t)) {
					hitbox.x = t.x - hitbox.width;
					x = t.x - hitbox.width;
					return;
				}
			}
			x += xspeed;
		}
	}

	public void update() {
		move();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDirection() {
		return direction;
	}
}
