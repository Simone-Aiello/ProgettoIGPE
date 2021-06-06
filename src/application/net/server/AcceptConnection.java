package application.net.server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

public class AcceptConnection {  
	
	protected JTextArea area;
	protected ExecutorService executor;
	protected ServerSocket server;
	
	public AcceptConnection(JTextArea area, ServerSocket server) {
		this.area = area;
		executor = Executors.newCachedThreadPool();
		this.server = server;
	}

}
