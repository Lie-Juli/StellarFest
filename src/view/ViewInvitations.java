package view;

import controller.EventController;
import controller.EventOrganizerController;
import controller.GuestController;
import controller.InvitationController;
import controller.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import model.Invitation;
import model.User;

public class ViewInvitations implements EventHandler<ActionEvent> {
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer, flowContainerBot;
	private VBox vbox;
	private TableView<Invitation> table;
	private Button viewInvitationsBtn, viewAcceptedEventsBtn, changeProfileBtn;
	private Button acceptBtn, logoutBtn, manageProductBtn;
	private Label eventInfoLbl, event_nameLbl, event_dateLbl, event_locationLbl; 
	
	private User user = null;
	private Invitation selectedInvitation = null;
	
	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		flowContainerBot = new FlowPane();
		
		eventInfoLbl = new Label();
		event_nameLbl = new Label();
		event_dateLbl = new Label();
		event_locationLbl = new Label();
		
		table = new TableView<Invitation>();
		table.setOnMouseClicked(tableMouseEvent());
		
		viewInvitationsBtn = new Button("View Invitations");
		viewInvitationsBtn.setOnAction(this);
		
		viewAcceptedEventsBtn = new Button("View Events");
		viewAcceptedEventsBtn.setOnAction(this);
		
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		
		acceptBtn = new Button("Accept Invitation");
        acceptBtn.setOnAction(this);
		
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		
		manageProductBtn = new Button("Manage Product");
		manageProductBtn.setOnAction(this);
		
		vbox = new VBox(10, flowContainer, table, eventInfoLbl, event_nameLbl, event_dateLbl, event_locationLbl, flowContainerBot);
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
		flowContainerBot.getChildren().add(acceptBtn);
	}
	
	// untuk membuat layout table
	private void setTable() {
        TableColumn<Invitation, Integer> idColumn = new TableColumn<>("Invitation ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));
        idColumn.setMinWidth(vbox.getWidth() / 4);

        TableColumn<Invitation, Integer> eventIdColumn = new TableColumn<>("Event ID");
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("event_id"));
        eventIdColumn.setMinWidth(vbox.getWidth() / 4);

        TableColumn<Invitation, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("invitation_status"));
        statusColumn.setMinWidth(vbox.getWidth() / 4);
        
        TableColumn<Invitation, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("invitation_role"));
        roleColumn.setMinWidth(vbox.getWidth() / 4);

        table.getColumns().addAll(idColumn, eventIdColumn, statusColumn, roleColumn);
	}
	
	// untuk menrefresh data di table dan menambahkan data terbaru agar sesuai database selalu up to date
	public void refreshTable() {
		ObservableList<Invitation> invitations = InvitationController.viewPendingInvitations(user.getUserID());
		table.setItems(invitations);
	}
	
	// pembuatan stage
	public ViewInvitations(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
		addComponent();
		setTable();
		refreshTable();
		stage.setTitle("View Invitations");
		stage.setScene(scene);
		stage.show();
	}
	
	// untuk mendapatkan data suatu invitation dari table dan mengisi informasi mengenai event yang terkait invitation itu hanya dengan click menggunakan mouse
	private EventHandler<MouseEvent> tableMouseEvent(){
				
		return new EventHandler<MouseEvent>() {
		
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Invitation> tsm = table.getSelectionModel();
				tsm.setSelectionMode(SelectionMode.SINGLE);
				selectedInvitation = tsm.getSelectedItem();
				if(selectedInvitation != null) {
					Event event_selected = EventController.viewEventDetail(selectedInvitation.getEvent_id());
					
					eventInfoLbl.setText("Event Info");
					event_nameLbl.setText("Event Name: " + event_selected.getName());
					event_dateLbl.setText("Event Date: " + event_selected.getDate());
					event_locationLbl.setText("Event Location: " + event_selected.getLocation());
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
		// Jika button yang di click adalah view products button, redirect ke page manageProduct
		else if (event.getSource() == manageProductBtn) {
			new ManageProductView(stage, user);
		}
		// jika menekan tombol Change Profile akan menredirect ke ChangeProfileView
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, user);
		}
		else if(event.getSource() == logoutBtn) { // Logout jika ditekan
			new LoginView(stage);
		}
		// jika menekan tombol Accept Invitation akan mendelete Invitationnya dan mendapat akses untuk melihat eventnya di ViewAcceptedEvents
		else if (event.getSource() == acceptBtn) {
            if (selectedInvitation != null) {
            	if(user.getRole().equals("vendor")) {
            		VendorController.acceptInvitation(selectedInvitation.getInvitation_id());
        		}
        		else if(user.getRole().equals("guest")) {
        			GuestController.acceptInvitation(selectedInvitation.getInvitation_id());
        		}
                
                refreshTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an invitation to accept.", ButtonType.OK);
                alert.show();
            }
            eventInfoLbl.setText("");
			event_nameLbl.setText("");
			event_dateLbl.setText("");
			event_locationLbl.setText("");
        }
	}
}
