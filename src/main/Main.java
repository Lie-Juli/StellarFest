package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.EventView;
import view.LoginView;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// gw ubah ke EventView smentara - William
		new EventView(primaryStage);
		primaryStage.show();
	}

}
