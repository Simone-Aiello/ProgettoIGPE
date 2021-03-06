package application.model;

public class Utilities {
	// Finali gioco
	public static final int PLAYING = 0;
	public static final int WIN = 1;
	public static final int LOSE = 2;
	// Stati sull'asse x
	public static final int MOVE_RIGHT = 0;
	public static final int MOVE_LEFT = 1;
	public static final int IDLE_RIGHT = 2;
	public static final int IDLE_LEFT = 3;
	public static final int SHOOT = 11;
	public static final int SHOOT_RIGHT = 16;
	public static final int SHOOT_LEFT = 15;
	// Stati sull'asse y
	public static final int Y_IDLE = 4;
	public static final int FALLING = 5;
	public static final int JUMPING = 6;
	// public static final int BUBBLED = 13;
	// Campi di supporto per animazioni
	public static final int FALL_LEFT = 7;
	public static final int FALL_RIGHT = 8;
	public static final int JUMP_LEFT = 9;
	public static final int JUMP_RIGHT = 10;
	// Parametri bolle
	public static final int BUBBLE_LIFESPAN = 130;
	public static final int BUBBLE_TURN = 17;
	public static final int BUBBLED_ROBOT = 14;
	public static final Integer BUBBLED_MAGE = 19;
	public static final Integer BUBBLED_SPRING = 20;
	public static final Integer BUBBLED_JELL = 21;
	public static final int BUBBLED_ENEMY_MAX_FRAME = 70;
	// Food
	public static final int CAKE = 0;
	public static final int EGG = 1;
	public static final int HAMBURGER = 2;
	// Protocollo client-server
	public static final String POSITION = "pos";
	public static final String MOVE = "m";
	public static final String ENEMY = "e";
	public static final String PLAYER = "p";
	public static final String BUBBLE = "b";
	public static final String FOOD = "f";
	public static final String SCORE = "s";
	public static final String MESSAGE_SEPARATOR = ";";
	public static final String OK_JOIN = "OK";
	public static final String JOIN_ERROR = "NO";
	public static final String GAME_START = "START";
	public static final String DISCONNECTED = "DISCONNECTED";
	public static final String ROOM_FULL_ERROR = "FULL";
	public static final String NOT_EXISTS_ROOM = "NOTEXTISTS";
	public static final String ALIVE = "a";
	public static final String DEAD = "d";

	// I messaggi dal client al server saranno del tipo m xcord ycord
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

	// I messaggi dal server al client saranno p entity xcord ycord xstate ystate
	// alive/dead
	// Nel caso del cibo xState viene sostituito con il tipo
	public static String position(String who, int index, int x, int y, int xState, int yState, boolean alive) {
		if (alive)
			return POSITION + " " + who + " " + index + " " + x + " " + y + " " + xState + " " + yState + " " + ALIVE
					+ " ";
		return POSITION + " " + who + " " + index + " " + x + " " + y + " " + xState + " " + yState + " " + DEAD + " ";
	}

	public static String captured(int bubbleIndex, int enemyIndex) {
		return BUBBLE + " " + bubbleIndex + " " + enemyIndex;
	}

	public static String score(int score) {
		return SCORE + " " + score;
	}

	public static final String HOST = "h";
	public static final String JOIN = "j";
	public static final String CHANGE_LEVEL = "cl";

	public static String joinRequest(String code) {
		return JOIN + " " + code;
	}

	
	public static String loginRequest(String username, String password) {
		return LOGIN + MESSAGE_SEPARATOR + username + MESSAGE_SEPARATOR + password;
	}
	
	public static String signUpRequest(String username, String password) {
		return SIGNUP + MESSAGE_SEPARATOR + username + MESSAGE_SEPARATOR + password;
	}
	
	public static String updateGamesRequest(String username, int score) {
		return UPDATE_SCORE + MESSAGE_SEPARATOR + username + MESSAGE_SEPARATOR + score;
	}
	
	//campi per il portocollo di connessione al db
		public static final String DB_ACCESS = "DBaccess";
		public static final String LOGIN = "login";
		public static final String SIGNUP = "signup";
		public static final String LEADERBOARDS = "leaderboards";
		public static final String UPDATE_SCORE = "update_score";
		
		//errori realtivi alla registrazione e al login	
		public static final String SERVER_NOT_REACHABLE = "NO CONNECTION TO SERVER";
		public static final String GO_ON = "GO_ON";
		public static final String ERROR = "GENERIC ERROR";
		public static final String ILLEGAL_REQUEST = "ILLEGAL REQUEST";
		public static final String SUCCESS_ACCESS = "OK_ACCESS";	
		public static final String USER_ALREADY_EXIST = "USER_EXIST";
		public static final String USER_NOT_EXIST = "USER_NOT_EXIST";
		public static final String WRONG_PASSWORD = "WRONG_PASSWORD";
		public static final String ERROR_CONNECTING_DB = "DB_ERROR";
		public static final String PROGRESS_SAVED = "PROGRESS_SAVED";
		public static final String STILL_NO_CLASSIFICATION = "NO_CLASSIFICATION";
		public static final String OK_CLASSIFICATION = "OK_CLASSIFICATION";
		public static final String START_CLASSIFICATION = "CLASS_START";
		public static final String END_CLASSIFICATION = "CLASS_END";

}
