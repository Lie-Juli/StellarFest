package controller;

import java.util.ArrayList;

import javafx.collections.ObservableList;
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
	
	// memanggil fungsi getGuest dari model yang akan mendapatkan semua guest yang belum diinvite pada suatu event
	public static ArrayList<User> getGuest(int event_id){
		return EventOrganizer.getGuest(event_id);
	}
	
	// memanggil fungsi getVendor dari model yang akan mendapatkan semua vendor yang belum diinvite pada suatu event
	public static ArrayList<User> getVendor(int event_id){
		return EventOrganizer.getVendor(event_id);
	}
	
	// memanggul fungsi viewOrganizedEvents dari model yang akan mendapatkan semua event sesuai dengan event organizer yang membuatnya
	public static ArrayList<Event> viewOrganizedEvents(int userId){
		return EventOrganizer.viewOrganizedEvents(userId);
	}

	// Method untuk edit event name
	public boolean editEventName (int eventId, String newEventName) {	
		return EventOrganizer.editEventName(eventId, newEventName);
	}
	
	// untuk membuat invitation kepada vendor jika berhasil melewati validasi
	public static String addVendor(ObservableList<User> users, int event_id) {
		String message = checkAddVendorInput(users);
		if(message.equals("add vendor success")) {
			EventOrganizer.addVendor(users, event_id);
		}
		
		return message;
	}
	
	// untuk membuat invitation kepada guest jika berhasil melewati validasi
	public static String addGuest(ObservableList<User> users, int event_id) {
		String message = chechAddGuestInput(users);
		if(message.equals("add guest success")) {
			EventOrganizer.addGuest(users, event_id);
		}
		
		return message;
	}
	
	// memanggil function checkAddVendorInput dari model yang akan memvalidasi input dari addVendor
	public static String checkAddVendorInput(ObservableList<User> users) {
		return EventOrganizer.checkAddVendorInput(users);
	}
	
	// memanggil function checkAddGuestInput dari model yang akan memvalidasi input dari addGuest
	public static String chechAddGuestInput(ObservableList<User> users) {
		return EventOrganizer.checkAddGuestInput(users);
	}
}
