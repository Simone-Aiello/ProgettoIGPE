package application.net.server.db;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import application.net.server.AcceptConnection;

public class DBServer extends AcceptConnection implements Runnable {
	
	public DBServer(JTextArea area, ServerSocket server) {
		super(area, server);
	}

	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				super.area.append("Waiting for connection on port 9000..." + System.lineSeparator());
				Socket connection = server.accept();
				executor.submit(new DBConnectionHandler(connection));				 
			}
		} catch (IOException e) {
			area.append("Error while connecting to the client" + System.lineSeparator());
		}
		
	}

}
