package application.net.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedHashMap;


import application.model.Utilities;
import application.net.server.dataBase.DBAccessHandler;
import application.net.server.multiplayer.MultiplayerGameHandler;
import application.net.server.multiplayer.RoomHandler;

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
			String[] splittedReq = request.split(Utilities.MESSAGE_SEPARATOR);
			if(splittedReq[0].equals(Utilities.HOST)) {
				String code = RoomHandler.generateCode();
				out.println(code);
				out = null;
				in = null;
				RoomHandler.rooms.put(code, new MultiplayerGameHandler(socket,code));
			}
			else if(splittedReq[0].equals(Utilities.LOGIN)) {
				String res = DBAccessHandler.checkLogin(splittedReq[1], splittedReq[2]);
				out.println(res);
				out = null;
				in = null;
			
			}else if(splittedReq[0].equals(Utilities.SIGNUP)) {
				String res = DBAccessHandler.checkSignUp(splittedReq[1], splittedReq[2]);
				out.println(res);
				out = null;
				in = null;
			
			}
			else if(splittedReq[0].equals(Utilities.LEADERBOARDS)) {
				LinkedHashMap<String, Integer> leaderboards = DBAccessHandler.getLeaderboards();
				String res;
				if(leaderboards == null)
					res = Utilities.ERROR_CONNECTING_DB;
				else if(leaderboards.size() == 0)
					res = Utilities.STILL_NO_CLASSIFICATION;
				else {
					StringBuilder str = new StringBuilder();
					for(String key : leaderboards.keySet()) {
						str.append(key + " " + leaderboards.get(key) + Utilities.MESSAGE_SEPARATOR);
					}
					res = str.toString();
				}
				out.println(res);
				out = null;
				in = null;
				
			}else if(splittedReq[0].equals(Utilities.UPDATE_SCORE)){
				System.out.println("sono entrato");
				String username = splittedReq[1];
				int score = Integer.parseInt(splittedReq[2]);
				String res = DBAccessHandler.updateScores(username, score);
				out.println(res);
				out = null;
				in = null;				
			}
			else {
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
		} catch (IOException e) {
			return;
		}
	}

}
