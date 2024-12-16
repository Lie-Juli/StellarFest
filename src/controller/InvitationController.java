package controller;

import model.Invitation;

public class InvitationController {
	// untuk memanggil function sendInvitation dari model Invitation yang akan membuat invitation
	public static void sendInvitation(int event_id, int user_id, String role) {
		Invitation.sendInvitation(event_id, user_id, role);
	}
}
