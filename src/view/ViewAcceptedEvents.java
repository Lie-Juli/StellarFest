package view;

import controller.GuestController;
import controller.VendorController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;

public class ViewAcceptedEvents implements EventHandler<ActionEvent> {
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer, flowContainerBot;
	private VBox vbox;
	private TableView<Event> table;
	private Button viewInvitationsBtn, viewAcceptedEventsBtn, changeProfileBtn;
	private Button detailsBtn, logoutBtn, manageProductBtn;
	
	private User user = null;
	private int tempId;
	private Event eventSelected = null;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		flowContainerBot = new FlowPane();
		
		table = new TableView<Event>();
		table.setOnMouseClicked(tableMouseEvent());
		
		viewInvitationsBtn = new Button("View Invitations");
		viewInvitationsBtn.setOnAction(this);
		
		viewAcceptedEventsBtn = new Button("View Events");
		viewAcceptedEventsBtn.setOnAction(this);
		
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		
		detailsBtn = new Button("Event Detail");
        detailsBtn.setOnAction(this);
		
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		
		manageProductBtn = new Button("Manage Product");
		manageProductBtn.setOnAction(this);
		
		vbox = new VBox(10, flowContainer, table, flowContainerBot);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita, dimana flowpane ini akan bekerja sebagai navbar(flowContainer) dan footer(flowContainerBot)
	private void addComponent() {
		flowContainer.getChildren().add(viewInvitationsBtn);
		flowContainer.getChildren().add(viewAcceptedEventsBtn);
		if(user.getRole().equals("vendor")) {
			flowContainer.getChildren().add(manageProductBtn);
		}
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
		flowContainerBot.getChildren().add(detailsBtn);
	}
	
	// untuk membuat layout table
	private void setTable() {
		ObservableList<Event> events = null;
		if(user.getRole().equals("vendor")) {
			events = VendorController.viewAcceptedEvents(user.getUserID());
		}
		else if(user.getRole().equals("guest")) {
			events = GuestController.viewAcceptedEvents(user.getUserID());
		}
		

		TableColumn<Event, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setMinWidth(vbox.getWidth() / 4);

        TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(vbox.getWidth() / 4);

        TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(vbox.getWidth() / 4);
        
        TableColumn<Event, String> locationColumn = new TableColumn<Event, String>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("location"));
		locationColumn.setMinWidth(vbox.getWidth()/4);

        table.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn);
        table.setItems(events);
	}
	
	// pembuatan stage
	public ViewAcceptedEvents(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
		addComponent();
		setTable();
		stage.setTitle("View Accepted Events");
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
					tempId = eventSelected.getId();
				}
			}
		};
	}

	// hal yang dilakukan ketika menekan suatu button
	@Override
	public void handle(ActionEvent event) {
		// jika menekan tombol View Events akan menredirect ke ViewAcceptedEvents
		if(event.getSource() == viewAcceptedEventsBtn) {
			new ViewAcceptedEvents(stage, user);
		}
		// jika menekan tombol View Invitations akan menredirect ke ViewInvitations
		else if(event.getSource() == viewInvitationsBtn) {
			new ViewInvitations(stage, user);
		}
		// jika menekan tombol Change Profile akan menredirect ke ChangeProfileView
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, user);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
		// jika menekan tombol Event Detail akan menredirect ke ViewAcceptedEventDetail
		else if (event.getSource() == detailsBtn) {
			Event selectedEvent = table.getSelectionModel().getSelectedItem();
	        if (selectedEvent != null) {
	        	new ViewEventDetailView(stage, user, tempId);
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event to view details.", ButtonType.OK);
	            alert.show();
	        }
        }
	}
}
