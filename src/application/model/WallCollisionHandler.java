package application.model;

import java.util.List;


public class WallCollisionHandler {
	//Return true se l'entitÃ  tocca il terreno, false altrimenti
		public static boolean touchingGround(Entity e,List<Tile> tiles) {
			e.hitbox.y += 1;
			for(Tile t : tiles) {
				if(e.hitbox.intersects(t)) {
					e.hitbox.y -= 1;
					return true;
				}
			}
			e.hitbox.y -= 1;
			return false;
		}
	//Muove l'hitbox nella direzione passata, se interseca un muro lo ritorna altrimento riporta l'hitbox al valore iniziale
	public static Tile collideWithWall(Entity e,int direction, List<Tile> tiles) {
		int speed = e.xspeed;
		if(direction == PlayerSettings.MOVE_LEFT) {
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
	public static boolean currentlyCollidingWithRoof(Entity e, List<Tile> roof) {
		if(roof == null) return false;
		for(Tile t : roof) {
			if(e.hitbox.intersects(t)) {
				return true;
			}
		}
		return false;
	}
	public static boolean collidingWithBorder(Entity e, int direction) {
		return false;
	}
}
