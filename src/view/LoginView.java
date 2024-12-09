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
	
	//component-component yang dibutuhkan
	Stage stage;
	Scene scene;
	
	Button loginBtn, RegistBtn;
	Label emailLbl, passwordLbl, errorLbl;
	TextField emailTxt;
	PasswordField passwordTxt;
	
	VBox vbox;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		emailLbl = new Label();
		emailLbl.setText("Email");
		
		passwordLbl = new Label();
		passwordLbl.setText("Password");
		
		errorLbl = new Label();
		
		emailTxt = new TextField();
		passwordTxt = new PasswordField();
		
		loginBtn = new Button("Login");
		RegistBtn = new Button("Don't Have an Account, Register Here!");
		
		vbox = new VBox(10, emailLbl, emailTxt, passwordLbl, passwordTxt, loginBtn, RegistBtn, errorLbl);
		scene = new Scene(vbox, 700, 500);
	}
	
	// pembuatan stage yang akan menunjukan login view
	public LoginView(Stage stage) {
		this.stage = stage;
		init();
		setBtnAction();
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
	}

	// hal yang dilakukan ketika menekan suatu button
	private void setBtnAction() {
		// jika menekan login button akan mendapatkan isi dari textfield email dan passwordfield password dan menjalanakan fungsi login dari controller, 
		// jika user tidak ada akan muncul error messeage
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String email = emailTxt.getText();
				String password = passwordTxt.getText();
				if(email.isEmpty()) {
					errorLbl.setText("Please fill your Email");
				}
				else if(password.isEmpty()) {
					errorLbl.setText("Please fill your password");
				}
				else {
					User user = UserController.login(email, password);
					if(user == null) {
						errorLbl.setText("User Not Found");
					}
					else {
						errorLbl.setText("User Found");
					}
				}
			}
			
		});
		
		// jika menekan regsiter button akan menredirect ke register view
		RegistBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new RegisterView(stage);
				
			}
		});
		
	}

}
