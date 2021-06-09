package application.net.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class DBConnectionHandler {
	
	private static DBConnectionHandler instance = null;
	private Connection connection = null;
	public boolean connectionError = false;
	
	private DBConnectionHandler() {		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:BubbleBobbleDB.bd");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBConnectionHandler getInstance() {
		if(instance == null)
			instance = new DBConnectionHandler();
		return instance;
	}
	
	public synchronized boolean insertUser(User user) throws SQLException { //va bene cosi
		if(connection == null || connection.isClosed() || user == null) {
			connectionError = true;
			return false;
		}
		
		if(existsUser(user))
			return false;
		
		PreparedStatement p = connection.prepareStatement("INSERT INTO Users values(?, ?);");
		p.setString(1, user.getUsername());
		String salt = BCrypt.gensalt(12);
		p.setString(2, BCrypt.hashpw(user.getPassword(), salt));
		p.executeUpdate();
		p.close();		
		return true;		
	}
	
	public synchronized boolean existsUser(User user) throws SQLException {
		if(connection == null || connection.isClosed() || user == null) {
			connectionError = true;
			return false;
		}
		
		String query = "SELECT * FROM Users WHERE username=?;";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, user.getUsername());
		ResultSet rs = p.executeQuery();
		boolean result = rs.next();
		p.close();
		return result;
	}
	
	public synchronized boolean checkUser(User user) throws SQLException { 
		if(connection == null || connection.isClosed() || user == null) {
			connectionError = true;
			return false;
		}
		
		String query = "SELECT * FROM Users WHERE username=?;";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, user.getUsername());		
		ResultSet rs = p.executeQuery();
		boolean result = false;
		if(rs.next()) {
			String password = rs.getString("password");
			result = BCrypt.checkpw(user.getPassword(), password);
		}
		p.close();
		return result;
	}
	
	public synchronized boolean updateGames(String username, int score, int time) throws SQLException {
		if(connection == null || connection.isClosed() || username == null) {
			connectionError = true;
			return false;
		}
		
		String query = "INSERT INTO Games ('Username', 'Score', 'PlayTime', 'data_time') VALUES(?, ?, ?, datetime('now', 'localtime');";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, username);
		p.setInt(2, score);
		p.setInt(3, time);
		p.executeUpdate();
		p.close();
		return true;		
	}
	
	public synchronized LinkedHashMap<String, Integer> getClassification() throws SQLException {
		if(connection == null || connection.isClosed()) {
			connectionError = true;
			return null;
		}
		
		String query = "SELECT * FROM Classification ORDER BY 'high_score' DESC, 'username' ASC;";
		PreparedStatement p = connection.prepareStatement(query);
		ResultSet res = p.executeQuery();
		LinkedHashMap<String, Integer> classification = new LinkedHashMap<String, Integer>();
		while(res.next()) {
			classification.put(res.getString("username"), res.getInt("high_score"));
		}
		return classification;		
	}
	
	public synchronized List<Game> getUserClassification(String username) throws SQLException {  //gestisci questo
		if(connection == null || connection.isClosed()) {
			connectionError = true;
			return null;
		}
		
		String query = "SELECT * FROM Games WHERE Username = ?;";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, username);
		ResultSet res = p.executeQuery();
		ArrayList<Game> userGames = new ArrayList<Game>();
		while(res.next()) {
			userGames.add(new Game(res.getString("Username"), res.getString("data_time"), res.getInt("Score"), res.getInt("PlayTime")));
		}
		//a questo punto in userGames ho tutte le partite giocate dallo user con username dato
		//ritorno la lista ordinata in base alla data
		//fai l'ordinamento
		
		return userGames;
			
	}

}
