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
	
	public static ArrayList<User> getGuest(){
		return EventOrganizer.getGuest();
	}
	
	public static ArrayList<User> getVendor(){
		return EventOrganizer.getVendor();
	}
	
	public static ArrayList<Event> viewOrganizedEvents(int userId){
		return EventOrganizer.viewOrganizedEvents(userId);
	}

	// Method untuk edit event name
	public boolean editEventName (String eventId, String organizerId, String newEventName) {	
		return EventOrganizer.editEventName(eventId, organizerId, newEventName);
	}
}
