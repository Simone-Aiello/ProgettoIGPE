package application.model;

public interface Enemy {
	public abstract void nextMove();
	public abstract void reactOnCollisionWithWalls(int direction);
}
