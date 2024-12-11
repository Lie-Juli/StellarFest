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
	private FlowPane flowContainerId;
	private FlowPane flowContainerName;
	private VBox vbox;
	private TableView<Event> table;
	private Label editNameLbl, errorLbl;
	private Label editEventIdLbl, editEventNameLbl;
	private TextField editEventIdTf, editEventNameTf;
	private Button viewOrganizedEventBtn, createEventPageBtn, addGuestBtn, addVendorBtn, editNameBtn, DetailsBtn;
	private Button logoutBtn;
	
	private User organizer = null;
	private EventOrganizerController eventOrganizerController = new EventOrganizerController(); 
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		flowContainerBot = new FlowPane();
		flowContainerId = new FlowPane();
		flowContainerName = new FlowPane();
		
		editNameLbl = new Label();
		editNameLbl.setText("Edit Name");
		editEventIdLbl = new Label("Event's id to edit   :   ");
		editEventNameLbl = new Label("Event's new name :   ");
		
		errorLbl = new Label();
		
		editEventIdTf = new TextField();
		editEventNameTf = new TextField();
		 
		table = new TableView<Event>();
		
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
		
		
		vbox = new VBox(10, flowContainer, table, editNameLbl, flowContainerId, flowContainerName, editNameBtn, errorLbl, flowContainerBot);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita dimana flowpane ini akan bekerja sebagai navbar(flowContainer) dan footer(flowContainerBot)
	private void addComponent() {
		flowContainer.getChildren().add(viewOrganizedEventBtn);
		flowContainer.getChildren().add(createEventPageBtn);
		flowContainer.getChildren().add(logoutBtn);
		flowContainerBot.getChildren().add(addGuestBtn);
		flowContainerBot.getChildren().add(addVendorBtn);
		flowContainerBot.getChildren().add(DetailsBtn);
		
		flowContainerId.getChildren().add(editEventIdLbl);
		flowContainerName.getChildren().add(editEventNameLbl);
		
		flowContainerId.getChildren().add(editEventIdTf);
		flowContainerName.getChildren().add(editEventNameTf);
	}
	
	// untuk membuat layout table dan mengisi table dengan data
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
		
		table.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn);
		
		
	}
	
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
		stage.setTitle("Create Event");
		stage.setScene(scene);
		stage.show();
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
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
		
		else if (event.getSource() == editNameBtn) {
			String eventId = editEventIdTf.getText();
			String newEventName = editEventNameTf.getText();
			boolean valid = eventOrganizerController.editEventName(eventId, Integer.toString(organizer.getUserID()), newEventName);
			
			if (valid) {
				errorLbl.setText("Name Changed.");
				refreshTable();
			}else {
				errorLbl.setText("ERROR. Make sure to input a valid id and name.");
			}
		}
	}
	
	
}
