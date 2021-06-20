package application.model;

import application.Settings;

public class SpringEnemy extends Entity implements Enemy {

	public SpringEnemy(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		xState = Utilities.MOVE_LEFT;
	}

	@Override
	public void nextMove() {
		this.requestedJump = true;
	}

	@Override
	public void reactOnCollisionWithWalls(int direction) {
		if(direction == Utilities.MOVE_LEFT) xState = Utilities.MOVE_RIGHT;
		else xState = Utilities.MOVE_LEFT;
	}

}
