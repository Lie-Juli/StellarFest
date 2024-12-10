package controller;

import model.Event;

public class EventController {
	public static int createEvent(String eventName, String date, String location, String description, int organizer_id) {
		return Event.createEvent(eventName, date, location, description, organizer_id);
	}
}
