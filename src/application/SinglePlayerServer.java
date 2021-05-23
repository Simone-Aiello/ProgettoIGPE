package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SinglePlayerServer implements Runnable {
	private ServerSocket server;

	public SinglePlayerServer() {
		try {
			server = new ServerSocket(8000);
		} catch (IOException e) {
			System.out.println("Error while starting the server");
		}
	}

	@Override
	public void run() {
		try {
			Socket connection = server.accept();
			GameConnectionManager manager = new GameConnectionManager(connection);
			Thread t = new Thread(manager);
			t.start();
		} catch (IOException e) {
			System.out.println("Error while connecting to the client");
		}
	}
}
