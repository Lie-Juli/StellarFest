package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Guest;
import model.Invitation;

public class InvitationController {
	// untuk memanggil function sendInvitation dari model Invitation yang akan membuat invitation
	public static void sendInvitation(int event_id, int user_id, String role) {
		Invitation.sendInvitation(event_id, user_id, role);
	}
	
	
	// untuk memanggil function acceptInvitation dari model Invitation yang akan menerima invitation
	public static void acceptInvitation(int invitationId) {
		Invitation.acceptInvitation(invitationId);
	}
	
	// untuk memanggil function viewPendingInvitations dari model Invitation yang akan mendapatkan semua invitation yang belum di accept
	 public static ObservableList<Invitation> viewPendingInvitations(int userId){
		 return FXCollections.observableArrayList(Invitation.viewPendingInvitations(userId));
	 }
}
