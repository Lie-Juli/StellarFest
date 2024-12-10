package model;

import java.time.LocalDate;
import java.util.List;

import controller.EventController;

public class EventOrganizer extends User{

	// untuk menyimpan event apa saja yang telah dibuat event organizer
	public List<String> eventsCreated;
	
	public EventOrganizer(int userID, String email, String username, String password, String role) {
		super(userID, email, username, password, role);
	}

	public List<String> getEventsCreated() {
		return eventsCreated;
	}

	public void setEventsCreated(List<String> eventsCreated) {
		this.eventsCreated = eventsCreated;
	}

	
	public static int createEvent(String eventName, String date, String location, String description, int organizer_id) {
		return EventController.createEvent(eventName, date, location, description, organizer_id);
		
	}
	
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
	

}
