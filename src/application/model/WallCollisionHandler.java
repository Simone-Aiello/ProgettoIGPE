package application.model;

import java.util.List;


public class WallCollisionHandler {
	//Return true se l'entità tocca il terreno, false altrimenti
	public static boolean touchingGround(Entity e,List<Tile> tiles) {
		e.getHitbox().y += GameModel.getInstance().getGravity();
		for (Tile t : tiles) {
			if (e.getHitbox().intersects(t)) {
				e.getHitbox().y -= GameModel.getInstance().getGravity();
				return true;
			}
		}
		e.getHitbox().y -= GameModel.getInstance().getGravity();
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
}
