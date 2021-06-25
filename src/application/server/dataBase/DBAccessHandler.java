package application.server.dataBase;


import java.util.LinkedHashMap;

import application.model.Utilities;

public class DBAccessHandler {

	
	public static String checkLogin(String username, String password) {
		User user = new User(username, password);
		String response = Utilities.SUCCESS_ACCESS;
		try {
			if(!DBHandler.getInstance().existsUser(user)) {
				response = Utilities.USER_NOT_EXIST;
			}else if(!DBHandler.getInstance().checkUser(user)) {
				response = Utilities.WRONG_PASSWORD;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = Utilities.ERROR_CONNECTING_DB;
		}
		return response;
	}
	
	public static String checkSignUp(String username, String password) {
		User user = new User(username, password);
		String response = Utilities.SUCCESS_ACCESS;
		
		try {
			if(DBHandler.getInstance().existsUser(user)) {
				response = Utilities.USER_ALREADY_EXIST;
			}else {
				DBHandler.getInstance().insertUser(user);
			}
		} catch (Exception e) {
			response = Utilities.ERROR_CONNECTING_DB;
		}
		return response;
	}
	
	public static LinkedHashMap<String, Integer> getLeaderboards(){
		try {
			return DBHandler.getInstance().getLeaderboards();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String updateScores(String username, int score) {
		String res;
		try {
			if(DBHandler.getInstance().updateGames(username, score)) {
				res =  Utilities.PROGRESS_SAVED;
			}
			else {
				res = Utilities.ERROR_CONNECTING_DB;
			}
		} catch (Exception e) {
			res = Utilities.ERROR_CONNECTING_DB;
		}
		return res;
	}


 /*
	public void run() {
		try {
			String request = in.readLine();
			if (request.equals(Utilities.LOGIN)) {
				System.out.println("sono entrato nel login");
				String[] userString = readAndSepare();
				User user = new User(userString[0], userString[1]); //username e password
				if(!DBHandler.getInstance().existsUser(user)) {
					//non esiste lo username
					System.out.println("user not exist");
					sendMessage(Utilities.USER_NOT_EXIST);
					closeStreams();
					return;
				}
				if(!DBHandler.getInstance().checkUser(user)) {
					if(DBHandler.getInstance().connectionError == true) {
						System.out.println("error connecting db");
						sendMessage(Utilities.ERROR_CONNECTING_DB);
						closeStreams();
						return;
					}
					//se sono qui vuol dire che l'unico motivo per cui non ha matchato lo user è la password sbagliata
					System.out.println("wrong password");
					sendMessage(Utilities.WRONG_PASSWORD);
					closeStreams();
					return;
				}
				//se sono qui allora chekUser ha tornato true
				System.out.println("right access");
				sendMessage(Utilities.SUCCESS_ACCESS);
				closeStreams();
				return;
			} else if (request.equals(Utilities.SIGNUP)) {
				return;

			} else if (request.equals(Utilities.CLASSIFICATION)) {
				return;

			} else {
				sendMessage(Utilities.ILLEGAL_REQUEST);
				closeStreams();
				return;
			}
		} catch (IOException e) {
			System.out.println("server error");
			sendMessage(Utilities.ERROR);
			out = null;
			return;
		} catch (SQLException e) {
			System.out.println("sql error");
			sendMessage(Utilities.ERROR_CONNECTING_DB);
			out = null;
			return;
		} 

	}*/

}
