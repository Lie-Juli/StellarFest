package controller;

import model.Event;

public class EventController {
	
	// untuk memanggil function createEvent dari model event yang akan membuat event
	public static int createEvent(String eventName, String date, String location, String description, int organizer_id) {
		return Event.createEvent(eventName, date, location, description, organizer_id);
	}
}
