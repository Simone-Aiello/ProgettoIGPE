package application.net.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import application.model.Utilities;

public class DBClient {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean DBRequestDone = false;

	private LinkedHashMap<String, Integer> classification = new LinkedHashMap<String, Integer>();
	private List<String> user_games_records = new ArrayList<String>();

	private static DBClient instance = null;

	private DBClient() { // client che si occupa solo di scambiare messaggi per richieste sul DB
		try {
			socket = new Socket("localhost", 8000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			socket = null;
			out = null;
			in = null;
			System.out.println("Error connecting to server");
		}
	}

	public static DBClient getInstance() {
		if (instance == null)
			instance = new DBClient();
		return instance;
	}

	public void reset() {
		instance = null;
		out = null;
		in = null;
		socket = null;
	}

	public void sendMessage(String message) {
		if (out != null)
			out.println(message);
	}

	public String[] read() {
		String[] res = null;
		try {
			if (in != null && in.ready()) {
				String line = in.readLine();
				res = line.split(Utilities.MESSAGE_SEPARATOR);
			}
		} catch (IOException e) {
			return null;
		}
		return res;
	}

	public String authentication(String username, String password, boolean login) {
		if (!DBRequestDone) {
			sendMessage(Utilities.DB_ACCESS);
			DBRequestDone = true;
		}
		if (login)
			sendMessage(Utilities.LOGIN);
		else {
			// inserirsci dei controlli sulla password e sullo username
			sendMessage(Utilities.SIGNUP);
		}
		sendMessage(username + Utilities.MESSAGE_SEPARATOR + password);
		System.out.println("messaggio mandato");
		try {
			if (in != null && in.ready()) {
				String line = in.readLine();
				return line;
			}
			return Utilities.ERROR_CONNECTING_DB;
		} catch (Exception e) {
			out = null;
			return Utilities.ERROR_CONNECTING_DB;
		}
	}

	public String classification_request() {
		if (!DBRequestDone) {
			sendMessage(Utilities.DB_ACCESS);
			DBRequestDone = true;
		}
		sendMessage(Utilities.ALL_CLASSIFICATION);
		try {
			if (in != null && in.ready()) {
				String line = in.readLine();
				classification.clear();
				if (line.equals(Utilities.START_CLASSIFICATION)) {
					String[] record = read();
					while (!record[0].equals(Utilities.END_CLASSIFICATION)) {
						classification.put(record[0], Integer.parseInt(record[1])); // salvo le posizioni della
																					// classifica
						record = read();
					}
					return Utilities.OK_CLASSIFICATION;
				} else {
					return line;
				}
			}
			return Utilities.ERROR_CONNECTING_DB;
		} catch (Exception e) {
			out = null;
			return Utilities.ERROR_CONNECTING_DB;
		}
	}

	public String user_game_records_request() {
		sendMessage(Utilities.MY_CLASSIFICATION);
		try {
			if (in != null && in.ready()) {
				user_games_records.clear();
				String line = in.readLine();

				if (line.equals(Utilities.START_CLASSIFICATION)) {
					String record = in.readLine();
					while (!record.equals(Utilities.END_CLASSIFICATION)) {
						user_games_records.add(record); // salvo le posizioni della classifica
						record = in.readLine();
					}
					return Utilities.OK_CLASSIFICATION;
				} else {
					return line;
				}
			}
			return Utilities.ERROR_CONNECTING_DB;
		} catch (Exception e) {
			out = null;
			return Utilities.ERROR_CONNECTING_DB;
		}
	}

}
