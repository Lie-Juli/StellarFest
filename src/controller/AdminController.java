package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Admin;
import model.Event;
import model.User;
import util.Connect;

public class AdminController {

	public ObservableList<Event> viewAllEvent(ArrayList<Event> eventList, Connect connect) {
		getAllEvent(eventList, connect);
		ObservableList<Event> events = FXCollections.observableArrayList(eventList);
		return events;
	}
	
	public ObservableList<User> viewAllUser(ArrayList<User> userList, Connect connect) {
		getAllUser(userList, connect);
		ObservableList<User> users = FXCollections.observableArrayList(userList);
		return users;
	}
	
	public void getAllEvent(ArrayList<Event> eventList, Connect connect) {
		Admin.getAllEvent(eventList, connect);
	}
	
	public boolean deleteEvent(String id, Connect connect) {
		return Admin.deleteEvent(id, connect);
	}
	
	public void getAllUser(ArrayList<User> userList, Connect connect) {
		Admin.getAllUser(userList, connect);
	}
	
	public boolean deleteUser(String id, Connect connect) {
		return Admin.deleteUser(id, connect);
	}
	
}
