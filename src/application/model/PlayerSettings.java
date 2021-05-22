package application.model;

public class PlayerSettings {
	//Stati sull'asse x
	public static final int MOVE_RIGHT = 0;
	public static final int MOVE_LEFT = 1;
	public static final int IDLE_RIGHT = 2;
	public static final int IDLE_LEFT = 3;
	//Stati sull'asse y
	public static final int Y_IDLE = 4;
	public static final int FALLING = 5;
	public static final int JUMPING = 6;
	//Campi di support per animazioni
	public static final int FALL_LEFT = 7;
	public static final int FALL_RIGHT = 8;
	public static final int JUMP_LEFT = 9;
	public static final int JUMP_RIGHT = 10;
}
