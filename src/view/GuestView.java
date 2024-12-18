package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class GuestView implements EventHandler<ActionEvent> {
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer;
	private VBox vbox;
	private Button viewInvitationsBtn, viewAcceptedEventsBtn, changeProfileBtn;
	private Button logoutBtn;
	
	private User guest = null;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		
		viewInvitationsBtn = new Button("View Invitations");
		viewInvitationsBtn.setOnAction(this);
		
		viewAcceptedEventsBtn = new Button("View Events");
		viewAcceptedEventsBtn.setOnAction(this);
		
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		
		
		vbox = new VBox(10, flowContainer);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita, dimana flowpane ini akan bekerja sebagai navbar(flowContainer)
	private void addComponent() {
		flowContainer.getChildren().add(viewInvitationsBtn);
		flowContainer.getChildren().add(viewAcceptedEventsBtn);
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
	}
	
	// pembuatan stage
	public GuestView(Stage stage, User user) {
		this.stage = stage;
		guest = user;
		init();
		addComponent();
		stage.setTitle("Guest View");
		stage.setScene(scene);
		stage.show();
	}

	// hal yang dilakukan ketika menekan suatu button
	@Override
	public void handle(ActionEvent event) {
		// jika menekan tombol View Events akan menredirect ke ViewAcceptedEvents
		if(event.getSource() == viewAcceptedEventsBtn) {
			new ViewAcceptedEvents(stage, guest);
		}
		// jika menekan tombol View Invitations akan menredirect ke ViewInvitations
		else if(event.getSource() == viewInvitationsBtn) {
			new ViewInvitations(stage, guest);
		}
		// jika menekan tombol Change Profile akan menredirect ke ChangeProfileView
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, guest);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
	}
}