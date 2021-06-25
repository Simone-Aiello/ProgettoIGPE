package application.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import application.model.Utilities;

public class DataBaseClient {

	private static DataBaseClient instance = null;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String username = null;
	private boolean isResetted = false;

	private DataBaseClient() {
		initialize();
	}

	public static DataBaseClient getInstance() {
		if (instance == null)
			instance = new DataBaseClient();
		return instance;
	}

	public void initialize() {
		try {
			socket = new Socket("localhost", 8000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			isResetted = false;
		} catch (Exception e) {
			socket = null;
			out = null;
			in = null;
			System.out.println("Error connecting to server");
		}
	}

	public void reset() {
		out = null;
		in = null;
		socket = null;
		isResetted = true;
	}

	public void sendMessage(String message) {
		if (out != null)
			out.println(message);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String authentication(boolean login, String username, String password) {
		if (isResetted)
			initialize();
		try {
			if (login) {
				sendMessage(Utilities.loginRequest(username, password));
			} else {
				sendMessage(Utilities.signUpRequest(username, password));
			}
			String answer = in.readLine();
			return answer;

		} catch (IOException e) {
			out = null;
			return Utilities.ERROR;
		}
	}

	public String getLeaderboards() {
		if (isResetted)
			initialize();
		sendMessage(Utilities.LEADERBOARDS);
		String answer;
		try {
			answer = in.readLine();
		} catch (IOException e) {
			answer = Utilities.ERROR_CONNECTING_DB;
		}
		return answer;
	}

	public String updateScores(int score) {
		if (isResetted)
			initialize();
		sendMessage(Utilities.updateGamesRequest(this.username, score));
		String answer;
		try {
			answer = in.readLine();
			System.out.println("answer server" + answer);
		} catch (IOException e) {
			answer = Utilities.ERROR_CONNECTING_DB;
		}
		return answer;
	}

}
