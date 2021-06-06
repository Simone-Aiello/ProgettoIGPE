package multiplayerserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;


public class Server implements Runnable{
	private ServerSocket server;
	private JTextArea area;
	private ExecutorService executor;
	public Server(JTextArea area) {
		this.area = area;
	}
	@Override
	public void run() {
		try {
			executor = Executors.newCachedThreadPool();
			server = new ServerSocket(8000);
			while(!Thread.currentThread().isInterrupted()) {
				area.append("Waiting for connection..." + System.lineSeparator());
				Socket connection1 = server.accept();
				executor.submit(new InitialConnectionHandler(connection1));
			}
		} catch (IOException e) {
			area.append("Server error" + System.lineSeparator());
		}
	}
}
