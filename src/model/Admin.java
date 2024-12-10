package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Admin extends User{

	public Admin(int userID, String email, String username, String password, String role) {
		super(userID, email, username, password, role);
	}

	public static void getAllEvent(ArrayList<Event> eventList, Connect connect) {
		eventList.clear();
		//Select query from DB
		String query = "SELECT * FROM events";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer id = connect.rs.getInt("id");
				String name = connect.rs.getString("name");
				String date = connect.rs.getString("date");
				String location = connect.rs.getString("location");
				String description = connect.rs.getString("description");
				eventList.add(new Event(id, name, date, location, description));
			}
		} catch (Exception e) {
			
		}
	}
	
	public static boolean deleteEvent(String id, Connect connect) {
		if (!isInteger(id)) {
			return false;
		}
		
		String querySearch = "SELECT * FROM events WHERE id = ? ";
		PreparedStatement ps = connect.prepareStatement(querySearch);
		
		try {
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = String.format("DELETE FROM events WHERE id = %s", id);
		connect.execUpdate(query);
		return true;
	}
	
	private static boolean isInteger(String id) {
		try {
			Integer.parseInt(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void getAllUser(ArrayList<User> userList, Connect connect) {
		userList.clear();
		//Select query from DB
		String query = "SELECT * FROM users";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer id = connect.rs.getInt("id");
				String email = connect.rs.getString("email");
				String username = connect.rs.getString("username");
				String password = connect.rs.getString("password");
				String role = connect.rs.getString("role");
				userList.add(new User(id, email, username, password, role));
			}
		} catch (Exception e) {
			
		}
		
	}
	
	public static boolean deleteUser(String id, Connect connect) {
		if (!isInteger(id)) {
			return false;
		}
		
		String querySearch = "SELECT * FROM users WHERE id = ? ";
		PreparedStatement ps = connect.prepareStatement(querySearch);
		
		try {
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = String.format("DELETE FROM users WHERE id = %s", id);
		connect.execUpdate(query);
		return true;
	}

}
