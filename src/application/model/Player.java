package application.model;

import java.util.List;

import application.Settings;

public class Player extends Entity {
	int hp; // health point

	public Player(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		hp = 3;
	}

	//metodo per il movimento con incremento di default(xspeed) a destra e sinistra
	@Override
	public void move() {  
		switch(direction) {
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
		hitbox.y -= yspeed;
		y -= yspeed;
	}
	
	public void fall() {
		y += yspeed;
		hitbox.y += yspeed;		
	}

}
