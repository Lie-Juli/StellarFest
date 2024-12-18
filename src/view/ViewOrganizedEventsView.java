package view;

import controller.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;

public class ViewOrganizedEventsView implements EventHandler<ActionEvent>{
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer, flowContainerBot;
	private FlowPane flowContainerName;
	private VBox vbox;
	private TableView<Event> table;
	private Label editNameLbl, errorLbl;
	private Label editEventNameLbl;
	private TextField editEventNameTf;
	private Button viewOrganizedEventBtn, createEventPageBtn, addGuestBtn, addVendorBtn, editNameBtn, DetailsBtn;
	private Button logoutBtn, changeProfileBtn;
	
	private User organizer = null;
	private EventOrganizerController eventOrganizerController = new EventOrganizerController();
	private int tempId;
	private Event eventSelected = null;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		flowContainerBot = new FlowPane();
		flowContainerName = new FlowPane();
		
		editNameLbl = new Label();
		editNameLbl.setText("Edit Name");
		editEventNameLbl = new Label("Event's new name :   ");
		
		errorLbl = new Label();
		
		editEventNameTf = new TextField();
		 
		table = new TableView<Event>();
		table.setOnMouseClicked(tableMouseEvent());
		
		viewOrganizedEventBtn = new Button("Organized Events");
		viewOrganizedEventBtn.setOnAction(this);
		
		createEventPageBtn = new Button("Create Event");
		createEventPageBtn.setOnAction(this);
		
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		
		editNameBtn = new Button("Edit Event Name");
		editNameBtn.setOnAction(this);
		
		addGuestBtn = new Button("Add Guest");
		addGuestBtn.setOnAction(this);
		addVendorBtn = new Button("Add Vendor");
		addVendorBtn.setOnAction(this);
		DetailsBtn = new Button("Details");
		DetailsBtn.setOnAction(this);
		
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		
		
		vbox = new VBox(10, flowContainer, table, editNameLbl, flowContainerName, editNameBtn, errorLbl, flowContainerBot);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita, dimana flowpane ini akan bekerja sebagai navbar(flowContainer) dan footer(flowContainerBot)
	private void addComponent() {
		flowContainer.getChildren().add(viewOrganizedEventBtn);
		flowContainer.getChildren().add(createEventPageBtn);
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
		flowContainerBot.getChildren().add(addGuestBtn);
		flowContainerBot.getChildren().add(addVendorBtn);
		flowContainerBot.getChildren().add(DetailsBtn);
		
		flowContainerName.getChildren().add(editEventNameLbl);
		
		flowContainerName.getChildren().add(editEventNameTf);
	}
	
	// untuk membuat layout table
	private void setTable() {
		TableColumn<Event, Integer> idColumn = new TableColumn<Event, Integer>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));
		idColumn.setMinWidth(vbox.getWidth()/4);
		
		TableColumn<Event, String> nameColumn = new TableColumn<Event, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
		nameColumn.setMinWidth(vbox.getWidth()/4);
		
		TableColumn<Event, String> dateColumn = new TableColumn<Event, String>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
		dateColumn.setMinWidth(vbox.getWidth()/4);
		
		TableColumn<Event, String> locationColumn = new TableColumn<Event, String>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("location"));
		locationColumn.setMinWidth(vbox.getWidth()/4);
		
		table.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn);
	}
	
	// untuk menrefresh data di table dan menambahkan data terbaru agar sesuai database selalu up to date
	public void refreshTable() {
		ObservableList<Event> events = FXCollections.observableArrayList(EventOrganizerController.viewOrganizedEvents(organizer.getUserID()));
		table.setItems(events);
	}
	
	
	// pembuatan stage yang akan menunjukan viewOrganizedEvents view
	public ViewOrganizedEventsView(Stage stage, User user) {
		this.stage = stage;
		organizer = user;
		init();
		addComponent();
		setTable();
		refreshTable();
		stage.setTitle("Organized Events");
		stage.setScene(scene);
		stage.show();
	}
	
	// untuk mendapatkan data suatu event dari table hanya dengan click menggunakan mouse
	private EventHandler<MouseEvent> tableMouseEvent(){
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Event> tsm = table.getSelectionModel();
				tsm.setSelectionMode(SelectionMode.SINGLE);
				eventSelected = tsm.getSelectedItem();
				if(eventSelected != null) {
					editEventNameTf.setText(eventSelected.getName());
					tempId = eventSelected.getId();
				}
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
		
		else if (event.getSource() == editNameBtn) { // Melakukan validasi dan query ketika edit name button ditekan
			String newEventName = editEventNameTf.getText();
			
			// Check apakah berhasil diedit/ input valid. (panggil method di event organizer controller)
			boolean valid = eventOrganizerController.editEventName(tempId, newEventName);
			
			if(eventSelected == null) {
				errorLbl.setText("Please Choose an Event to edit name");
			}
			else if (valid) {
				errorLbl.setText("Name Changed.");
				refreshTable();
			}else {
				errorLbl.setText("ERROR. Please input a New Event Name");
			}
			
			refreshTable();
		}
		// jika menekan tombol addGuest akan mengecek apakah ada event yang terpilih, jika iya baru akan menredirect ke AddGuest View
		else if(event.getSource() == addGuestBtn) {
			if(eventSelected == null) {
				errorLbl.setText("Please Choose an Event to add guest");
			}
			else {
				new AddGuestView(stage, organizer, eventSelected);
			}
		}
		// jika menekan tombol addVendor akan mengecek apakah ada event yang terpilih, jika iya baru akan menredirect ke AddVendor View
		else if(event.getSource() == addVendorBtn) {
			if(eventSelected == null) {
				errorLbl.setText("Please Choose an Event to add vendor");
			}
			else {
				new AddVendorView(stage, organizer, eventSelected);
			}
		}
		// jika menekan tombol detail akan mengecek apakah ada event yang terpilih, jika iya baru mendredirect ke Detail view
		else if(event.getSource() == DetailsBtn) {
			if(eventSelected == null) {
				errorLbl.setText("Please Choose an event to view the details");
			}
			else {
				new ViewEventDetailView(stage, organizer, tempId);
			}
		}
	}
	
	
}
