package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Invitation {
	// untuk koneksi dengan database
	private static Connect con = Connect.getInstance();
	
	// data-data apa saja yang akan dimiliki invitation
	private int invitation_id;
	private int event_id;
	private int user_id;
	private String invitation_status;
	private String invitation_role;
	
	public Invitation(int invitation_id, int event_id, int user_id, String invitation_status, String invitation_role) {
		super();
		this.invitation_id = invitation_id;
		this.event_id = event_id;
		this.user_id = user_id;
		this.invitation_status = invitation_status;
		this.invitation_role = invitation_role;
	}
	
	//getter setter
	public int getInvitation_id() {
		return invitation_id;
	}

	public void setInvitation_id(int invitation_id) {
		this.invitation_id = invitation_id;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getInvitation_status() {
		return invitation_status;
	}

	public void setInvitation_status(String invitation_status) {
		this.invitation_status = invitation_status;
	}

	public String getInvitation_role() {
		return invitation_role;
	}

	public void setInvitation_role(String invitation_role) {
		this.invitation_role = invitation_role;
	}
	
	// fungsi untuk membuat invitation baru dan kemudian memasukannya ke dalam database
	public static void sendInvitation(int event_id, int user_id, String role) {
		String query = "INSERT INTO invitations(event_id, user_id, invitation_status, invitation_role) VALUES(?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, event_id);
			ps.setInt(2, user_id);
			ps.setString(3, "not accepted");
			ps.setString(4, role);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
}
