package application.model;

public class Utilities {
	//Stati sull'asse x
	public static final int MOVE_RIGHT = 0;
	public static final int MOVE_LEFT = 1;
	public static final int IDLE_RIGHT = 2;
	public static final int IDLE_LEFT = 3;
	public static final int SHOOT = 11;
	//Stati sull'asse y
	public static final int Y_IDLE = 4;
	public static final int FALLING = 5;
	public static final int JUMPING = 6;
	public static final int BUBBLED = 13;
	//Campi di supporto per animazioni
	public static final int FALL_LEFT = 7;
	public static final int FALL_RIGHT = 8;
	public static final int JUMP_LEFT = 9;
	public static final int JUMP_RIGHT = 10;
	//Parametri bolle in frame
	public static final int BUBBLE_LIFESPAN = 130;
	public static final int BUBBLE_TURN = 17;
	//Protocollo client-server
	public static final String POSITION = "pos";
	public static final String MOVE = "m";
	public static final String ENEMY = "e";
	public static final String PLAYER = "p";
	public static final String MESSAGE_SEPARATOR = ";";
	public static final String OK_JOIN = "OK";
	public static final String JOIN_ERROR = "NO";
	public static final String GAME_START = "START";
	public static final String DISCONNECTED = "DISCONNECTED";
	public static final String ROOM_FULL_ERROR = "FULL";
	public static final String NOT_EXIXTS_ROOM = "NOTEXTISTS";
	//I messaggi dal client al server saranno del tipo m xcord ycord
	public static String moveLeft() {
		return MOVE + " " + MOVE_LEFT; 
	}
	public static String moveRight() {
		return MOVE + " " + MOVE_RIGHT; 
	}
	public static String requestIdleLeft() {
		return MOVE + " " + IDLE_LEFT; 
	}
	public static String requestIdleRight() {
		return MOVE + " " + IDLE_RIGHT; 
	}
	public static String requestJump() {
		return MOVE + " " + JUMPING; 
	}
	//I messaggi dal server al client saranno p entity xcord ycord xstate ystate alive/dead
	public static String position(String who,int index,int x,int y,int xState,int yState) {
		return POSITION + " " + who + " " + index + " " + x + " " + y + " " + xState + " " + yState; 
	}
	public static final String HOST = "h";
	public static final String JOIN = "j";
	public static final String joinRequest(String code) {
		return JOIN + " " + code;
	}
	
}
