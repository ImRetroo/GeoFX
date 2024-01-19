package es.yorman.dad.geofx;

import es.yorman.dad.geofx.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static final String LOADING_MESSAGE = "Loading...";
	
	private MainController mainController;

	@Override
	public void start(Stage primaryStage) {
		mainController = new MainController();

		primaryStage.setTitle("GeoFX");
		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.show();
	}
}
