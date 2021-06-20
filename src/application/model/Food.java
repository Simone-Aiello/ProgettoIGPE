package application.model;

import java.util.Random;

import application.Settings;

public class Food extends Entity{
	private int type;
	private int points;
	private static Random r = new Random();
	public Food(int x,int y,int type) {
		super(x, y, Settings.FOOD_DIMENSION, Settings.FOOD_DIMENSION);
		this.type = type;
		switch(this.type) {
		case Utilities.CAKE:
			points = 100;
			break;
		case Utilities.EGG:
			points = 150;
			break;
		case Utilities.HAMBURGER:
			points = 200;
	}
	}
	public Food(int x,int y) {
		super(x, y, Settings.FOOD_DIMENSION, Settings.FOOD_DIMENSION);
		this.type = r.nextInt(3);
		switch(this.type) {
			case Utilities.CAKE:
				points = 100;
				break;
			case Utilities.EGG:
				points = 150;
				break;
			case Utilities.HAMBURGER:
				points = 200;
		}
	}
	public int getPoints() {
		return points;
	}
	public int getType() {
		return type;
	}
}
