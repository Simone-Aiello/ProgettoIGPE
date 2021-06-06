package application.net.server.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import application.net.server.AcceptConnection;

public class MultiplayerServer extends AcceptConnection implements Runnable{

	public MultiplayerServer(JTextArea area, ServerSocket server) {
		super(area, server);
	}

	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				super.area.append("Waiting for connection on port 8000..." + System.lineSeparator());
				Socket connection1 = server.accept();
				executor.submit(new InitialConnectionHandler(connection1));				 
			}
		} catch (IOException e) {
			area.append("Error while connecting to the client" + System.lineSeparator());
		}
		
	}

}
