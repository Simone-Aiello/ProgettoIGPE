package application.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import application.model.GameModel;
import application.model.PlayerSettings;
import application.model.WallCollisionHandler;
import application.view.GameView;
import application.view.PlayerAnimationHandler;

public class GameController implements KeyListener {
	
	private GameView view;
	private boolean spacebarAlreadyPressed;
	
	
	public GameController(GameView view) {
		this.view = view;
		spacebarAlreadyPressed = false;
	}
	public void update() {   
		GameModel.getInstance().update();
		view.update();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			GameModel.getInstance().movePlayer(PlayerSettings.MOVE_LEFT);
			break;
		case KeyEvent.VK_D:
			GameModel.getInstance().movePlayer(PlayerSettings.MOVE_RIGHT);
			break;
		case KeyEvent.VK_SPACE: //salto
			if (!spacebarAlreadyPressed) {
				spacebarAlreadyPressed = true;
				GameModel.getInstance().handlePlayerJump();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			default:
				return;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			if (GameModel.getInstance().getPlayer().getXState() == PlayerSettings.MOVE_LEFT) {
				GameModel.getInstance().movePlayer(PlayerSettings.IDLE_LEFT);
			}
			break;
		case KeyEvent.VK_D:
			if(GameModel.getInstance().getPlayer().getXState() == PlayerSettings.MOVE_RIGHT) {
				GameModel.getInstance().movePlayer(PlayerSettings.IDLE_RIGHT);
			}
			break;
		case KeyEvent.VK_SPACE:
			spacebarAlreadyPressed = false;
			default:
				return;
		}
	}

}
