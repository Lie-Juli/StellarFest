package controller;

import model.Event;

public class EventController {
	
	// untuk memanggil function createEvent dari model event yang akan membuat event
	public static void createEvent(String eventName, String date, String location, String description, int organizer_id) {
		Event.createEvent(eventName, date, location, description, organizer_id);
	}
	
	// untuk memanggil function viewEventDetail dari model yang akan mendapatkan data-data dari suatu event tertentu
	public static Event viewEventDetail(int event_id) {
		return Event.viewEventDetail(event_id);
	}
}
