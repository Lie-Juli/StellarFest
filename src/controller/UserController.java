package controller;

import model.User;

public class UserController {

	public static String register(String email, String username, String password, String role) {
		String message = checkRegisterInput(email, username, password);
		
		if(message.equals("success")) {
			int success = User.register(email, username, password, role);
			return "register success";
		}
		return message;
		
		
	}
	
	public static User login(String email, String password) {
		return User.login(email, password);
	}
	
	public static String checkRegisterInput(String email, String username, String password){
		return User.checkRegisterInput(email, username, password);
	}

}
