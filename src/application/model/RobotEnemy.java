package application.model;

import java.util.Random;

import application.Settings;

public class RobotEnemy extends Entity implements Enemy {
	private Random r;
	private int frameWithTheSameDirection;
	
	public RobotEnemy(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		r = new Random();
		frameWithTheSameDirection = 5;
		xState = Utilities.MOVE_LEFT;
	}

	@Override
	public void nextMove() {
		if(frameWithTheSameDirection >= 10) {
			int rand = r.nextInt(5);
			if(rand <= 1 && !this.requestedJump) {
				this.requestedJump = true;
			}
			else if(rand == 4) {
				if(this.xState == Utilities.MOVE_LEFT) this.xState = Utilities.MOVE_RIGHT;
				else this.xState = Utilities.MOVE_LEFT;
			}
			frameWithTheSameDirection = -1;
		}
		frameWithTheSameDirection++;
	}

	@Override
	public void reactOnCollisionWithWalls(int direction) {
		if(direction == Utilities.MOVE_LEFT) xState = Utilities.MOVE_RIGHT;
		else xState = Utilities.MOVE_LEFT;
		frameWithTheSameDirection = 0;
	}
}
