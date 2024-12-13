package view;

import java.io.Console;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;
import util.Connect;

public class EventView implements EventHandler<ActionEvent>{
	
	// Component declaration yang digunakan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer;
	private VBox vbox;
	private TableView<Event> table;
	private Label titleLabel;
	private Label idLabel;
	private Label errorLabel;
	private TextField idInput;
	private Button deleteBtn;
	private Button viewEventBtn;
	private Button viewUserBtn;
	private Button logoutBtn, changeProfileBtn;
	
	private Connect connect = Connect.getInstance();
	private AdminController adminController = new AdminController();
	
	private User user;
	
	private ArrayList<Event> eventList;
	
	// Inisialisasi component
	public void init() {
		eventList = new ArrayList<Event>();
		
		flowContainer = new FlowPane();
		
		titleLabel = new Label("View Events Page");
		idLabel = new Label("Event Id: ");
		errorLabel = new Label();
		idInput = new TextField();
		
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
		
		table = new TableView<Event>();
		
		vbox = new VBox(10, titleLabel, flowContainer, table,  idLabel, idInput, deleteBtn, errorLabel);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// Tambah button ke navbar
	private void addComponent() {
		flowContainer.getChildren().add(viewEventBtn);
		flowContainer.getChildren().add(viewUserBtn);
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
	}
	
	// Set up table, column, size, dll
	private void setTable() {
		TableColumn<Event, Integer> idColumn = new TableColumn<Event, Integer>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));
		idColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<Event, String> nameColumn = new TableColumn<Event, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
		nameColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<Event, String> dateColumn = new TableColumn<Event, String>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
		dateColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<Event, String> locationColumn = new TableColumn<Event, String>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("location"));
		locationColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<Event, String> descriptionColumn = new TableColumn<Event, String>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("description"));
		descriptionColumn.setMinWidth(vbox.getWidth()/5);
		
		TableColumn<Event, Integer> organizer_idColumn = new TableColumn<Event, Integer>("organizer id");
		organizer_idColumn.setCellValueFactory(new PropertyValueFactory<Event, Integer>("organizerID"));
		organizer_idColumn.setMinWidth(vbox.getWidth()/5);
		
		table.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn, descriptionColumn, organizer_idColumn);
	}
	
	// Method untuk view semua events & refresh table, manggil admin method & tambahkan events ke table
	public void viewAllEvent() {
		ObservableList<Event> events = adminController.viewAllEvent(eventList, connect);
		table.setItems(events);
	}
	
	// Constructor untuk inisialisasi event view page
	public EventView(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
		addComponent();
		setTable();
		viewAllEvent();
		stage.setTitle("Events");
		stage.setScene(scene);
		stage.show();
	}

	
	// Handling events, button-button click listener
	@Override
	public void handle(ActionEvent event) {
		
		if (event.getSource() == deleteBtn) { // Check jika button yang di click adalah delete button
			
			if (idInput.getText().equals("")) { // Check apakah input fieldnya kosong. Jika iya, error label yang sesuai muncul 
				errorLabel.setText("Input vield can't be empty!");
				
			}else { // Jika input field tidak kosong, validasi input & refresh table view.
				String id = idInput.getText();
				
				// Check apakah berhasil dihapus/ input valid. (panggil method di admin controller)
				Boolean valid = adminController.deleteEvent(id, connect);

				if (valid) { // jika valid confirmasi lewat error label
					errorLabel.setText("Data deleted!");
				}else { // jika tidak valid munculkan error label yang sesuai
					errorLabel.setText("Enter a valid id!");
				}
				//refresh table
				viewAllEvent();
				
			}
		}
		else if (event.getSource() == viewEventBtn) { // Jika button yang di click adalah view event button, redirect ke page ini lagi
			new EventView(stage, user);
		}else if (event.getSource() == viewUserBtn) { // Jika button yang di click adalah view user button, redirect ke page user view page
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
