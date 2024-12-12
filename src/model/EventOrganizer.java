package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.EventController;
import util.Connect;

public class EventOrganizer extends User{
	private static Connect con = getCon();
	
	// untuk menyimpan event apa saja yang telah dibuat event organizer
	private static ArrayList<Event> eventsCreated;
	private static ArrayList<User> guestList;
	private static ArrayList<User> vendorList;
	
	public EventOrganizer(int userID, String email, String username, String password, String role) {
		super(userID, email, username, password, role);
	}

	// getter setter
	public ArrayList<Event> getEventsCreated() {
		return eventsCreated;
	}

	public void setEventsCreated(ArrayList<Event> eventsCreated) {
		this.eventsCreated = eventsCreated;
	}

	
	// untuk memangil function createEvent dari model event melalui controllernya
	public static int createEvent(String eventName, String date, String location, String description, int organizer_id) {
		return EventController.createEvent(eventName, date, location, description, organizer_id);
		
	}
	
	// untuk memvalidasi input dari createEvent
	public static String checkCreateEventInput(String eventName, String date, String location, String description) {
		LocalDate now = LocalDate.now(); 
		LocalDate datePicked = LocalDate.parse(date);
		
		// jika event name kosong
		if(eventName.isEmpty()) {
			return "event name cannot be empty!";
		}
		// jika date kosong
		if(date.isEmpty()) {
			return "event date cannot be empty!";
		}
		// jika date yang dipilih bukan di masa depan
		else if(datePicked.isBefore(now) || datePicked.isEqual(now)) {
			return "event Date must be in the future!";
		}
		// jika location kosong
		if(location.isEmpty()) {
			return "event location cannot be empty!";
		}
		// jika location kurang dari 5 karakter
		if(location.length() < 5) {
			return "event location length must be greater than 5 characters!";
		}
		// jika description kosong
		if(description.isEmpty()) {
			return "event description cannot be empty!";
		}
		// jika description lebih dari 200 karakter
		if(description.length() > 200) {
			return "event description cannot be more than 200 character!";
		}
		
		// jika sudah melewati semua validasi
		return "success";
	}
	
	// fungsi untuk mengambil semua user yang memiliki role guest dari database
	public static ArrayList<User> getGuest(){
		guestList = new ArrayList<>();
		String query = "SELECT * FROM users WHERE role = 'guest'";
		ResultSet rs = con.execQuery(query);
		
		try {
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				guestList.add(new User(id, email, username, password, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return guestList;
	}
	
	//  fungsi untuk mengambil semua user yang memiliki role vendor dari database
	public static ArrayList<User> getVendor(){
		vendorList = new ArrayList<>();
		String query = "SELECT * FROM users WHERE role = 'vendor'";
		ResultSet rs = con.execQuery(query);
		
		try {
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				vendorList.add(new User(id, email, username, password, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vendorList;
	}
	
	// fungsi untuk mengambil semua event yang memiliki userId yang sama dari database
	public static ArrayList<Event> viewOrganizedEvents(int userId){
		eventsCreated = new ArrayList<>();
		String query = "SELECT * FROM events WHERE organizer_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Integer id = rs.getInt("event_id");
				String name = rs.getString("event_name");
				String date = rs.getString("event_date");
				String location = rs.getString("event_location");
				String description = rs.getString("event_description");
				int organizer_id =rs.getInt("organizer_id");
				eventsCreated.add(new Event(id, name, date, location, description, organizer_id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return eventsCreated;
	}

	// Method untuk edit event name
	public static boolean editEventName (int eventId, String newEventName) {
		boolean nameIsEmpty = newEventName.isEmpty();
		if (nameIsEmpty) { // Validasi input 
			return false;
		}
		//Select query from DB
		String querySearch = "SELECT * FROM events WHERE event_id = ?";
		PreparedStatement ps = con.prepareStatement(querySearch);
		
		try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			
			boolean resultNotEmpty = rs.next();
			// jika event tidak ditemukan
			if(!resultNotEmpty) {
				return false;
			}
			// jika nama event lama sama dengan nama event baru
			if(rs.getString("event_name").equals(newEventName)) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// sudah melewati semua validasi akan diupdate
		String query = String.format("UPDATE events SET event_name = '%s' WHERE event_id = %s", newEventName, eventId);
		con.execUpdate(query);
		return true;
	}
}
