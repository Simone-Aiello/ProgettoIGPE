package application;

import application.controller.GameController;

public class GameLoop implements Runnable {
	private GameController controller;
	private long frequency = 60;

	public GameLoop(GameController controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		System.out.println("TEST");
		while (true) {
			controller.update();
			try {
				Thread.sleep(frequency);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
