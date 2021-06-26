package application;

public class Settings {
	public static final int TOPBAR_HEIGHT = 40;
	public static final int WINDOW_HEIGHT = 500 + TOPBAR_HEIGHT;
	public static final int WINDOW_WIDTH = 800;
	public static final int TILE_WIDHT = 40;
	public static final int TILE_HEIGHT = 20;
	
	public static final int PLAYER_DIMENSION= 40;
	public static final int FOOD_DIMENSION = 30;
	
	//public static final int INITIAL_POSITION_X = 300;
	public static final int PLAYER_ONE_INITIAL_X = TILE_WIDHT;
	public static final int PLAYER_TWO_INITIAL_X = WINDOW_WIDTH - TILE_WIDHT - PLAYER_DIMENSION;
	//public static final int INITIAL_POSITION_Y = 100;
	public static final int PLAYER_ONE_INITIAL_Y = WINDOW_HEIGHT - TILE_HEIGHT - PLAYER_DIMENSION - 80;
	public static final int PLAYER_TWO_INITIAL_Y = WINDOW_HEIGHT - TILE_HEIGHT - PLAYER_DIMENSION - 80;
}
