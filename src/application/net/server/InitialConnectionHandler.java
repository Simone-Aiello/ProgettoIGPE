package application.net.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.model.Utilities;
import application.net.server.db.AccessMessagesHandler;
import application.net.server.multiplayer.MultiplayerGameHandler;
import application.net.server.multiplayer.RoomHandler;

public class InitialConnectionHandler implements Runnable {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;	
	private ExecutorService DBexecutor;
	
	public InitialConnectionHandler(Socket s) throws IOException{
		this.socket = s;
		DBexecutor = Executors.newCachedThreadPool();
		out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	@Override
	public void run() {
		String request;
		try {
			request = in.readLine();
			if(request.equals(Utilities.HOST)) {
				String code = RoomHandler.generateCode();
				out.println(code);
				out = null;
				in = null;
				RoomHandler.rooms.put(code, new MultiplayerGameHandler(socket,code));
			}
			else if(request.equals(Utilities.JOIN)){
				String [] res = request.split(" ");
				String joinResult = RoomHandler.joinRequest(res[1],socket);
				/*if(!RoomHandler.rooms.containsKey(res[1])) {
					out.println(Utilities.NOT_EXISTS_ROOM);

				}
				else if(RoomHandler.rooms.get(res[1]).isStarted()) {
					out.println(Utilities.ROOM_FULL_ERROR);
				}
				else {
					out.println(Utilities.OK_JOIN);
					RoomHandler.rooms.get(res[1]).addPlayerTwo(socket);
					RoomHandler.excutor.submit(RoomHandler.rooms.get(res[1]));

				}*/
				out.println(joinResult);
			}
			else if(request.equals(Utilities.DB_ACCESS)){
				DBexecutor.submit(new AccessMessagesHandler(socket));
				System.out.println("create accessMessagesHandler");
			}
		} catch (IOException e) {
			return;
		}
	}

}