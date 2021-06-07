package application.model;

import application.Settings;

public class Bubble extends Entity{
	boolean affectedByGravity;
	int aliveFrame;
	Enemy enemyContained;
	int frameEnemyContained;
	public Bubble(int x, int y,int direction) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		affectedByGravity = false;
		aliveFrame = 0;
		frameEnemyContained = 0;
		enemyContained = null;
		this.yspeed *= -1;
		this.xState = direction;
		this.yState = Utilities.Y_IDLE;
		this.xspeed = 20;
	}
	public int getAliveFrame() {
		return aliveFrame;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public Enemy getEnemyContained() {
		return enemyContained;
	}
}
