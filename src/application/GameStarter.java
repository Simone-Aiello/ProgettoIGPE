package application;


import application.controller.GameController;
import application.model.Utilities;
import application.net.Client;
import application.view.GameView;
import application.view.TopLayerGameView;
import menu.view.GenericMessagePanel;

public class GameStarter {
	private static GameView view = null;
	private static GameController controller = null;
	private static Client client = null;
	private static GameLoop loop = null;
	private static Thread t = null;
	private static TopLayerGameView topView = null;
	public static void startGame(boolean singlePlayer,Client c) {
		topView = new TopLayerGameView();
		view = new GameView(topView.getScoreLabel());
		controller = new GameController(view,topView,singlePlayer);
		view.setController(controller);
		view.addKeyListener(controller);
		controller.setClient(c);
		loop = new GameLoop(controller);
		t = new Thread(loop);
		t.start();
	}

	public static void hostRequest() {
		client = new Client(Utilities.HOST,null);
		if(client.isStartedCorrectly()) {
			String text = "Your room code is: " + client.getRoomCode()+"\n\n"
					+ "Give it to your friend and start playing!\n\n"
					+ "Do not return to main menu, your room will be cancelled";
			ChangeSceneHandler.showMessage(text);
			startGame(false, client);
		}
		else {
			String text = "Error while hosting the room\n\n"
					+ "- Check your internet connection\n\n"
					+ "- Try again in a few minutes";
			ChangeSceneHandler.showMessage(text);
		}
	}
	public static void joinRequest(String code) {
		if(code.equals("")) return;
		client = new Client(Utilities.JOIN,code);
		if(client.isStartedCorrectly()) {
			startGame(false, client);
		}
		else if(client.getError() != null && client.getError().equals(Utilities.NOT_EXIXTS_ROOM)) {
			String text = "There is no room with code: " + client.getRoomCode()+"\n\n"
					+ "- Check the spelling of the code\n\n"
					+ "- Make sure your friend hasn't returned to the main menu";
			ChangeSceneHandler.showMessage(text);
		}
		else if(client.getError() != null && client.getError().equals(Utilities.ROOM_FULL_ERROR)) {
			String text = "The room with code: " + client.getRoomCode()+" is full\n\n"
					+ "- Check the spelling of the code or host a new room";
			ChangeSceneHandler.showMessage(text);
		}
		else {
			String text = "Connection error\n\n"
					+ "- Check your internet connection\n\n"
					+ "- Try again in a few minutes";
			ChangeSceneHandler.showMessage(text);
		}
	}
	public static void resetAll() {
		if(client != null) {
			client.sendMessage(Utilities.DISCONNECTED);
		}
		view = null;
		if(controller != null) {
			ChangeSceneHandler.removeTopBar(topView);
		}
		topView = null;
		controller = null;
		client = null;
		loop = null;
		if(t != null) t.interrupt();
		t = null;
	}
	public static void pauseGame(boolean pause) {
		if(controller != null) {
			controller.pauseGame(pause);
			if(!pause) ChangeSceneHandler.setCurrentScene("game");
		}
	}
}
