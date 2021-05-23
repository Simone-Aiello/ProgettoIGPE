package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import application.model.GameModel;
import application.model.PlayerSettings;


public class GameConnectionManager implements Runnable{
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private GameModel game;
	public GameConnectionManager(Socket connection) {
		try {
			game = new GameModel();
			this.connection = connection;
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			System.out.println("Error");
		}
	}
	@Override
	public void run() {
		while(true) {
			try {
				//Riceve una direzione, se è l'update manda alla view il Model per visualizzarlo
				Integer direction = (Integer) in.readObject();
				if(direction == PlayerSettings.UPDATE) {
					game.update();
					//Il reset serve perchè altrimenti per questioni di ottimizzazione il socket non manderebbe la versione aggiornata ma la versione inviata la prima volta
					out.reset();
					out.writeObject(game);
					out.flush();
				}
				else if(direction == PlayerSettings.JUMPING) {
					game.handlePlayerJump();
				}
				else game.movePlayer(direction);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
