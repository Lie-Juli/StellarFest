package view;

import java.io.Console;
import java.util.ArrayList;

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
import util.Connect;

public class EventView implements EventHandler<ActionEvent>{
	
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
	
	private Connect connect = Connect.getInstance();
	
	private ArrayList<Event> eventList;
	
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
		
		table = new TableView<Event>();
		
		vbox = new VBox(10, titleLabel, flowContainer, table,  idLabel, idInput, deleteBtn, errorLabel);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	private void addComponent() {
		flowContainer.getChildren().add(viewEventBtn);
		flowContainer.getChildren().add(viewUserBtn);
	}
	
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
		
		table.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn, descriptionColumn);
		
		
	}
	
	public void refreshTable() {
		getEvent();
		ObservableList<Event> events = FXCollections.observableArrayList(eventList);
		table.setItems(events);
	}
	
	public EventView(Stage stage) {
		this.stage = stage;
		init();
		addComponent();
		setTable();
		refreshTable();
		stage.setTitle("Events");
		stage.setScene(scene);
		stage.show();
	}
	
	public void getEvent() {
		eventList.clear();
		//Select query from DB
		String query = "SELECT * FROM events";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer id = connect.rs.getInt("id");
				String name = connect.rs.getString("name");
				String date = connect.rs.getString("date");
				String location = connect.rs.getString("location");
				String description = connect.rs.getString("description");
				eventList.add(new Event(id, name, date, location, description));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == deleteBtn) {
			if (idInput.getText().equals("")) {
				errorLabel.setText("Input vield can't be empty!");
			}else {
				String id = idInput.getText();
				String query = String.format("DELETE FROM events WHERE id = %s", id);
				connect.execUpdate(query);
				refreshTable();
				errorLabel.setText("Data deleted!");
			}
		}
		else if (event.getSource() == viewEventBtn) {
			new EventView(stage);
		}else if (event.getSource() == viewUserBtn) {
			new UserView(stage);
		}
		
	}
	
}
