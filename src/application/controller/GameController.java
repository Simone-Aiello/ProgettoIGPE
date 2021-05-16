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
		case KeyEvent.VK_SPACE: //salto
			GameModel.getInstance().handlePlayerJump(true);
			if(GameModel.getInstance().getPlayer().getDirection() == PlayerSettings.MOVE_LEFT)
			    view.changeAnimation(PlayerAnimationHandler.JUMP_LEFT);
			else
				view.changeAnimation(PlayerAnimationHandler.JUMP_RIGHT);			
			default:
				return;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			if (GameModel.getInstance().getPlayer().getDirection() == PlayerSettings.MOVE_LEFT) {
				GameModel.getInstance().movePlayer(PlayerSettings.IDLE);

				/*se sta toccando terra allora l' animazione è quella di idle altrimenti prende
				 l' animazione di falling
				if (WallCollisionHandler.touchingGround(GameModel.getInstance().getPlayer(),
						GameModel.getInstance().getTiles()))
					view.changeAnimation(PlayerAnimationHandler.IDLE_LEFT);
				else
					view.changeAnimation(PlayerAnimationHandler.FALL_LEFT); */
			}
			break;
		case KeyEvent.VK_D:
			if(GameModel.getInstance().getPlayer().getDirection() == PlayerSettings.MOVE_RIGHT) {
				GameModel.getInstance().movePlayer(PlayerSettings.IDLE);
				
			}
			break;
		case KeyEvent.VK_SPACE:
			GameModel.getInstance().handlePlayerJump(false);
			if (WallCollisionHandler.touchingGround(GameModel.getInstance().getPlayer(),
					GameModel.getInstance().getTiles()))
				view.changeAnimation(PlayerAnimationHandler.IDLE_RIGHT);
			else
				view.changeAnimation(PlayerAnimationHandler.FALL_RIGHT);
			//gestidci l' animazione per la caduta
			default:
				return;
		}
	}

}
