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
}
