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

	// Method untuk mengambil semua event dari database, dan simpan ke eventList
	public static void getAllEvent(ArrayList<Event> eventList, Connect connect) {
		eventList.clear();
		//Select query from DB
		String query = "SELECT * FROM events";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer id = connect.rs.getInt("event_id");
				String name = connect.rs.getString("event_name");
				String date = connect.rs.getString("event_date");
				String location = connect.rs.getString("event_location");
				String description = connect.rs.getString("event_description");
				int organizer_id = connect.rs.getInt("organizer_id");
				eventList.add(new Event(id, name, date, location, description, organizer_id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method untuk menghapus event dari database
	public static boolean deleteEvent(int id, Connect connect) {
		String query = "DELETE FROM events WHERE event_id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setInt(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Method untuk mengambil semua user dari database, dan simpan ke eventList
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Method untuk menghapus user dari database
	public static boolean deleteUser(int id, Connect connect) {
		String query = "DELETE FROM users WHERE id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setInt(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
