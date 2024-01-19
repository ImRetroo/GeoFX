package es.yorman.dad.geofx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import es.yorman.dad.geofx.api.IPAPIService;
import es.yorman.dad.geofx.api.IPIFYService;
import es.yorman.dad.geofx.api.model.Ipapi;
import es.yorman.dad.geofx.api.model.Ipify;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	// view

	@FXML
	private BorderPane view;

	@FXML
	private Tab connectionTab;

	@FXML
	private Tab locationTab;

	@FXML
	private Tab securityTab;

	@FXML
	private TabPane tabPane;

	@FXML
	private TextField ipField;

	// controllers

	private LocationController locationController;
	private ConnectionController connectionController;
	private SecurityController securityController;

	// logic

	private IPIFYService ipifyService = new IPIFYService();
	private IPAPIService ipapiService = new IPAPIService();

	// model

	private StringProperty ipAddress = new SimpleStringProperty();

	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		locationController = new LocationController();
		connectionController = new ConnectionController();
		securityController = new SecurityController();

		locationTab.setContent(locationController.getView());
		connectionTab.setContent(connectionController.getView());
		securityTab.setContent(securityController.getView());

		// bindings

		ipField.textProperty().bindBidirectional(ipAddress);

		// load ip
		
		loadIP();
	}

	public BorderPane getView() {
		return view;
	}
	
	public void loadIP() {
		Task<Ipify> task = new Task<Ipify>() {
			protected Ipify call() throws Exception {
				return ipifyService.getIP();
			}
		};
		task.setOnSucceeded(event -> {
			ipAddress.set(task.getValue().getIp());
			onCheckIP(null);
		});
		task.setOnFailed(event -> event.getSource().getException().printStackTrace());
		new Thread(task).start();
	}

	@FXML
	void onCheckIP(ActionEvent event) {
		Task<Ipapi> task = new Task<Ipapi>() {
			protected Ipapi call() throws Exception {
				return ipapiService.getIPData(ipAddress.get());
			}
		};
		task.setOnSucceeded(taskEvent -> {
			Ipapi result = task.getValue();
			locationController.load(result);
			connectionController.load(result);
			securityController.load(result);
		});
		task.setOnFailed(taskEvent -> taskEvent.getSource().getException().printStackTrace());
		new Thread(task).start();
	}
}
