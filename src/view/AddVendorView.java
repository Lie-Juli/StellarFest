package view;

import controller.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;

public class AddVendorView implements EventHandler<ActionEvent> {
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer;
	private VBox vbox;
	private TableView<User> table;
	private Label errorLbl, titleLbl;
	private Button viewOrganizedEventBtn, createEventPageBtn, addVendorBtn;
	private Button logoutBtn, changeProfileBtn;
	
	private User organizer = null;
	private Event eventSelected = null;
	private ObservableList<User> usersSelected;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		
		errorLbl = new Label();
		titleLbl = new Label();
		titleLbl.setText("Event ID: " + eventSelected.getId() + " \tEvent Name: " + eventSelected.getName());
		
		table = new TableView<User>();
		table.setOnMouseClicked(tableMouseEvent());
		
		viewOrganizedEventBtn = new Button("Organized Events");
		viewOrganizedEventBtn.setOnAction(this);
		
		createEventPageBtn = new Button("Create Event");
		createEventPageBtn.setOnAction(this);
		
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		
		addVendorBtn = new Button("Add Vendor");
		addVendorBtn.setOnAction(this);
		
		vbox = new VBox(10, flowContainer, titleLbl, table, addVendorBtn, errorLbl);
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita dimana flowpane ini akan bekerja sebagai navbar
	private void addComponent() {
		flowContainer.getChildren().add(viewOrganizedEventBtn);
		flowContainer.getChildren().add(createEventPageBtn);
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
	}
	
	// untuk membuat layout table
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
		
		TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
		roleColumn.setMinWidth(vbox.getWidth()/5);
		
		table.getColumns().addAll(idColumn, emailColumn, usernameColumn, roleColumn);
	}
	
	// untuk menrefresh data di table dan menambahkan data terbaru agar sesuai database selalu up to date
	public void refreshTable() {
		ObservableList<User> vendors = FXCollections.observableArrayList(EventOrganizerController.getVendor(eventSelected.getId()));
		table.setItems(vendors);
	}
	
	// pembuatan stage yang akan menunjukan addVendor view
	public AddVendorView(Stage stage, User user, Event event) {
		this.stage = stage;
		this.organizer = user;
		this.eventSelected = event;
		init();
		addComponent();
		setTable();
		refreshTable();
		stage.setTitle("Add Vendor");
		stage.setScene(scene);
		stage.show();
	}
	
	// untuk mendapatkan data beberapa users dari table hanya dengan click menggunakan mouse
	private EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>() {
			// untuk memilih beberapa user gunakan ctrl + click
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<User> tsm = table.getSelectionModel();
				tsm.setSelectionMode(SelectionMode.MULTIPLE);
				usersSelected = tsm.getSelectedItems();
			}
		};
	}

	// hal yang dilakukan ketika menekan suatu button
	@Override
	public void handle(ActionEvent event) {
		// jika menekan tombol createEvent akan menredirect ke createEvent view
		if(event.getSource() == createEventPageBtn) {
			new CreateEventView(stage, organizer);
		}
		// jika menekan tombol view Organized Event akan menredirect ke viewOrganizedEvents view
		else if(event.getSource() == viewOrganizedEventBtn) {
			new ViewOrganizedEventsView(stage, organizer);
		}
		// jika menekan tombol changeprofle akan menredirect ke changeProfile view
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, organizer);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
		// jika menekan add vendor akan mendapatkan vendors yang dipilih oleh event organizer kemudian menjalankan validasi, 
		// jika berhasil melewati validasi maka akan membuat invitation baru kepada semua vendors yang dipilih
		else if(event.getSource() == addVendorBtn) {
			String message = EventOrganizerController.addVendor(usersSelected, eventSelected.getId());
			errorLbl.setText(message);
			refreshTable();
		}
		
	}
}
