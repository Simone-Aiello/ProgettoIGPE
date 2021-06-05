package application.model;


import java.io.Serializable;

import application.Settings;

public class Player extends Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6854671907296980605L;
	
	int hp; // health point

	public Player(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		preJumpPos = y; 
		hp = 3;
	}
	public void requestJump() {
		requestedJump = true;
	}

}
