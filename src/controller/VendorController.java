package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Event;
import model.Vendor;

public class VendorController {
	 // View accepted events
    public static ObservableList<Event> viewAcceptedEvents(int userId) {
        return FXCollections.observableArrayList(Vendor.viewAcceptedEvents(userId));
    }

    // Accept an invitation
    public static void acceptInvitation(int invitationId) {
        Vendor.acceptInvitation(invitationId);
    }
    
  //fungsi untuk memasukan product baru oleh vendor
  	public static String insertProduct(int vendorId, String productName, String productDescription){
  		String message = checkManageProduct(productName, productDescription);
  		
  		if(message.equals("Add Product Success")) {
  			Vendor.insertProduct(vendorId, productName, productDescription);
  		}
  		
  		return message;
  	}
    
	// untuk mengecek apakah input untuk memasukan product sudah benar atau tidak
  	public static String checkManageProduct(String productName, String productDescription) {
  		return Vendor.checkManageProduct(productName, productDescription);
  	}
}
