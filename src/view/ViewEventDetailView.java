package view;

import controller.EventController;
import controller.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;

public class ViewEventDetailView implements EventHandler<ActionEvent>{
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
		
	private FlowPane flowContainer;
	private GridPane grid;
	private Label event_idLbl, event_nameLbl, event_dateLbl, event_locationLbl, event_descriptionLbl, event_descriptionContentLbl, event_organizedIdLbl, vendorListLbl, guestListLbl;
	private Button changeProfileBtn, logoutBtn, viewOrganizedEventBtn, createEventPageBtn, viewEventBtn, viewUserBtn;
	private TableView<User> vendorTable, guestTable;
	private VBox vbox;
		
	private User user = null;
	private int event_id;
	private Event eventSelected = null;
	private User organizer = null;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		grid = new GridPane();
			
		event_idLbl = new Label();
		event_idLbl.setText("Event ID: " + event_id);
			
		event_nameLbl = new Label();
		event_nameLbl.setText("Event Name: " + eventSelected.getName());
			
		event_dateLbl = new Label();
		event_dateLbl.setText("Event Date: " + eventSelected.getDate());
			
		event_locationLbl = new Label();
		event_locationLbl.setText("Event Location: " + eventSelected.getLocation());
			
		event_organizedIdLbl = new Label();
		// untuk mendapatkan data email dan username organizernya 
		organizer = EventOrganizerController.getOrganizerById(eventSelected.getOrganizerID()); 
		event_organizedIdLbl.setText("Event Organizer: " + organizer.getUsername() + " (" + organizer.getEmail() + ")");
			
		event_descriptionLbl = new Label();
		event_descriptionLbl.setText("Event Description: ");
		
		event_descriptionContentLbl = new Label();
		event_descriptionContentLbl.setText(eventSelected.getDescription());
		
		event_descriptionContentLbl.setPadding(new Insets(0, 0, 5, 0));
			
		vendorListLbl = new Label();
		vendorListLbl.setText("Vendor attending:");
		
		guestListLbl = new Label();
		guestListLbl.setText("Guest attending:");
		
		vendorTable = new TableView<User>();
		guestTable = new TableView<User>();
		
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
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
		
		vbox = new VBox(10, flowContainer, event_idLbl, event_nameLbl, event_dateLbl, event_locationLbl, event_organizedIdLbl, event_descriptionLbl, event_descriptionContentLbl, grid);
		vbox.setPadding(new Insets(10)); 
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita, dimana flowpane ini akan bekerja sebagai navbar
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
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
		
		grid.add(guestListLbl, 0, 0);
		grid.add(vendorListLbl, 1, 0);
		grid.add(vendorTable, 1, 1);
		grid.add(guestTable, 0, 1);
		grid.setHgap(15);
	}
	
	// untuk membuat layout table
	private void setTable() {
		TableColumn<User, Integer> guestIdColumn = new TableColumn<User, Integer>("Id");
		guestIdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
		
		TableColumn<User, Integer> vendorIdColumn = new TableColumn<User, Integer>("Id");
		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
		
		TableColumn<User, String> guestEmailColumn = new TableColumn<User, String>("Email");
		guestEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		
		TableColumn<User, String> vendorEmailColumn = new TableColumn<User, String>("Email");
		vendorEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		
		TableColumn<User, String> guestUsernameColumn = new TableColumn<User, String>("Username");
		guestUsernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		
		TableColumn<User, String> vendorUsernameColumn = new TableColumn<User, String>("Username");
		vendorUsernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		
		guestTable.getColumns().addAll(guestIdColumn, guestEmailColumn, guestUsernameColumn);
		vendorTable.getColumns().addAll(vendorIdColumn, vendorEmailColumn, vendorUsernameColumn);
	}
	
	// untuk menrefresh data di table dan menambahkan data terbaru agar sesuai database selalu up to date
	public void refreshTable() {
		ObservableList<User> guests = FXCollections.observableArrayList(EventOrganizerController.getGuestByTransactionID(event_id));
		guestTable.setItems(guests);
 		ObservableList<User> vendors = FXCollections.observableArrayList(EventOrganizerController.getVendorByTransactionID(event_id));
 		vendorTable.setItems(vendors);
	}
	
	// pembuatan stage yang akan menunjukan viewEventDetail view
	public ViewEventDetailView(Stage stage, User user, int event_id) {
		this.stage = stage;
		this.user = user;
		this.event_id = event_id;
		eventSelected = EventController.viewEventDetail(event_id);
		init();
		addComponent();
		setTable();
		refreshTable();
		stage.setTitle("Event Detail");
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
		// jika menekan tombol changeprofle akan menredirect ke changeProfile view
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, user);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
	}
}
