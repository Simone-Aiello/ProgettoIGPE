package application.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import application.model.GameModel;
import application.model.PlayerSettings;
import application.view.GameView;
import application.view.PlayerAnimationHandler;

public class GameController implements KeyListener {
	private GameView view;
	public void update() {
		GameModel.getInstance().update();
		view.update();
	}
	public GameController(GameView view) {
		this.view = view;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			GameModel.getInstance().movePlayer(PlayerSettings.MOVE_LEFT);
			view.changeAnimation(PlayerAnimationHandler.WALK_LEFT);
			break;
		case KeyEvent.VK_D:
			GameModel.getInstance().movePlayer(PlayerSettings.MOVE_RIGHT);
			view.changeAnimation(PlayerAnimationHandler.WALK_RIGHT);
			break;
			default:
				return;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			if(GameModel.getInstance().getPlayer().getDirection() == PlayerSettings.MOVE_LEFT) {
				GameModel.getInstance().movePlayer(PlayerSettings.IDLE);
				view.changeAnimation(PlayerAnimationHandler.IDLE_LEFT);
			}
			break;
		case KeyEvent.VK_D:
			if(GameModel.getInstance().getPlayer().getDirection() == PlayerSettings.MOVE_RIGHT) {
				GameModel.getInstance().movePlayer(PlayerSettings.IDLE);
				view.changeAnimation(PlayerAnimationHandler.IDLE_RIGHT);
			}
			break;
			default:
				return;
		}
	}

}
