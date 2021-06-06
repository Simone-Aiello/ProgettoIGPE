package application.net.server.db;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DBConnectionHandler implements Runnable{
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public DBConnectionHandler(Socket s) throws IOException{
		this.socket = s;
		out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
