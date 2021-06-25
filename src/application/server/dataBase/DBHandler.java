package application.server.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;


import org.springframework.security.crypto.bcrypt.BCrypt;

import application.model.Utilities;

public class DBHandler {
	
	private static DBHandler instance = null;
	private Connection connection = null;
	
	private DBHandler() {		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:BubbleBobbleDB.db");
			System.out.println("connected to db");
		} catch (SQLException e) {
			System.out.println("error connecting DB");
		}
	}
	
	public static DBHandler getInstance() {
		if(instance == null)
			instance = new DBHandler();
		return instance;
	}
	
	public synchronized boolean insertUser(User user) throws Exception { //va bene cosi
		if(connection == null || connection.isClosed() || user == null) {
			throw new Exception(Utilities.ERROR_CONNECTING_DB);
		}
		
		if(existsUser(user))
			return false;
		
		PreparedStatement p = connection.prepareStatement("INSERT INTO Users values(?, ?);");
		p.setString(1, user.getUsername());
		p.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
		p.executeUpdate();
		p.close();		
		return true;		
	}
	
	public synchronized boolean existsUser(User user) throws Exception {
		if(connection == null || connection.isClosed() || user == null) {
			throw new Exception(Utilities.ERROR_CONNECTING_DB);
		}
		
		String query = "SELECT * FROM Users WHERE username=?;";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, user.getUsername());
		ResultSet rs = p.executeQuery();
		boolean result = rs.next();
		p.close();
		return result;
	}
	
	public synchronized boolean checkUser(User user) throws Exception { 
		if(connection == null || connection.isClosed() || user == null) {
			throw new Exception(Utilities.ERROR_CONNECTING_DB);
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
	
	public synchronized boolean updateGames(String username, int score) throws Exception {
		if(connection == null || connection.isClosed() || username == null) {
			throw new Exception(Utilities.ERROR_CONNECTING_DB);
		}
		
		//cambia qui
		String query = "INSERT INTO Games ('Username', 'Score') VALUES(?, ?);";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, username);
		p.setInt(2, score);
		p.executeUpdate();
		p.close();
		return true;		
	}
	
	public synchronized LinkedHashMap<String, Integer> getLeaderboards() throws Exception {
		
		if(connection == null || connection.isClosed()) {
			throw new Exception(Utilities.ERROR_CONNECTING_DB);
		}
		
		String query = "SELECT * FROM Classification ORDER BY high_score DESC;";
		PreparedStatement p = connection.prepareStatement(query);
		ResultSet res = p.executeQuery();
		LinkedHashMap<String, Integer> classification = new LinkedHashMap<String, Integer>();
		while(res.next()) {
			System.out.println(res.getString("username") + res.getInt("high_score"));
			classification.put(res.getString("username"), res.getInt("high_score"));			
		}
		return classification;		
	}

}
