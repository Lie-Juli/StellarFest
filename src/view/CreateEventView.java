package view;

import java.time.LocalDate;

import controller.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class CreateEventView implements EventHandler<ActionEvent>{
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
	
	private FlowPane flowContainer;
	private VBox vbox;
	private Button viewOrganizedEventBtn, createEventBtn, createEventPageBtn;
	private Label nameLbl, dateLbl, locationLbl, descriptionLbl, errorLbl;
	private TextField nameTxt, locationTxt;
	private TextArea descriptionTxt;
	private DatePicker datePicker;
	
	private LocalDate now = LocalDate.now();
	
	private User organizer = null;

	//menginisialisasi komponen dan pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
			
		nameLbl = new Label();
		nameLbl.setText("Event Name");
			
		dateLbl = new Label();
		dateLbl.setText("Event Date");
			
		locationLbl = new Label();
		locationLbl.setText("Event Location");
			
		descriptionLbl = new Label();
		descriptionLbl.setText("Event Description");
		
		errorLbl = new Label();
			
		nameTxt = new TextField();
		locationTxt = new TextField();
		descriptionTxt = new TextArea();
			
		datePicker = new DatePicker();
		datePicker.setValue(now);
			
		viewOrganizedEventBtn = new Button("Organized Events");
		createEventPageBtn = new Button("Create Event");
		createEventBtn = new Button("Create");
		createEventBtn.setOnAction(this);
			
		vbox = new VBox(10, flowContainer, nameLbl, nameTxt, dateLbl, datePicker, locationLbl, locationTxt, descriptionLbl, descriptionTxt, createEventBtn, errorLbl);
		scene = new Scene(vbox, 700, 500);
	}
	
	// menambahkan component yang akan masuk ke dalam flowpane kita dimana flowpane ini akan bekerja sebagai navbar
	private void addComponent() {
		flowContainer.getChildren().add(viewOrganizedEventBtn);
		flowContainer.getChildren().add(createEventPageBtn);
	}
	
	// pembuatan stage yang akan menunjukan createEvent view
	public CreateEventView(Stage stage, User user) {
		this.stage = stage;
		organizer = user;
		init();
		addComponent();
		stage.setTitle("Create Event");
		stage.setScene(scene);
		stage.show();
	}

	// hal yang dilakukan ketika menekan suatu button
	@Override
	public void handle(ActionEvent event) {
		// jika menekan tombol create akan mendapatkan isi dari textfield name, location, textarea description, dan value dari datepicker yang akan dikonversi ke string dan menjalankan fungsi create event dari controller
		// jika error akan menunjukkan messeage sesuai errornya, jika berhasil akan muncul messeage yang memberitahu bahwa data sudah berhasil ditambahkan
		if(event.getSource() == createEventBtn) {
			String name = nameTxt.getText();
			LocalDate d = datePicker.getValue();
			String date = d.toString();
			String location = locationTxt.getText();
			String description = descriptionTxt.getText();
			String message = EventOrganizerController.createEvent(name, date, location, description, organizer.getUserID());
			errorLbl.setText(message);
		}
		
	}
}
