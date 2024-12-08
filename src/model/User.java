package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connect;

public class User {
	private static Connect con = Connect.getInstance();
	
	private int userID;
	private String email;
	private String username;
	private String password;
	private String role;
	
	public User(int userID, String email, String username, String password, String role) {
		super();
		this.userID = userID;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public static int register(String email, String username, String password, String role) {
		String query = "INSERT INTO users(email, username, password, role) VALUES(?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		
		int success = 0;
		
		try {
			ps.setString(1, email);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, role);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public static User login(String email, String password) {
		String query = "SELECT * FROM users WHERE email = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		User user = null;
		
		try {
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("userID");
				String user_email = rs.getString("email");
				String username = rs.getString("username");
				String user_password = rs.getString("password");
				String role = rs.getString("role");
				user = new User(id, user_email, username, user_password, role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}
	
}
