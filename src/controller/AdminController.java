package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Admin;
import model.Event;
import model.User;
import util.Connect;

public class AdminController { // Agak berantakan karena ingin meniru class diagram

	// Method untuk mengembalikan events ke view 
	public ObservableList<Event> viewAllEvent(ArrayList<Event> eventList, Connect connect) {
		// Panggil method untuk ambil semua events, dan simpan ke dalam eventList
		getAllEvent(eventList, connect);
		
		// Ubah eventList kedalam bentuk observable list agar bisa dimasukan ke table.
		ObservableList<Event> events = FXCollections.observableArrayList(eventList);
		return events; //return events variable
	}
	
	// Method untuk mengembalikan users ke view 
	public ObservableList<User> viewAllUser(ArrayList<User> userList, Connect connect) {
		// Panggil method untuk ambil semua users, dan simpan ke dalam userList
		getAllUser(userList, connect);
		
		// Ubah userList kedalam bentuk observable list agar bisa dimasukan ke table.
		ObservableList<User> users = FXCollections.observableArrayList(userList);
		return users; //return users variable
	}
	
	// Method untuk ambil semua events, dan simpan ke dalam eventList (panggil admin method)
	public void getAllEvent(ArrayList<Event> eventList, Connect connect) {
		Admin.getAllEvent(eventList, connect);
	}
	
	// Method untuk menghapus event (panggil admin method)
	public boolean deleteEvent(int id, Connect connect) {
		return Admin.deleteEvent(id, connect);
	}
	
	// Method untuk ambil semua users, dan simpan ke dalam userList (panggil admin method)
	public void getAllUser(ArrayList<User> userList, Connect connect) {
		Admin.getAllUser(userList, connect);
	}
	
	// Method untuk menghapus user (panggil admin method)
	public boolean deleteUser(int id, Connect connect) {
		return Admin.deleteUser(id, connect);
	}
	
}
