package view;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class RegisterView {

	Scene scene;
	
	Button loginBtn, RegistBtn;
	Label usernameLbl, emailLbl, passwordLbl, roleLbl, errorLbl;
	TextField emailTxt, usernameTxt;
	PasswordField passwordTxt;
	ComboBox<String> role_cb;
	VBox vbox;
	
	String role[] = {"event organizer", "vendor", "guest"};
	 
	public void init() {
		emailLbl = new Label();
		emailLbl.setText("Email");
			
		usernameLbl = new Label();
		usernameLbl.setText("Username");
			
		passwordLbl = new Label();
		passwordLbl.setText("Password");
		
		roleLbl = new Label();
		roleLbl.setText("Role");
			
		errorLbl = new Label();
			
		emailTxt = new TextField();
		usernameTxt = new TextField();
		passwordTxt = new PasswordField();
		
		role_cb = new ComboBox<>(FXCollections.observableArrayList(role));
			
		loginBtn = new Button("Already Have an Account, Login Here!");
		RegistBtn = new Button("Register");
			
		vbox = new VBox(10, emailLbl, emailTxt, usernameLbl, usernameTxt, passwordLbl, passwordTxt, roleLbl, role_cb, RegistBtn, loginBtn, errorLbl);
		scene = new Scene(vbox, 700, 500);
	}
	
	public RegisterView(Stage stage) {
		init();
		setBtnAction(stage);
		stage.setTitle("Register");
		stage.setScene(scene);
		stage.show();
	}
	
	private void setBtnAction(Stage stage) {
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new LoginView(stage);
			}
			
		});
		
		RegistBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new RegisterView(stage);
				
			}
		});
	}

}
