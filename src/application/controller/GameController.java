package application.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import application.model.PlayerSettings;
import application.view.GameView;

public class GameController implements KeyListener {
	
	private GameView view;
	private boolean spacebarAlreadyPressed;
	private Socket socket;
	private ObjectOutputStream out;
	public GameController(GameView view,String ip,int port) {
		try {
			socket = new Socket(ip, port);
			this.view = view;
			spacebarAlreadyPressed = false;
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Unable to connect to the server");
		}
	}
	public void update() {   
		try {
			out.writeObject(PlayerSettings.UPDATE);
			out.flush();
			view.update();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		Integer directionToSend = null;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			directionToSend = PlayerSettings.MOVE_LEFT;
			break;
		case KeyEvent.VK_D:
			directionToSend = PlayerSettings.MOVE_RIGHT;
			break;
		case KeyEvent.VK_SPACE: //salto
			if (spacebarAlreadyPressed) return;
			spacebarAlreadyPressed = true;
			directionToSend = PlayerSettings.JUMPING;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			default:
				return;
		}
		//Viene mandato il tasto al server che lo reindirizza al client
		try {
			out.writeObject(directionToSend);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		Integer released = null;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			released = PlayerSettings.IDLE_LEFT;
			break;
		case KeyEvent.VK_D:
			released = PlayerSettings.IDLE_RIGHT;
			break;
		case KeyEvent.VK_SPACE:
			spacebarAlreadyPressed = false;
			default:
				return;
		}
		try {
			out.writeObject(released);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public Socket getSocket() {
		return socket;
	}

}
