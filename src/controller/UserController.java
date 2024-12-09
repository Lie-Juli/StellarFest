package controller;

import model.User;

public class UserController {
	
	//untuk melakukan register jika berhasil melewati validasi
	public static String register(String email, String username, String password, String role) {
		String message = checkRegisterInput(email, username, password, role);
		
		if(message.equals("success")) {
			User.register(email, username, password, role);
			return "register success";
		}
		return message;
		
		
	}
	
	//melakukan login
	public static User login(String email, String password) {
		return User.login(email, password);
	}
	
	// memanggil function checRegisterInput dari model yang akan memvalidasi input dari register
	public static String checkRegisterInput(String email, String username, String password, String role){
		return User.checkRegisterInput(email, username, password, role);
	}

}
