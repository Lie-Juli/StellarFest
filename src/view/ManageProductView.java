package view;

import controller.ProductController;
import controller.VendorController;
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
import model.Product;
import model.User;

public class ManageProductView implements EventHandler<ActionEvent> {
	//component-component yang dibutuhkan
	private Stage stage;
	private Scene scene;
		
	private FlowPane flowContainer;
	private VBox vbox;
	private Label productNameLbl, productDescriptionLbl, errorLbl;
	private Button deleteBtn, addBtn;
	private TextField productNameTF;
	private TextArea productDescriptionTF;
	
	private Button viewInvitationsBtn, viewAcceptedEventsBtn, manageProductsBtn, logoutBtn, changeProfileBtn;
	private TableView<Product> table;
	private User vendor = null;
	private Product selected_product = null;
	
	// komponen dalam pembuatan scene
	public void init() {
		flowContainer = new FlowPane();
		
		table = new TableView<Product>();
		table.setOnMouseClicked(tableMouseEvent());
		
		productNameLbl = new Label();
		productNameLbl.setText("Product Name: ");
		productDescriptionLbl = new Label();
		productDescriptionLbl.setText("Description: ");
		
		productNameTF = new TextField();
		productDescriptionTF = new TextArea();
		
		addBtn = new Button("Add");
		addBtn.setOnAction(this);
		deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(this);
		
		errorLbl = new Label();
		
		viewInvitationsBtn = new Button("View Invitations");
		viewInvitationsBtn.setOnAction(this);
		viewAcceptedEventsBtn = new Button("View Events");
		viewAcceptedEventsBtn.setOnAction(this);
		manageProductsBtn = new Button("Manage Product");
		manageProductsBtn.setOnAction(this);
		changeProfileBtn = new Button("Change Profile");
		changeProfileBtn.setOnAction(this);
		logoutBtn = new Button("Logout");
		logoutBtn.setOnAction(this);
		
		vbox = new VBox(10, flowContainer, table, productNameLbl, productNameTF, productDescriptionLbl, productDescriptionTF, addBtn, deleteBtn, errorLbl);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox, 700, 500);
	}
	
	// flowpane yang akan bekerja sebagai navbar
	private void addComponent() {
		flowContainer.getChildren().add(viewInvitationsBtn);
		flowContainer.getChildren().add(viewAcceptedEventsBtn);
		flowContainer.getChildren().add(manageProductsBtn);
		flowContainer.getChildren().add(changeProfileBtn);
		flowContainer.getChildren().add(logoutBtn);
	}
		
	// untuk membuat layout table
	private void setTable() {
		TableColumn<Product, String> productNameColumn = new TableColumn<Product, String>("Product Name");
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));
		productNameColumn.setMinWidth(vbox.getWidth()/2);
			
		TableColumn<Product, String> productDescriptionColumn = new TableColumn<Product, String>("Product Description");
		productDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("product_description"));
		productDescriptionColumn.setMinWidth(vbox.getWidth()/2);
			
		table.getColumns().addAll(productNameColumn, productDescriptionColumn);
	}
	
	// untuk menrefresh data di table dan menambahkan data terbaru agar sesuai database selalu up to date
	private void viewAllProduct() {
		ObservableList<Product> products = FXCollections.observableArrayList(ProductController.getAllProducts(vendor.getUserID()));
		table.setItems(products);
	}
	
	public ManageProductView(Stage stage, User user) {
		this.vendor = user;
		this.stage = stage;
		init();
		addComponent();
		setTable();
		viewAllProduct();
		stage.setTitle("Manage Product");
		stage.setScene(scene);
		stage.show();
	}
	
	// untuk mendapatkan data suatu event dari table hanya dengan click menggunakan mouse
	private EventHandler<MouseEvent> tableMouseEvent(){
			
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Product> tsm = table.getSelectionModel();
				tsm.setSelectionMode(SelectionMode.SINGLE);
				selected_product = tsm.getSelectedItem();
			}
		};
	}
	
	
	@Override
	public void handle(ActionEvent event) {
		
		// Jika button yang di click adalah view invitations button, redirect ke view invitations page
		if (event.getSource() == viewInvitationsBtn) { 
			new ViewInvitations(stage, vendor);
		// Jika button yang di click adalah view events button, redirect ke accepted events view page
		}else if(event.getSource()== viewAcceptedEventsBtn){
			new ViewAcceptedEvents(stage, vendor);
		}
		// Jika button yang di click adalah view products button, redirect ke page ini lagi
		else if (event.getSource() == manageProductsBtn) {
			new ManageProductView(stage, vendor);
		}
		// jika menekan tombol changeprofle akan menredirect ke changeProfile view
		else if(event.getSource() == changeProfileBtn) {
			new ChangeProfileView(stage, vendor);
		}
		// Logout jika ditekan
		else if(event.getSource() == logoutBtn) { 
			new LoginView(stage);
		}
		// fungsi action jika memasukan product baru dan menekan tombol add
		else if(event.getSource() == addBtn) {
			int vendor_id = vendor.getUserID();
			String name = productNameTF.getText();
			String description = productDescriptionTF.getText();
			productNameTF.clear();
			productDescriptionTF.clear();
			
			String message = VendorController.insertProduct(vendor_id, name, description);
			errorLbl.setText(message);
			viewAllProduct();
		}
		// fungsi action jika menekan tombol delete
		else if(event.getSource() == deleteBtn) {
			if(selected_product == null) {
				errorLbl.setText("Please choose a product to delete");
			}
			else {
				ProductController.deleteProduct(selected_product.getProduct_id());
				errorLbl.setText("Product Succesfully Deleted");
				viewAllProduct();
			}
		}
		
	}
	
	
}
