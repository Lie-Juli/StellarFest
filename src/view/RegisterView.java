package view;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
	//component-component yang dibutuhkan
	Scene scene;
	
	private Button loginBtn, RegistBtn;
	private Label usernameLbl, emailLbl, passwordLbl, roleLbl, errorLbl;
	private TextField emailTxt, usernameTxt;
	private PasswordField passwordTxt;
	private ComboBox<String> role_cb;
	private VBox vbox;
	
	private String role[] = {"event organizer", "vendor", "guest"};
	 
	//menginisialisasi komponen dan pembuatan scene
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
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// pembuatan stage yang akan menunjukan register view
	public RegisterView(Stage stage) {
		init();
		setBtnAction(stage);
		stage.setTitle("Register");
		stage.setScene(scene);
		stage.show();
	}
	
	// hal yang dilakukan ketika menekan suatu button
	private void setBtnAction(Stage stage) {
		// jika menekan login button akan menredirect ke login view
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new LoginView(stage);
			}
			
		});
		
		// jika menekan register button akan mendapatkan isi dari textfield email, username, passwordfield password dan combobox role dan menjalanakan fungsi register dari controller
		// jika registrasi berhasil melewati validasi akan ke rediret ke loginview, jika tidak berhasil melewati semua validasi akan muncul error message sesuai errornya
		RegistBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String email = emailTxt.getText();
				String username = usernameTxt.getText();
				String password = passwordTxt.getText();
				String role = role_cb.getValue();
				String message = UserController.register(email, username, password, role);
				if(message.equals("register success")) {
					new LoginView(stage);
				}
				else {
					errorLbl.setText(message);
				}
			}
		});
	}

}
