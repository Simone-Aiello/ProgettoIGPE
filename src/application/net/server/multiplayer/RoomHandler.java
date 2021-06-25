package application.net.server.multiplayer;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.model.Utilities;

public class RoomHandler {
	private static Random random = new Random();
	private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static int codeLenght = 5;
	public static ExecutorService excutor = Executors.newCachedThreadPool();
	public static HashMap<String, MultiplayerGameHandler> rooms = new HashMap<String, MultiplayerGameHandler>();

	public static String generateCode() {
		StringBuilder builder = new StringBuilder(codeLenght);
		for (int i = 0; i < codeLenght; i++) {
			builder.append(letters.charAt(random.nextInt(letters.length())));
		}
		return builder.toString();
	}

	public static synchronized String joinRequest(String code,Socket socket) throws IOException{
		if (!RoomHandler.rooms.containsKey(code)) {
			return Utilities.NOT_EXISTS_ROOM;
		} 
		else if (RoomHandler.rooms.get(code).isStarted()) {
			return Utilities.ROOM_FULL_ERROR;
		} 
		else {
			rooms.get(code).addPlayerTwo(socket);
			excutor.submit(RoomHandler.rooms.get(code));
			return Utilities.OK_JOIN;
		}
	}
}
