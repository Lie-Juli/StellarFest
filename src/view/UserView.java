package view;

import java.util.ArrayList;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;
import util.Connect;

public class UserView implements EventHandler<ActionEvent>{

	// Component declaration yang digunakan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer;
	private VBox vbox;
	private TableView<User> table;
	private Label titleLabel;
	private Label errorLabel;
	private Button deleteBtn;
	private Button viewEventBtn;
	private Button viewUserBtn;
	private Button logoutBtn, changeProfileBtn;
	
	private Connect connect = Connect.getInstance();
	private AdminController adminController = new AdminController();
	
	private User user;
	private User userSelected = null;
	private ArrayList<User> userList;
	private int tempId;
	
	// Inisialisasi component
	public void init() {
		userList = new ArrayList<User>();
		
		flowContainer = new FlowPane();
		titleLabel = new Label("View Users Page");
		errorLabel = new Label();

		deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(this);
		viewEventBtn = new Button("View Events");
		viewEventBtn.setOnAction(this);
		viewUserBtn = new Button("View Users");
		viewUserBtn.setOnAction(this);
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		
		table = new TableView<User>();
		table.setOnMouseClicked(tableMouseEvent());
		
		vbox = new VBox(10, titleLabel, flowContainer, table, deleteBtn, errorLabel);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// Tambah button ke navbar
	private void addComponent() {
		flowContainer.getChildren().add(viewUserBtn);
		flowContainer.getChildren().add(viewEventBtn);
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
	}
	
	// Set up table, column, size, dll
	private void setTable() {
		TableColumn<User, Integer> idColumn = new TableColumn<User, Integer>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
		idColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		emailColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<User, String> usernameColumn = new TableColumn<User, String>("Username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		usernameColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<User, String> passwordColumn = new TableColumn<User, String>("Password");
		passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		passwordColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
		roleColumn.setMinWidth(vbox.getWidth()/5);
		
		table.getColumns().addAll(idColumn, emailColumn, usernameColumn, passwordColumn, roleColumn);
	}
	
	// Method untuk view semua users & refresh table, manggil admin method & tambahkan users ke table
	public void viewAllUser() {
		ObservableList<User> users = adminController.viewAllUser(userList, connect);
		table.setItems(users);
	}
	
	// Constructor untuk inisialisasi users view page
	public UserView(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
		addComponent();
		setTable();
		viewAllUser();
		stage.setTitle("Users");
		stage.setScene(scene);
		stage.show();
	}
	
	// untuk mendapatkan data users dari table hanya dengan click menggunakan mouse
		private EventHandler<MouseEvent> tableMouseEvent(){
			return new EventHandler<MouseEvent>() {
				// untuk memilih beberapa user gunakan ctrl + click
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					TableSelectionModel<User> tsm = table.getSelectionModel();
					tsm.setSelectionMode(SelectionMode.SINGLE);
					userSelected = tsm.getSelectedItem();
					if(userSelected != null) {
						tempId = userSelected.getUserID();
					}
				}
			};
		}

	// Handling events, button-button click listener
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == deleteBtn) { // Check jika button yang di click adalah delete button
			if (userSelected == null) { // Check apakah user dipilih. Jika tidak, error label yang sesuai muncul 
				errorLabel.setText("Please choose a user to delete!");
			}else { // Jika user dipilih, validasi input & refresh table view.
				// Check apakah berhasil dihapus/ input valid. (panggil method di admin controller)
				Boolean valid = adminController.deleteUser(tempId, connect);

				if (valid) { // jika valid confirmasi lewat error label
					errorLabel.setText("Data deleted!");
				}else { // jika tidak valid munculkan error label yang sesuai
					errorLabel.setText("Failed!");
				}
				//refresh table
				viewAllUser();
			}
		}
		else if (event.getSource() == viewEventBtn) { // Jika button yang di click adalah view event button, redirect ke page event view page
			new EventView(stage, user);
		}else if (event.getSource() == viewUserBtn) { // Jika button yang di click adalah view user button, redirect ke page ini lagi
			new UserView(stage, user);
		}
		// jika menekan tombol changeprofle akan menredirect ke changeProfile view
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, user);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
	}
}
