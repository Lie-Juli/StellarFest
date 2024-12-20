package controller;

import java.util.ArrayList;

import model.Product;

public class ProductController {
	//fungsi untuk memasukan product baru oleh vendor
	public static void insertProduct(int vendorId, String productName, String productDescription) {
		Product.insertProduct(vendorId, productName, productDescription);
	}
	
	// fungsi untuk melakukan delete product sesuai id
	public static void deleteProduct(int productId) {
		Product.deleteProduct(productId);
	}
	//fungsi untuk menampilkan seluruh product sesuai vendor
	public static ArrayList<Product> getAllProducts(int vendorId){
		return Product.getAllProducts(vendorId);
	}

}
