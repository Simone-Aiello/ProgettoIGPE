package application.net.server.dataBase;


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

}
