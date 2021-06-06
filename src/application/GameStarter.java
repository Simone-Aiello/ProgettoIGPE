package application;


import application.controller.GameController;
import application.model.Utilities;
import application.net.client.Client;
import application.view.GameView;
import menu.view.GenericMessagePanel;

public class GameStarter {
	private static GameView view = null;
	private static GameController controller = null;
	private static Client client = null;
	private static GameLoop loop = null;
	private static Thread t = null;
	public static void startGame(boolean singlePlayer,Client c) {
		view = new GameView();
		controller = new GameController(view, singlePlayer);
		view.setController(controller);
		view.addKeyListener(controller);
		controller.setClient(c);
		loop = new GameLoop(controller);
		t = new Thread(loop);
		t.start();
	}

	public static void hostRequest() {
		client = new Client(Utilities.HOST,null);
		GenericMessagePanel panel = (GenericMessagePanel) ChangeSceneHandler.scenes.get("messagePanel");
		if(client.isStartedCorrectly()) {
			panel.setText("Your room code is: " + client.getRoomCode()+"\n\n"
					+ "Give it to your friend and start playing!\n\n"
					+ "Do not return to main menu, your room will be cancelled");
			ChangeSceneHandler.setCurrentScene("messagePanel");
			startGame(false, client);			
		}
		else {
			panel.setText("Error while hosting the room\n\n"
					+ "- Check your internet connection\n\n"
					+ "- Try again in a few minutes");
			ChangeSceneHandler.setCurrentScene("messagePanel");
		}
	}
	public static void joinRequest(String code) {
		if(code.equals("")) return;
		client = new Client(Utilities.JOIN,code);
		if(client.isStartedCorrectly()) {
			startGame(false, client);			
		}
		else {
			GenericMessagePanel panel = (GenericMessagePanel) ChangeSceneHandler.scenes.get("messagePanel");
			panel.setText("Something went wrong:\n\n"
					+ "- Check your internet connection\n\n "
					+ "- Check the spelling of the code \n\n"
					+ "- Make sure that your friend has not returned to the main menu");
			ChangeSceneHandler.setCurrentScene("messagePanel");
		}
	}
	public static void resetAll() {
		if(client != null) {
			client.sendMessage(Utilities.DISCONNECTED);
		}
		view = null;
		controller = null;
		client = null;
		loop = null;
		if(t != null) t.interrupt();
		t = null;
	}
}
