package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.InvitationController;
import util.Connect;

public class Vendor extends User{
	private static Connect con = Connect.getInstance();

	public Vendor(int userID, String email, String username, String password, String role) {
		super(userID, email, username, password, role);
		// TODO Auto-generated constructor stub
	}

	private static ArrayList<Event> acceptedInvitation = new ArrayList<>();
	
	// Fetch accepted events
    public static ArrayList<Event> viewAcceptedEvents(int userId) {
        acceptedInvitation.clear();
        String query = "SELECT e.* FROM events e INNER JOIN invitations i " +
                       "ON e.event_id = i.event_id WHERE i.user_id = ? AND i.invitation_status = 'accepted'";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	acceptedInvitation.add(new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("event_date"),
                        rs.getString("event_location"),
                        rs.getString("event_description"),
                        rs.getInt("organizer_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acceptedInvitation;
    }
    
    // Accept invitation
    public static void acceptInvitation(int invitationId) {
       InvitationController.acceptInvitation(invitationId);
    }

}
