package application.model;

import java.util.List;

import application.Settings;


public class WallCollisionHandler {
	//Return true se l'entitÃ  tocca il terreno, false altrimenti
		public static boolean touchingGround(Entity e,List<Tile> tiles) {
			int step = e.yspeed < 0 ? -1 : 1;
			e.hitbox.y += step;
			for(Tile t : tiles) {
				if(e.hitbox.intersects(t)) {
					e.hitbox.y -= step;
					return true;
				}
			}
			e.hitbox.y -= step;
			return false;
		}
	//Muove l'hitbox nella direzione passata, se interseca un muro lo ritorna altrimento riporta l'hitbox al valore iniziale
	public static Tile collideWithWall(Entity e,int direction, List<Tile> tiles) {
		int speed = e.xspeed;
		if(direction == Utilities.MOVE_LEFT) {
			speed *= -1;
		}
		e.hitbox.x += speed;
		for(Tile t : tiles) {
			if(e.getHitbox().intersects(t)) {
				e.hitbox.x -=speed;
				return t;
			}
		}
		e.hitbox.x -=speed;
		return null;
	}
	public static Tile collideForGravity(Entity e,int gravity, List<Tile> tiles) {
		e.hitbox.y += gravity;
		for(Tile t : tiles) {
			if(e.hitbox.intersects(t)) {
				e.hitbox.y -= gravity;
				return t;
			}
		}
		e.hitbox.y -= gravity;
		return null;
	}
	public static boolean currentlyInsideTiles(Entity e, List<Tile> roof) {
		if(roof == null) return false;
		for(Tile t : roof) {
			if(e.hitbox.intersects(t)) {
				return true;
			}
		}
		return false;
	}
	public static boolean collidingWithBorder(Entity e, int direction) {
		switch(direction) {
		case Utilities.MOVE_RIGHT:
			return e.x+e.xspeed + Settings.PLAYER_DIMENSION > Settings.WINDOW_WIDTH - Settings.TILE_WIDHT;
		case Utilities.MOVE_LEFT:
			return e.x - e.xspeed  < Settings.TILE_WIDHT;
		case Utilities.JUMPING:
			break;
		}
		return false;
	}
}
