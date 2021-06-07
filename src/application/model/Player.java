package application.model;


import java.io.Serializable;

import application.Settings;

public class Player extends Entity{
	
	int hp; // health point
	boolean requestedBubble;
	public Player(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		preJumpPos = y; 
		hp = 3;
	}
	public void requestJump() {
		requestedJump = true;
	}
	public void requestBubble() {
		requestedBubble = true;
	}

}
