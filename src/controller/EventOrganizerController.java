package controller;

import java.util.ArrayList;

import model.Event;
import model.EventOrganizer;
import model.User;

public class EventOrganizerController {

	// untuk membuat event jika berhasil melewati validasi
	public static String createEvent(String eventName, String date, String location, String description, int organizer_id) {
		String message = checkCreateEventInput(eventName, date, location, description);
		
		if(message.equals("success")) {
			EventOrganizer.createEvent(eventName, date, location, description, organizer_id);
			return "event succesfully added";
		}
		return message;
	}
	
	// memanggil function checkCreateEventInput dari model yang akan memvalidasi input dari createEvent
	public static String checkCreateEventInput(String eventName, String date, String location, String description) {
		return EventOrganizer.checkCreateEventInput(eventName, date, location, description);
	}
	
	// memanggil fungsi getGuest dari model yang akan mendapatkan semua guest
	public static ArrayList<User> getGuest(){
		return EventOrganizer.getGuest();
	}
	
	// memanggil fungsi getVendor dari model yang akan mendapatkan semua vendor
	public static ArrayList<User> getVendor(){
		return EventOrganizer.getVendor();
	}
	
	// memanggul fungsi viewOrganizedEvents dari model yang akan mendapatkan semua event sesuai dengan event organizer yang membuatnya
	public static ArrayList<Event> viewOrganizedEvents(int userId){
		return EventOrganizer.viewOrganizedEvents(userId);
	}

	// Method untuk edit event name
	public boolean editEventName (int eventId, String newEventName) {	
		return EventOrganizer.editEventName(eventId, newEventName);
	}
}
