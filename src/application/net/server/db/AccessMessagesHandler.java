package application.net.server.db;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import application.model.Utilities;

public class AccessMessagesHandler implements Runnable {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public AccessMessagesHandler(Socket s) throws IOException {
		this.socket = s;
		out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
	}

	private void closeStreams() throws IOException {
		if (out != null)
			out.close();
		out = null;
		if (in != null)
			in.close();
		in = null;
		if (socket != null)
			socket.close();
		socket = null;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String input = in.readLine();
			String username = null;
			if (input.equals(Utilities.LOGIN)) {
				String line = in.readLine();
				String[] userString = line.split(Utilities.MESSAGE_SEPARATOR);
				User user = new User(userString[0], userString[1]);
				if (!DBConnectionHandler.getInstance().existsUser(user)) {
					sendMessage(Utilities.USER_NOT_EXIST);
					closeStreams();
					return;
				}
				if (!DBConnectionHandler.getInstance().checkUser(user)) {
					if (DBConnectionHandler.getInstance().connectionError)
						sendMessage(Utilities.ERROR_CONNECTING_DB);
					sendMessage(Utilities.WRONG_PASSWORD);
					System.out.println(Utilities.WRONG_PASSWORD);
					closeStreams();
					return;
				}
				username = user.getUsername();
				sendMessage(Utilities.SUCCESS_ACCESS);
				System.out.println(Utilities.SUCCESS_ACCESS);
			} else if (input.equals(Utilities.SIGNUP)) {
				String line = in.readLine();
				String[] userString = line.split(Utilities.MESSAGE_SEPARATOR);
				User user = new User(userString[0], userString[1]);
				if (DBConnectionHandler.getInstance().existsUser(user)) {
					sendMessage(Utilities.USER_ALREADY_EXIST);
					System.out.println(Utilities.USER_ALREADY_EXIST);
					closeStreams();
					return;
				} else {
					if (!DBConnectionHandler.getInstance().insertUser(user)) {
						sendMessage(Utilities.ERROR_CONNECTING_DB);
						closeStreams();
						return;
					}
				}
				username = user.getUsername();
				sendMessage(Utilities.SUCCESS_ACCESS);
				System.out.println(Utilities.SUCCESS_ACCESS);
			} else if (input.equals(Utilities.ALL_CLASSIFICATION)) {
				handleClassification();
			}
			else { //se ricevo altre richieste diverse da queste sono illeggittime
				closeStreams();
				return;
			}
			while (true) {
				String request = in.readLine();
				if (request.equals(Utilities.UPDATE_SCORE)) {
					String scoreString = in.readLine();
					int score = Integer.parseInt(scoreString);

					if (!DBConnectionHandler.getInstance().updateGames(username, score))
						sendMessage(Utilities.ERROR_CONNECTING_DB); // stabilisci cosa fare quando non riesce a salvare
																	// il nuovo score
					else
						sendMessage(Utilities.PROGRESS_SAVED);

				} else if (request.equals(Utilities.ALL_CLASSIFICATION)) {
					handleClassification();
				} else if (request.equals(Utilities.MY_CLASSIFICATION)) {
					List<Game> user_games_record = DBConnectionHandler.getInstance().getUserClassification(username);
					if (user_games_record == null) { 
						sendMessage(Utilities.ERROR_CONNECTING_DB);
						closeStreams();
						return;
					}
					sendMessage(Utilities.START_CLASSIFICATION);
					for (Game game : user_games_record) {
						sendMessage(game.toString());
					}
					sendMessage(Utilities.END_CLASSIFICATION);
					return;
				}

			}
		} catch (Exception e) {
			sendMessage(Utilities.ERROR_CONNECTING_DB);
			out = null;
			return;
		}
	}

	public void sendMessage(String message) {
		if (out == null)
			return;
		out.println(message);
	}

	private void handleClassification() throws SQLException, IOException {
		LinkedHashMap<String, Integer> classification = DBConnectionHandler.getInstance().getClassification();
		if (DBConnectionHandler.getInstance().connectionError == true) {
			sendMessage(Utilities.ERROR_CONNECTING_DB);
			closeStreams();
			return;
		}
		if (classification.size() == 0) {
			sendMessage(Utilities.STILL_NO_CLASSIFICATION);
			return;
		}
		sendMessage(Utilities.START_CLASSIFICATION);
		for (String username : classification.keySet()) {
			sendMessage(username + Utilities.MESSAGE_SEPARATOR + classification.get(username));
		}
		sendMessage(Utilities.END_CLASSIFICATION);
		return;
	}

}
