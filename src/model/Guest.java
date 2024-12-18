package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Guest extends User {
	private static Connect con = getCon();
	
    public Guest(int userID, String email, String username, String password, String role) {
        super(userID, email, username, password, role);
    }

    // Fetch pending invitations
    public static ArrayList<Invitation> viewPendingInvitations(int userId) {
        ArrayList<Invitation> invitations = new ArrayList<>();
        String query = "SELECT * FROM invitations WHERE user_id = ? AND invitation_status = 'not accepted'";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                invitations.add(new Invitation(
                        rs.getInt("invitation_id"),
                        rs.getInt("event_id"),
                        rs.getInt("user_id"),
                        rs.getString("invitation_status"),
                        rs.getString("invitation_role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invitations;
    }

    // Fetch accepted events
    public static ArrayList<Event> viewAcceptedEvents(int userId) {
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT e.* FROM events e INNER JOIN invitations i " +
                       "ON e.event_id = i.event_id WHERE i.user_id = ? AND i.invitation_status = 'accepted'";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                events.add(new Event(
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
        return events;
    }

    // Accept invitation
    public static void acceptInvitation(int invitationId) {
        String query = "UPDATE invitations SET invitation_status = 'accepted' WHERE invitation_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, invitationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
