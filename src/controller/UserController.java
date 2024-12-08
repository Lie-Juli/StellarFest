package controller;

import model.User;

public class UserController {

	public static String register(String email, String username, String password, String role) {
		if(email == null) {
			return "email cannot be empty!";
		}
		else if(username == null) {
			return "username cannot be empty!";
		}
		else if(password == null) {
			return "password cannot be empty!";
		}
		else if(password.length() < 5) {
			return "password length must be greater than 5!";
		}
		else if(role == null) {
			return "role must be selected!";
		}
		
		int success = User.register(email, username, password, role);
		
		if(success == 0) {
			return "email already taken or username already taken";
		}
		
		return "register success";
	}
	
	public static User login(String username, String password) {
		return User.login(username, password);
	}

}
