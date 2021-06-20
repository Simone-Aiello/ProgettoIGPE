package application.net.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import application.model.Utilities;


public class Client {
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String roomCode;

	private String error = null;
	private boolean startedCorrectly;

	public Client(String mode,String code) {
		try {
			startedCorrectly = true;
			socket = new Socket("localhost",8000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(mode == Utilities.HOST) {
				out.println(Utilities.HOST);
				roomCode = in.readLine();
			}
			else if(mode == Utilities.JOIN) {
				roomCode = code;
				out.println(Utilities.joinRequest(code));
				String response = in.readLine();
				if(!response.equals(Utilities.OK_JOIN)) {
					startedCorrectly = false;
					error = response;
				}
			}
		} catch (IOException e) {
			socket = null;
			out = null;
			in = null;
			startedCorrectly = false;
		}
	}
	
	public Client() { //se viene chiamato questo costruttore il client si connette sulla porta 9000 del server, quella per lo scambio di messaggi relativi al DB
		try {
			socket = new Socket("localhost",9000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			socket = null;
			out = null;
			in = null;
			System.out.println("Error connecting to server");
		}		
	}
	
	public boolean isStartedCorrectly() {
		return startedCorrectly;
	}
	public String getRoomCode() {
		return roomCode;
	}
	public void sendMessage(String message) {
		if(out != null) out.println(message);
	}
	public String[] read() {
		String[] res = null;
		try { 
			if(in != null && in.ready()) {
				String line = in.readLine();
				res = line.split(Utilities.MESSAGE_SEPARATOR);		
			}
		}
		catch(IOException e) {
			return null;
		}
		return res;
	}
	public String getError() {
		return error;
	}
}
