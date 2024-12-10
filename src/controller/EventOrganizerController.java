package controller;

import model.EventOrganizer;

public class EventOrganizerController {

	public static String createEvent(String eventName, String date, String location, String description, int organizer_id) {
		String message = checkCreateEventInput(eventName, date, location, description);
		
		if(message.equals("success")) {
			EventOrganizer.createEvent(eventName, date, location, description, organizer_id);
			return "event succesfully added";
		}
		return message;
	}
	
	public static String checkCreateEventInput(String eventName, String date, String location, String description) {
		return EventOrganizer.checkCreateEventInput(eventName, date, location, description);
	}

}
