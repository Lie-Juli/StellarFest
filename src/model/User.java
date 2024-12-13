package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connect;

public class User {
	// untuk koneksi dengan database
	private static Connect con = Connect.getInstance();
	
	// data-data apa saja yang akan dimiliki user
	private int userID;
	private String email;
	private String username;
	private String password;
	private String role;
	
	public User(int userID, String email, String username, String password, String role) {
		super();
		this.userID = userID;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	//getter setter
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public static Connect getCon() {
		return con;
	}

	public static void setCon(Connect con) {
		User.con = con;
	}

	//fungsi untuk melakukan register user baru yang kemudian akan dimasukan kedalam database
	public static int register(String email, String username, String password, String role) {
		String query = "INSERT INTO users(email, username, password, role) VALUES(?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		
		int success = 0;
		
		try {
			ps.setString(1, email);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, role);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	//fungsi untuk melakukan login
	public static User login(String email, String password) {
		String query = "SELECT * FROM users WHERE email = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		User user = null;
		
		try {
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				String user_email = rs.getString("email");
				String username = rs.getString("username");
				String user_password = rs.getString("password");
				String role = rs.getString("role");
				// bila user yang ditemukan adalah admin maka akan dibuat admin baru, jika yang ditemukan guest akan dibuat guest baru, 
				// jika ditemukan event organizer akan dibuat event organizer yang baru, dan jika ditemukan vendor akan dibuat vendor baru
				// ini digunakan untuk menentukan user apa yang sedang menggunakan sistem sehingga dapat menyesuaikan view dan fitur-fitur apa saja yang dapat digunakan user tersebut
				if(role.equals("admin")) {
					user = new Admin(id, user_email, username, user_password, role);
				}
				else if(role.equals("guest")) {
					user = new Guest(id, user_email, username, user_password, role);
				}
				else if(role.equals("event organizer")) {
					user = new EventOrganizer(id, user_email, username, user_password, role);
				}
				else if(role.equals("vendor")) {
					user = new Vendor(id, user_email, username, user_password, role);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	// untuk memvalidasi login bahwa email dan password sama
	public static boolean LoginValidation(String email, String password) {
		String query = "SELECT * FROM users WHERE email = ? and password = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String user_email = rs.getString("email");
				String user_password = rs.getString("password");
				// pengecekan untuk memastikan bahwa email dan password harus sama persis
				if(user_email.equals(email) && user_password.equals(password)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	//untuk validasi input dari register
	public static String checkRegisterInput(String email, String username, String password, String role) {
		// jika email kosong
		if(email.isEmpty()) {
			return "email cannot be empty!";
		}
		// jika username kosong
		else if(username.isEmpty()) {
			return "username cannot be empty!";
		}
		// jika password kosong
		else if(password.isEmpty()) {
			return "password cannot be empty!";
		}
		// jika password kurang dari 5 karakter
		else if(password.length() < 5) {
			return "password length must be greater than 5!";
		}
		// jika role tidak dipilih oleh user
		else if(role.isEmpty()) {
			return "role must be chosen";
		}
		
		// ini logic untuk menemukan apakah user dengan email tersebut sudah ada
		String query = "SELECT * FROM users WHERE email = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return "email already taken";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// ini logic untuk menemukan apakah user dengan username tersebut sudah ada
		query = "SELECT * FROM users WHERE username = ?";
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return "username already taken";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// jika sudah melewati semua validasi
		return "success";
	}
	
	// fungsi untuk mendapatkan suatu user bedasarakan emailnya
	public static User getUserByEmail(String email) {
		String query = "SELECT * FROM users WHERE email = ?";
		PreparedStatement ps = con.prepareStatement(query);
		User user = null;
		
		try {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("userID");
				String user_email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				user = new User(id, user_email, username, password, role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	// fungsi untuk mendapatkan suatu user bedasarakan usernamenya
	public static User getUserByUsername(String name) {
		String query = "SELECT * FROM users WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(query);
		User user = null;
		
		try {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("userID");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				user = new User(id, email, username, password, role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	// fungsi untuk mengubah profile
	public static int changeProfile(User user, String email, String username, String oldPassword, String newPassword) {
		String query;
		
		int success = 0;
		
		// bila email diisi, akan mengupdate emailnya
		if(!email.isEmpty()) {
			query = "UPDATE users SET email = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			try {
				ps.setString(1, email);
				ps.setInt(2, user.getUserID());
				user.setEmail(email);
				success = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		
		// bila username diisi, akan mengupdate usernamenya
		if(!username.isEmpty()) {
			query = "UPDATE users SET username = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			try {
				ps.setString(1, username);
				ps.setInt(2, user.getUserID());
				user.setUsername(username);
				success = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		// jika new password dan old password diisi, akan mengupdate passwordnya
		if(!newPassword.isEmpty() && !oldPassword.isEmpty()) {
			query = "UPDATE users SET password = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			try {
				ps.setString(1, newPassword);
				ps.setInt(2, user.getUserID());
				user.setPassword(newPassword);
				success = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		
		return success;
	}
	
	
	// untuk validasi input dari change profile
	public static String checkChangeProfileInput(User user, String email, String username, String oldPassword, String newPassword) {
		// bila email yang baru sama dengan email yang lama
		if(email.equals(user.getEmail())) {
			return "new email cannot be the same as old email";
		}
		
		// bila username yang baru sama dengan username yang lama
		if(username.equals(user.getUsername())) {
			return "new username cannot be the same as old username";
		}
		
		// bila email tidak kosong, kita akan mengecek apabila email yang dimasuki sudah digunakan atau belum
		if(!email.isEmpty()) {
			String query = "SELECT * FROM users WHERE email = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			try {
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					return "email already taken";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// bila username tidak kosong, kita akan mengecek apabila username yang dimasuki sudah digunakan atau belum
		if(!username.isEmpty()) {
			String query = "SELECT * FROM users WHERE username = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			try {
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					return "username already taken";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// jika old password tidak kosong
		if(!oldPassword.isEmpty()) {
			// check apakah old password yang dimasukan benar
			if(!user.getPassword().equals(oldPassword)) {
				return "old password is wrong";
			}
			// jika old password benar, check apakah, new password diisi
			if(newPassword.isEmpty()) {
				return "to update your password, you must also input the new password";
			}
			// jika new password diisi, check apakah password baru sama dengan password lama
			else {
				if(oldPassword.equals(newPassword)) {
					return "new password cannot be the same as old password";
				}
			}
		}
		
		// jika new password tidak kosong
		if(!newPassword.isEmpty()) {
			// check apakah old password diisi
			if(oldPassword.isEmpty()) {
				return "to update your password, you must also input the old password";
			}
		}
		
		// jika semua field kosong
		if(email.isEmpty() && username.isEmpty() && oldPassword.isEmpty() && newPassword.isEmpty()) {
			return "There is nothing to update";
		}
		
		// jika sudah melewati semua validasi
		return "success";
	}
	
}
