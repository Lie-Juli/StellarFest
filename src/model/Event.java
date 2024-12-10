package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.Connect;

public class Event {
	// untuk koneksi dengan database
	private static Connect con = Connect.getInstance();
	
	// data-data apa saja yang dimiliki event
	private int id;
	private String name;
	private String date;
	private String location;
	private String description;
	private int organizerID;
	
	public Event(int id, String name, String date, String location, String description, int organizerID) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.location = location;
		this.description = description;
		this.organizerID = organizerID;
	}
	
	// getter setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOrganizerID() {
		return organizerID;
	}
	public void setOrganizerID(int organizerID) {
		this.organizerID = organizerID;
	}
	
	// fungsi untuk membuat event baru dan kemudian memasukannya ke dalam database
	public static int createEvent(String eventName, String date, String location, String description, int organizer_id) {
		String query = "INSERT INTO events(event_name, event_date, event_location, event_description, organizer_id) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		
		int success = 0;
		
		try {
			ps.setString(1, eventName);
			ps.setString(2, date);
			ps.setString(3, location);
			ps.setString(4, description);
			ps.setInt(5, organizer_id);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
		
	}
}
