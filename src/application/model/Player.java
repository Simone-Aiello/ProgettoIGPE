package application.model;


import java.io.Serializable;

import application.Settings;

public class Player extends Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6854671907296980605L;
	int hp; // health point
	int preJumpPos;

	public Player(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		preJumpPos = y; 
		hp = 3;
	}

	//metodo per il movimento con incremento di default(xspeed) a destra e sinistra
	@Override
	public void move() {  
		switch(xState) {
		case PlayerSettings.MOVE_LEFT:
			hitbox.x -= xspeed;
			x -= xspeed;
			break;
		case PlayerSettings.MOVE_RIGHT:
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

}
