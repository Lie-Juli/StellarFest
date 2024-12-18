package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Event;
import model.Guest;
import model.Invitation;

public class GuestController {
    // View pending invitations
    public ObservableList<Invitation> viewPendingInvitations(int userId) {
        return FXCollections.observableArrayList(Guest.viewPendingInvitations(userId));
    }

    // View accepted events
    public ObservableList<Event> viewAcceptedEvents(int userId) {
        return FXCollections.observableArrayList(Guest.viewAcceptedEvents(userId));
    }

    // Accept an invitation
    public void acceptInvitation(int invitationId) {
        Guest.acceptInvitation(invitationId);
    }
}
