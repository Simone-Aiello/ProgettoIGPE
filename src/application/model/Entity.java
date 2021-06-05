package application.model;

import java.awt.Rectangle;
import java.io.Serializable;

public abstract class Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7975286625857830824L;
	int x;
	int y;
	int xspeed;
	int yspeed;
	int xState;
	int yState;
	int preJumpPos;
	boolean jumping;
	boolean requestedJump;
	Rectangle hitbox;
	public Entity(int x, int y, int hitboxWidht,int hitboxHeight) {
		xspeed = 10;
		yspeed = 12;
		hitbox = new Rectangle(x, y, hitboxWidht, hitboxHeight);
		this.x = x;
		this.y = y;
		xState = Utilities.IDLE_RIGHT;
		yState = Utilities.Y_IDLE;
		jumping = false;
		requestedJump = false;
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
	//metodo per il movimento con incremento di default(xspeed) a destra e sinistr
	public void move() {  
		switch(xState) {
		case Utilities.MOVE_LEFT:
			hitbox.x -= xspeed;
			x -= xspeed;
			break;
		case Utilities.MOVE_RIGHT:
			hitbox.x += xspeed;
			x += xspeed;
			break;
		}
	}
	public void jump() {
		y -= yspeed;
		hitbox.y -= yspeed;
	}
	public void fall() {
		y += yspeed;
		hitbox.y += yspeed;
	}
	public void setxState(int xState) {
		this.xState = xState;
	}
	public void setyState(int yState) {
		this.yState = yState;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
}
