package application.model;


import application.Settings;

public class Player extends Entity {
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
		y -= yspeed;
		hitbox.y -= yspeed;
	}
	
	public void fall() {
		y += yspeed;
		hitbox.y += yspeed;		
	}

}
