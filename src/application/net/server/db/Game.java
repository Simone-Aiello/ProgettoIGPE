package application.net.server.db;

import application.model.Utilities;

public class Game {

	private String username;
	private String date_time;
	private int score;
	private long playTime;
	
	public Game(String username, String date_time, int score, long playTime) {
		this.username = username;
		this.date_time = date_time;
		this.score = score;
		this.playTime = playTime;
	}	
	
	public String getUsername() {
		return username;
	}

	public String getDate_time() {
		return date_time;
	}

	public int getScore() {
		return score;
	}

	public long getPlayTime() {
		return playTime;
	}
	
	@Override
	public String toString() {
		//gestisci se ti serve
		return "";
	}

}
