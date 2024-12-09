package view;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class LoginView{

	Stage stage;
	Scene scene;
	
	Button loginBtn, RegistBtn;
	Label emailLbl, passwordLbl;
	TextField emailTxt;
	PasswordField passwordTxt;
	
	VBox vbox;
	
	public void init() {
		emailLbl = new Label();
		emailLbl.setText("Enter Your Email");
		
		passwordLbl = new Label();
		passwordLbl.setText("Enter Your Password");
		
		emailTxt = new TextField();
		passwordTxt = new PasswordField();
		
		loginBtn = new Button("Login");
		RegistBtn = new Button("Already Have an Account, Register Here!");
		
		vbox = new VBox(10, emailLbl, emailTxt, passwordLbl, passwordTxt, loginBtn, RegistBtn);
		scene = new Scene(vbox, 700, 500);
	}
	
	public LoginView(Stage stage) {
		this.stage = stage;
		init();
		setBtnAction();
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
	}

	private void setBtnAction() {
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String email = emailTxt.getText();
				String password = passwordTxt.getText();
				User user = UserController.login(email, password);
				if(user == null) {
					System.out.println("User Not Found");
				}
				
			}
			
		});
	}

}
