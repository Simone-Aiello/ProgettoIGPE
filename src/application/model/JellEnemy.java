package application.model;

import application.Settings;

public class JellEnemy extends Entity implements Enemy{
	public JellEnemy(int x, int y) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		xState = Utilities.MOVE_RIGHT;
		yspeed = xspeed/2;
	}

	@Override
	public void nextMove() {
		if(y + Settings.PLAYER_DIMENSION >= (Settings.WINDOW_HEIGHT - Settings.TOPBAR_HEIGHT - Settings.TILE_HEIGHT) || y <= Settings.TILE_HEIGHT) {
			yspeed *= -1;
		}
		else if(x + Settings.PLAYER_DIMENSION >= Settings.WINDOW_WIDTH - Settings.TILE_WIDHT || x <= Settings.TILE_WIDHT) {
			xState = xState == Utilities.MOVE_LEFT ? Utilities.MOVE_RIGHT : Utilities.MOVE_LEFT;
		}
		y += yspeed;
		hitbox.y += yspeed;
	}

	@Override
	public void reactOnCollisionWithWalls(int direction) {}

}
