package application;

import javax.swing.JFrame;

import application.controller.GameController;
import application.view.GameView;

public class MainPanel {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		GameView view = new GameView();
		view.setFocusable(true);
		GameController controller = new GameController(view);
		view.addKeyListener(controller);
		f.setSize(Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT);
		f.add(view);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameLoop loop = new GameLoop(controller);
		Thread t = new Thread(loop);
		t.start();
	}
}
