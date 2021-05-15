package application.model;

import java.awt.Rectangle;

public abstract class Entity {
	int x;
	int y;
	int xspeed;
	int yspeed;
	int direction;
	Rectangle hitbox;
	public Entity(int x, int y, int hitboxWidht,int hitboxHeight) {
		xspeed = 10;
		yspeed = 0;
		hitbox = new Rectangle(x, y, hitboxWidht, hitboxHeight);
		this.x = x;
		this.y = y;
		direction = PlayerSettings.IDLE;
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
	public int getDirection() {
		return direction;
	}
	public abstract void move();
	public void update() {
		move();
	}
}
