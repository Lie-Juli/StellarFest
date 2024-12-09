package view;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.User;
import util.Connect;

public class UserView implements EventHandler<ActionEvent>{

	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer;
	private VBox vbox;
	private TableView<User> table;
	private Label titleLabel;
	private Label idLabel;
	private Label errorLabel;
	private TextField idInput;
	private Button deleteBtn;
	private Button viewEventBtn;
	private Button viewUserBtn;
	
	private Connect connect = Connect.getInstance();
	
	private ArrayList<User> userList;
	
	public void init() {
		userList = new ArrayList<User>();
		
		flowContainer = new FlowPane();
		titleLabel = new Label("View Users Page");
		idLabel = new Label("User Id: ");
		errorLabel = new Label();
		idInput = new TextField();

		deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(this);
		viewEventBtn = new Button("View Events");
		viewEventBtn.setOnAction(this);
		viewUserBtn = new Button("View Users");
		viewUserBtn.setOnAction(this);
		
		table = new TableView<User>();
		
		vbox = new VBox(10, titleLabel, flowContainer, table,  idLabel, idInput, deleteBtn, errorLabel);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	private void addComponent() {
		flowContainer.getChildren().add(viewUserBtn);
		flowContainer.getChildren().add(viewEventBtn);
		
	}
	
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
	
	public void refreshTable() {
		getUser();
		ObservableList<User> users = FXCollections.observableArrayList(userList);
		table.setItems(users);
		
	}
	
	public UserView(Stage stage) {
		this.stage = stage;
		init();
		addComponent();
		setTable();
		refreshTable();
		stage.setTitle("Users");
		stage.setScene(scene);
		stage.show();
	}
	
	public void getUser() {
		userList.clear();
		//Select query from DB
		String query = "SELECT * FROM users";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer id = connect.rs.getInt("id");
				String email = connect.rs.getString("email");
				String username = connect.rs.getString("username");
				String password = connect.rs.getString("password");
				String role = connect.rs.getString("role");
				userList.add(new User(id, email, username, password, role));
			}
		} catch (Exception e) {
			
		}
		
	}

	public void handle(ActionEvent event) {
		if (event.getSource() == deleteBtn) {
			if (idInput.getText().equals("")) {
				errorLabel.setText("Input vield can't be empty!");
			}else {
				String id = idInput.getText();
				String query = String.format("DELETE FROM users WHERE id = %s", id);
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
