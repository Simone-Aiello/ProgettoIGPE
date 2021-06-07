package application.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Duration;
import java.time.Instant;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.model.GameModel;
import application.model.Player;
import application.model.Utilities;
import application.net.Client;
import application.view.GameView;

public class GameController implements KeyListener {

	private GameView view;
	private GameModel model;
	private boolean spacebarAlreadyPressed;
	private boolean shootAlreadyPressed;
	private boolean isSinglePlayer;
	private Client client;
	
	private Instant lastUpdate = null;
	public GameController(GameView view, boolean isSinglePlayer) {
		this.view = view;
		spacebarAlreadyPressed = false;
		shootAlreadyPressed = false;
		this.isSinglePlayer = isSinglePlayer;
		model = new GameModel();
		if (isSinglePlayer) {
			ChangeSceneHandler.add("game", view);
			ChangeSceneHandler.setCurrentScene("game");
			model.startGame(isSinglePlayer);
		}
		else {
			view.loadPlayerTwo();
		}
	}
	public void setClient(Client c) {
		this.client = c;
	}
	public boolean isSinglePlayer() {
		return isSinglePlayer;
	}

	public void update() {
		if (!isSinglePlayer) {
			String[] update = client.read();
			//è null fino a quando non entra il secondo giocatore, perché prima il server non manda nessun messaggio
			if (update != null) {
				if (!model.isStarted()) {
					model.startGame(isSinglePlayer);
					ChangeSceneHandler.add("game", view);
					ChangeSceneHandler.setCurrentScene("game");
				}
				for (String s : update) {
					String[] message = s.split(" ");
					if (message[1].equals(Utilities.PLAYER)) {
						Player player;
						if (message[2].equals("1")) {
							player = model.getPlayerOne();
						}  
						else { //Non ho controlli sul messaggio
							player = model.getPlayerTwo();
						}
						player.setX(Integer.parseInt(message[3]));
						player.setY(Integer.parseInt(message[4]));
						player.getHitbox().x = Integer.parseInt(message[3]);
						player.getHitbox().y = Integer.parseInt(message[4]);
						player.setxState(Integer.parseInt(message[5]));
						player.setyState(Integer.parseInt(message[6]));
						
					} else if (message[1].equals(Utilities.ENEMY)) {
						model.setEnemyPosition(message);
					}
				}
				view.update();
				lastUpdate = Instant.now();
			}
			else {
				if(lastUpdate != null && Duration.between(lastUpdate, Instant.now()).toSecondsPart() > 3) {
					ChangeSceneHandler.showMessage("Connection lost");
					GameStarter.resetAll();
				}
			}
		} 
		else {
			model.update();
			view.update();
			model.freeBubbleMemory();
			view.freeBubbleMemory();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (isSinglePlayer) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				model.movePlayer(model.getPlayerOne(),Utilities.MOVE_LEFT);
				break;
			case KeyEvent.VK_D:
				model.movePlayer(model.getPlayerOne(),Utilities.MOVE_RIGHT);
				break;
			case KeyEvent.VK_SPACE:
				if (spacebarAlreadyPressed)
					return;
				spacebarAlreadyPressed = true;
				model.movePlayer(model.getPlayerOne(),Utilities.JUMPING);
				break;
			case KeyEvent.VK_P:
				if(shootAlreadyPressed) return;
				shootAlreadyPressed = true;
				model.movePlayer(model.getPlayerOne(), Utilities.SHOOT);
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			default:
				return;
			}
		} else {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				client.sendMessage(Utilities.moveLeft());
				break;
			case KeyEvent.VK_D:
				client.sendMessage(Utilities.moveRight());
				break;
			case KeyEvent.VK_SPACE:
				if (spacebarAlreadyPressed)
					return;
				spacebarAlreadyPressed = true;
				client.sendMessage(Utilities.requestJump());
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			default:
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isSinglePlayer) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				model.movePlayer(model.getPlayerOne(),Utilities.IDLE_LEFT);
				break;
			case KeyEvent.VK_D:
				model.movePlayer(model.getPlayerOne(),Utilities.IDLE_RIGHT);
				break;
			case KeyEvent.VK_SPACE:
				spacebarAlreadyPressed = false;
			case KeyEvent.VK_P:
				shootAlreadyPressed = false;
				break;
			default:
				return;
			}
		} else {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				client.sendMessage(Utilities.requestIdleLeft());
				break;
			case KeyEvent.VK_D:
				client.sendMessage(Utilities.requestIdleRight());
				break;
			case KeyEvent.VK_SPACE:
				spacebarAlreadyPressed = false;
			default:
				return;
			}
		}
	}

	public GameModel getModel() {
		return model;
	}

}
