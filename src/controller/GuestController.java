package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Event;
import model.Guest;

public class GuestController {
    // View accepted events
    public ObservableList<Event> viewAcceptedEvents(int userId) {
        return FXCollections.observableArrayList(Guest.viewAcceptedEvents(userId));
    }

    // Accept an invitation
    public void acceptInvitation(int invitationId) {
        Guest.acceptInvitation(invitationId);
    }
}
