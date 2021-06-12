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
import application.view.TopLayerGameView;

public class GameController implements KeyListener {

	private GameView view;
	private GameModel model;
	private boolean spacebarAlreadyPressed;
	private boolean shootAlreadyPressed;
	private boolean isSinglePlayer;
	private Instant lastBubble = Instant.now();
	private Client client;
	private TopLayerGameView topView;
	private Instant lastUpdate = null;
	public GameController(GameView view, TopLayerGameView topView, boolean isSinglePlayer) {
		this.view = view;
		this.topView = topView;
		spacebarAlreadyPressed = false;
		shootAlreadyPressed = false;
		this.isSinglePlayer = isSinglePlayer;
		model = new GameModel();
		if (isSinglePlayer) {
			ChangeSceneHandler.setTopBar(topView);
			ChangeSceneHandler.add("game", view);
			ChangeSceneHandler.setCurrentScene("game");
			ChangeSceneHandler.setFrameUndecorated(true);
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
					ChangeSceneHandler.setTopBar(topView);
					ChangeSceneHandler.add("game", view);
					ChangeSceneHandler.setCurrentScene("game");
					ChangeSceneHandler.setFrameUndecorated(true);
				}
				for (String s : update) {
					String[] message = s.split(" ");
					if(message[0].equals(Utilities.BUBBLE)) {
						model.capture(Integer.parseInt(message[1]),Integer.parseInt(message[2]));
					}
					else if(message[0].equals(Utilities.SCORE)) {
						model.setScore(Integer.parseInt(message[1]));
					}
					else if (message[1].equals(Utilities.PLAYER)) {
						Player player;
						if (message[2].equals("1")) {
							player = model.getPlayerOne();
						}  
						else { //Non ho controlli sul messaggio
							player = model.getPlayerTwo();
						}
						model.setPlayerPosition(player,message);
						
					} 
					else if (message[1].equals(Utilities.ENEMY)) {
						model.setEnemyPosition(message);
					}
					else if(message[1].equals(Utilities.BUBBLE)) {
						model.setBubblePosition(message);
					}
					else if(message[1].equals(Utilities.FOOD)) {
						model.setFoodPosition(message);
					}
				}
				view.update();
				lastUpdate = Instant.now();
			}
			else {
				if(lastUpdate != null && Duration.between(lastUpdate, Instant.now()).toSecondsPart() > 3) {
					ChangeSceneHandler.showMessage("Connection lost");
					ChangeSceneHandler.removeTopBar(topView);
					GameStarter.resetAll();
				}
			}
		} 
		else {
			model.update();
			view.update();
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
				if(shootAlreadyPressed || Duration.between(lastBubble, Instant.now()).toMillis() < 500) return;
				shootAlreadyPressed = true;
				//Si potrebbe fare che il controller notifica alla view di creare un altra bolla, non so quale sia meglio come soluzione
				model.movePlayer(model.getPlayerOne(), Utilities.SHOOT);
				lastBubble = Instant.now();
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
			case KeyEvent.VK_P:
				if(shootAlreadyPressed || Duration.between(lastBubble, Instant.now()).toMillis() < 500) return;
				shootAlreadyPressed = true;
				client.sendMessage(Utilities.BUBBLE);
				lastBubble = Instant.now();
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
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
			case KeyEvent.VK_P:
				shootAlreadyPressed = false;
				break;
			default:
				return;
			}
		}
	}

	public GameModel getModel() {
		return model;
	}

}
