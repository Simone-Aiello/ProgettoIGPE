package application.model;

public class Player {
	int hp; //health point
	int x;
	int y;
	int xspeed;
	int yspeed;;
	//
	public int direction;
	//
	public Player(int x, int y) {
		hp = 3;
		xspeed = 10;
		yspeed = 10;
		this.x = x;
		this.y = y;
		direction = 2;
	}
	public void move() {
		if(direction == PlayerSettings.MOVE_LEFT) x -= xspeed;
		else if(direction == PlayerSettings.MOVE_RIGHT) x += xspeed;
	}
	public void update() {
		move();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}
