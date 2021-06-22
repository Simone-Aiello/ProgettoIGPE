package application.net.server.db;

import application.model.Utilities;

public class Game implements Comparable<Game>{

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
		return username + Utilities.MESSAGE_SEPARATOR + score + Utilities.MESSAGE_SEPARATOR + playTime + Utilities.MESSAGE_SEPARATOR + date_time;
	}

	@Override
	public int compareTo(Game o) {
		return date_time.compareTo(o.date_time);
	}

}
