package view;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class ChangeProfileView implements EventHandler<ActionEvent>{
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
		
	private Label usernameLbl, emailLbl, oldPasswordLbl, newPasswordLbl, errorLbl;
	private TextField emailTxt, usernameTxt;
	private PasswordField oldPasswordTxt, newPasswordTxt;
	private FlowPane flowContainer;
	private VBox vbox;
	private Button changeProfileBtn, changeProfileBtnPage, logoutBtn, viewOrganizedEventBtn, createEventPageBtn, viewEventBtn, viewUserBtn, viewInvitationsBtn, viewAcceptedEventsBtn;
	private User user = null;
		
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		
		emailLbl = new Label();
		emailLbl.setText("Email");
				
		usernameLbl = new Label();
		usernameLbl.setText("Username");
				
		oldPasswordLbl = new Label();
		oldPasswordLbl.setText("Old Password");
			
		newPasswordLbl = new Label();
		newPasswordLbl.setText("New Password");
			
		errorLbl = new Label();
		
		emailTxt = new TextField();
		usernameTxt = new TextField();
		oldPasswordTxt = new PasswordField();
		newPasswordTxt = new PasswordField();
		
		changeProfileBtn = new Button("Change");
		changeProfileBtn.setOnAction(this);
		changeProfileBtnPage = new Button("Change Profile");
		changeProfileBtnPage.setOnAction(this);
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		viewOrganizedEventBtn = new Button("Organized Events");
		viewOrganizedEventBtn.setOnAction(this);
		createEventPageBtn = new Button("Create Event");
		createEventPageBtn.setOnAction(this);
		viewEventBtn = new Button("View Events");
		viewEventBtn.setOnAction(this);
		viewUserBtn = new Button("View Users");
		viewUserBtn.setOnAction(this);
		viewInvitationsBtn = new Button("View Invitations");
		viewInvitationsBtn.setOnAction(this);
		viewAcceptedEventsBtn = new Button("View Events");
		viewAcceptedEventsBtn.setOnAction(this);
		
		vbox = new VBox(10, flowContainer, emailLbl, emailTxt, usernameLbl, usernameTxt, oldPasswordLbl, oldPasswordTxt, newPasswordLbl, newPasswordTxt, changeProfileBtn, errorLbl);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// Tambah button ke navbar
	private void addComponent() {
		// menambahkan button sesuai role user
		if(user.getRole().equals("event organizer")) {
			flowContainer.getChildren().add(viewOrganizedEventBtn);
			flowContainer.getChildren().add(createEventPageBtn);
		}
		if(user.getRole().equals("admin")) {
			flowContainer.getChildren().add(viewEventBtn);
			flowContainer.getChildren().add(viewUserBtn);
		}
		if(user.getRole().equals("guest")) {
			flowContainer.getChildren().add(viewInvitationsBtn);
			flowContainer.getChildren().add(viewAcceptedEventsBtn);
		}
		flowContainer.getChildren().add(changeProfileBtnPage);
		flowContainer.getChildren().add(logoutBtn);
	}
	
	// pembuatan stage yang akan menunjukan change profile view
	public ChangeProfileView(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
		addComponent();
		stage.setTitle("Change Profile");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void handle(ActionEvent event) {
		// jika menekan tombol createEvent akan menredirect ke createEvent view
		if(event.getSource() == createEventPageBtn) {
			new CreateEventView(stage, user);
		}
		// jika menekan tombol view Organized Event akan menredirect ke viewOrganizedEvents view
		else if(event.getSource() == viewOrganizedEventBtn) {
			new ViewOrganizedEventsView(stage, user);
		}
		else if (event.getSource() == viewEventBtn) { // Jika button yang di click adalah view event button, redirect ke page ini lagi
			new EventView(stage, user);
		}else if (event.getSource() == viewUserBtn) { // Jika button yang di click adalah view user button, redirect ke page user view page
			new UserView(stage, user);
		}
		// jika menekan tombol View Events akan menredirect ke ViewAcceptedEvents
		if(event.getSource() == viewAcceptedEventsBtn) {
			new ViewAcceptedEvents(stage, user);
		}
		// jika menekan tombol View Invitations akan menredirect ke ViewInvitations
		else if(event.getSource() == viewInvitationsBtn) {
			new ViewInvitations(stage, user);
		}
		// jika menekan tombol changeprofle akan menredirect ke changeProfile view
		else if(event.getSource() == changeProfileBtnPage) {
			new ChangeProfileView(stage, user);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
		
		// jika menekan tombol change akan mendapatkan semua value dari text field dan menjalankan function changeprofile
		else if(event.getSource() == changeProfileBtn) {
			String email = emailTxt.getText();
			String username = usernameTxt.getText();
			String oldPassword = oldPasswordTxt.getText();
			String newPassword = newPasswordTxt.getText();
			String message = UserController.changeProfile(user, email, username, oldPassword, newPassword);
			errorLbl.setText(message);
		}
		
	}
	
}
