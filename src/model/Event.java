package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	public static void createEvent(String eventName, String date, String location, String description, int organizer_id) {
		String query = "INSERT INTO events(event_name, event_date, event_location, event_description, organizer_id) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, eventName);
			ps.setString(2, date);
			ps.setString(3, location);
			ps.setString(4, description);
			ps.setInt(5, organizer_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// fungsi untuk mengambil data-data dari suatu event tertentu
	public static Event viewEventDetail(int event_id) {
		String query = "SELECT * FROM events where events.event_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, event_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Integer id = rs.getInt("event_id");
				String name = rs.getString("event_name");
				String date = rs.getString("event_date");
				String location = rs.getString("event_location");
				String description = rs.getString("event_description");
				int organizer_id =rs.getInt("organizer_id");
				return new Event(event_id, name, date, location, description, organizer_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
