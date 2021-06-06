package application.net.server.multiplayer;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;

import application.model.Entity;
import application.model.GameModel;
import application.model.Player;
import application.model.Utilities;

public class MultiplayerGameHandler implements Runnable {
	private PrintWriter out1 = null;
	private BufferedReader in1 = null;

	private PrintWriter out2 = null;
	private BufferedReader in2 = null;

	private GameModel model;
	private int frequency = 60;
	private Instant lastUpdate;

	private String roomCode;
	
	private Thread checkStillConnected;	
	
	boolean started = false;

	public MultiplayerGameHandler(Socket playerOne, String roomCode) throws IOException {
		out1 = new PrintWriter(new BufferedOutputStream(playerOne.getOutputStream()), false);
		in1 = new BufferedReader(new InputStreamReader(playerOne.getInputStream()));
		this.roomCode = roomCode;
		checkStillConnected = new Thread(new Runnable() {	
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					try {
						if(in1.ready()) {
							if(in1.readLine().equals(Utilities.DISCONNECTED)) {
								Thread.currentThread().interrupt();
								closeAllConnections();
								RoomHandler.rooms.remove(roomCode);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		checkStillConnected.start();
	}

	public void addPlayerTwo(Socket playerTwo) throws IOException {
		out2 = new PrintWriter(new BufferedOutputStream(playerTwo.getOutputStream()), false);
		in2 = new BufferedReader(new InputStreamReader(playerTwo.getInputStream()));
		model = new GameModel();
		model.startGame(false);
		lastUpdate = Instant.now();
	}

	private void closeAllConnections() {
		out1 = null;
		in1 = null;
		out2 = null;
		in2 = null;
	}

	public boolean isStarted() {
		return started;
	}

	private void read() throws IOException {
		readMessage(in1);
		readMessage(in2);
	}

	private void readMessage(BufferedReader in) throws IOException {
		if (in != null && in.ready()) {
			String line = in.readLine();
			int direction;
			if (line.equals(Utilities.moveLeft())) {
				direction = Utilities.MOVE_LEFT;
			} else if (line.equals(Utilities.moveRight())) {
				direction = Utilities.MOVE_RIGHT;
			} else if (line.equals(Utilities.requestIdleLeft())) {
				direction = Utilities.IDLE_LEFT;
			} else if (line.equals(Utilities.requestIdleRight())) {
				direction = Utilities.IDLE_RIGHT;
			} else if (line.equals(Utilities.requestJump())) {
				direction = Utilities.JUMPING;
			} else
				return;
			Player player;
			if (in == in1)
				player = model.getPlayerOne();
			else
				player = model.getPlayerTwo();
			if (direction == Utilities.JUMPING) {
				player.requestJump();
			} else {
				model.movePlayer(player, direction);
			}
		}
	}

	public void sendNewPosition() throws IOException {
		// Posizione dei player posizione dei nemici e posizione delle bolle
		StringBuilder message = new StringBuilder();
		String player1Pos = Utilities.position(Utilities.PLAYER, 1, model.getPlayerOne().getX(),
				model.getPlayerOne().getY(), model.getPlayerOne().getXState(), model.getPlayerOne().getYState());
		message.append(player1Pos + Utilities.MESSAGE_SEPARATOR);
		// Stessa cosa per player 2
		String player2Pos = Utilities.position(Utilities.PLAYER, 2, model.getPlayerTwo().getX(),
				model.getPlayerTwo().getY(), model.getPlayerTwo().getXState(), model.getPlayerTwo().getYState());
		message.append(player2Pos + Utilities.MESSAGE_SEPARATOR);
		for (int i = 0; i < model.getEnemies().size(); i++) {
			Entity e = (Entity) model.getEnemies().get(i);
			String enemyPos = Utilities.position(Utilities.ENEMY, i, e.getX(), e.getY(), e.getXState(), e.getYState());
			message.append(enemyPos + ";");
		}
		out1.println(message.toString());
		out2.println(message.toString());
		// Se uno dei due giocatori si disconnette vengono chiuse le connessioni e
		// rimossa la stanza
		if (out1.checkError() || out2.checkError()) {
			closeAllConnections();
			RoomHandler.rooms.remove(roomCode);
		}
	}

	@Override
	public void run() {
		try {
			checkStillConnected.interrupt();
			started = true;
			while (!Thread.currentThread().isInterrupted()) {
				read();
				if (Duration.between(lastUpdate, Instant.now()).toMillisPart() > frequency) {
					model.update();
					sendNewPosition();
					lastUpdate = Instant.now();
				}
			}
		} catch (IOException e) {
			closeAllConnections();
			RoomHandler.rooms.remove(roomCode);
		}
	}

}
