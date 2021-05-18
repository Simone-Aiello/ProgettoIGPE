package application.model;

import java.awt.Rectangle;

public abstract class Entity {
	int x;
	int y;
	int xspeed;
	int yspeed;
	int xState;
	int yState;
	boolean jumping;

	Rectangle hitbox;
	public Entity(int x, int y, int hitboxWidht,int hitboxHeight) {
		xspeed = 10;
		yspeed = 16;
		hitbox = new Rectangle(x, y, hitboxWidht, hitboxHeight);
		this.x = x;
		this.y = y;
		xState = PlayerSettings.IDLE_RIGHT;
		yState = PlayerSettings.IDLE_RIGHT;
		jumping = false;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXState() {
		return xState;
	}
	
	public int getYState() {
		return yState;
	}
	
	public boolean isJumping() {
		return jumping;
	}
	
	public abstract void move();
	public abstract void jump();
	public abstract void fall();
	
	/*public void update() {
		move();
	}*/
}
