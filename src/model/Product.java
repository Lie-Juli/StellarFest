package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Product {
	// untuk koneksi dengan database
	private static Connect con = Connect.getInstance();
		
	// data-data yang dimiliki product
	private int product_id;
	private String product_name;
	private String product_description;
	private int vendor_id;
	
	//product constructor
	public Product(int product_id, String product_name, String product_description, int vendor_id) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_description = product_description;
		this.vendor_id = vendor_id;
	}

	//getters and setters
	public int getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
	//fungsi untuk memasukan product baru oleh vendor
	public static void insertProduct(int vendorId, String productName, String productDescription) {
		String query = "INSERT INTO products(product_name, product_description, vendor_id) VALUES(?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
			
		try {
			ps.setString(1, productName);
			ps.setString(2, productDescription);
			ps.setInt(3, vendorId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// fungsi untuk melakukan delete product sesuai id
	public static void deleteProduct(int productId) {
		Connect con = Connect.getInstance();
		String query = "DELETE FROM products WHERE product_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setInt(1, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//fungsi untuk menampilkan seluruh product sesuai vendor
	public static ArrayList<Product> getAllProducts(int vendorId) {
		ArrayList<Product> productList = new ArrayList<Product>();
		String query = "SELECT * FROM products WHERE vendor_id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setInt(1, vendorId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int productId = rs.getInt("product_id");
				String productName = rs.getString("product_name");
				String productDescription = rs.getString("product_description");
				int vendor_Id = rs.getInt("vendor_id"); 
				productList.add(new Product(productId, productName, productDescription, vendor_Id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}
		
}
