package application.model;

import java.util.Random;

import application.Settings;

public class MageEnemy extends Entity implements Enemy{
	private Random r;
	private int frameWithTheSameDirection;
	private Player playerOne;
	private Player playerTwo;
	private boolean chaseMode;
	int distanceFromPlayerTwo = Settings.WINDOW_WIDTH;
	int distanceFromPlayerOne = Settings.WINDOW_WIDTH;
	public MageEnemy(int x, int y,Player playerOne,Player playerTwo) {
		super(x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		r = new Random();
		frameWithTheSameDirection = 5;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.chaseMode = false;
		this.xState = Utilities.MOVE_LEFT;
	}

	//Se uno dei due player è sullo stesso piano del nemico allora viene inseguito, altrimenti vengono generate direzioni casuali
	@Override
	public void nextMove() {
		distanceFromPlayerTwo = Settings.WINDOW_WIDTH;
		distanceFromPlayerOne = Settings.WINDOW_WIDTH;
		chaseMode = false;
		if(playerTwo != null && y == playerTwo.y) {
			chaseMode = true;
			distanceFromPlayerTwo = Math.abs(this.x - playerTwo.x);
		}
		if(y == playerOne.y) {
			chaseMode = true;
			distanceFromPlayerOne = Math.abs(this.x - playerOne.x);
		}
		if(chaseMode) {
			int pos;
			if(playerTwo != null) {
				pos = distanceFromPlayerOne <= distanceFromPlayerTwo ? playerOne.x : playerTwo.x ;
			}
			else pos = playerOne.x;
			this.xState = this.x <= pos ? Utilities.MOVE_RIGHT : Utilities.MOVE_LEFT;
		}
		else {
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
	}

	@Override
	public void reactOnCollisionWithWalls(int direction) {
		if(direction == Utilities.MOVE_LEFT) xState = Utilities.MOVE_RIGHT;
		else xState = Utilities.MOVE_LEFT;
		frameWithTheSameDirection = 0;
	}
}
