package multiplayerserver;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RoomHandler {
	private static Random random = new Random();
	private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static int codeLenght = 5;
	public static ExecutorService excutor = Executors.newCachedThreadPool();
	public static HashMap<String, MultiplayerGameHandler> rooms = new HashMap<String, MultiplayerGameHandler>();
	public static String generateCode() {
		StringBuilder builder = new StringBuilder(codeLenght);
		for(int i = 0; i < codeLenght;i++) {
			builder.append(letters.charAt(random.nextInt(letters.length())));
		}
		return builder.toString();
	}
}
