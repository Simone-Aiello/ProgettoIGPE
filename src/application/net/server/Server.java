package application.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import application.net.server.db.DBServer;
import application.net.server.multiplayer.MultiplayerServer;


public class Server{
	
	private ServerSocket multiplayerServer;
	private ServerSocket dbServer;
	private JTextArea area;
	private ExecutorService executor;
	
	public Server(JTextArea area) {
			this.area = area;
	}
	
	public void start() {
		
		try {
			executor = Executors.newCachedThreadPool();
			multiplayerServer = new ServerSocket(8000);
			dbServer = new ServerSocket(9000);
			executor.submit(new DBServer(area, dbServer));
			executor.submit(new MultiplayerServer(area, multiplayerServer));
		} catch (IOException e) {
			area.append("Server error" + System.lineSeparator());
		}
		
	}

}
