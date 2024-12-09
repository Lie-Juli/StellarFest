package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connect;

public class User {
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
				//bila user yang ditemukan adalah admin maka akan dibuat admin baru, jika yang ditemukan guest akan dibuat guest baru, 
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
	
	//untuk validasi input dari register
	public static String checkRegisterInput(String email, String username, String password, String role) {
		// jika email kosong
		if(email == null) {
			return "email cannot be empty!";
		}
		// jika username kosong
		else if(username == null) {
			return "username cannot be empty!";
		}
		// jika password kosong
		else if(password == null) {
			return "password cannot be empty!";
		}
		// jika password kurang dari 5 karakter
		else if(password.length() < 5) {
			return "password length must be greater than 5!";
		}
		// jika role tidak dipilih oleh user
		if(role == null) {
			return "role must be chosen";
		}
		
		
		// ini logic untuk menemukan apakah user dengan email atau username tersebut sudah ada
		String query = "SELECT * FROM users WHERE email = ? or username = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, email);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return "email already taken or username already taken";
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
	
}
