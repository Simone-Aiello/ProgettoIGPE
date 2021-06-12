package application.model;

import application.Settings;

public class Food extends Entity{
	private int type;
	private int points;
	public Food(int x,int y,int type) {
		super(x, y, Settings.FOOD_DIMENSION, Settings.FOOD_DIMENSION);
		this.type = type;
		switch(this.type) {
			case Utilities.CAKE:
				points = 100;
				break;
		}
	}
	public int getPoints() {
		return points;
	}
	public int getType() {
		return type;
	}
}
