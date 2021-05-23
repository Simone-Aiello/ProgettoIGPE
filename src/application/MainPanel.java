package application;

import javax.swing.JFrame;

import application.controller.GameController;
import application.view.GameView;

public class MainPanel {
	public static void main(String[] args) {
		//Se si sceglie la modalità single player viene fatto partire un server in localhost
		SinglePlayerServer server = new SinglePlayerServer();
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		JFrame f = new JFrame();
		GameView view = new GameView();
		GameController controller = new GameController(view,"localhost",8000);
		view.setSocket(controller.getSocket());
		view.setFocusable(true);
		view.addKeyListener(controller);
		f.setSize(Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT);
		f.setLocationRelativeTo(null);
		f.add(view);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameLoop loop = new GameLoop(controller);
		Thread t = new Thread(loop);
		t.start();
	}
}
