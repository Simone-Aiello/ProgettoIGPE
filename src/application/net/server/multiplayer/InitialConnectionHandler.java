package application.net.server.multiplayer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import application.model.Utilities;

public class InitialConnectionHandler implements Runnable {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	public InitialConnectionHandler(Socket s) throws IOException{
		this.socket = s;
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
			else {
				String [] res = request.split(" ");
				if(!RoomHandler.rooms.containsKey(res[1])) {
					out.println(Utilities.NOT_EXIXTS_ROOM);
				}
				else if(RoomHandler.rooms.get(res[1]).isStarted()) {
					out.println(Utilities.ROOM_FULL_ERROR);
				}
				else {
					out.println(Utilities.OK_JOIN);
					RoomHandler.rooms.get(res[1]).addPlayerTwo(socket);
					RoomHandler.excutor.submit(RoomHandler.rooms.get(res[1]));
				}
			}
		} catch (IOException e) {
			return;
		}
	}

}