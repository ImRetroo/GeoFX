package es.yorman.dad.geofx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import es.yorman.dad.geofx.App;
import es.yorman.dad.geofx.api.model.Ipapi;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SecurityController implements Initializable {

	// view

	@FXML
	private BorderPane view;

	@FXML
	private CheckBox crawlerDetectedCheck;

	@FXML
	private Label potentialThreatTypesLabel;

	@FXML
	private CheckBox proxyDetectedCheck;

	@FXML
	private Label threatLevelLabel;

	@FXML
	private CheckBox torDetectedCheck;

	@FXML
	private Label verdictLabel;

	// model

	private StringProperty verdict = new SimpleStringProperty(App.LOADING_MESSAGE);
	private BooleanProperty proxyDetected = new SimpleBooleanProperty();
	private BooleanProperty torDetected = new SimpleBooleanProperty();
	private BooleanProperty crawlerDetected = new SimpleBooleanProperty();
	private StringProperty threatLevel = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty threatTypes = new SimpleStringProperty(App.LOADING_MESSAGE);

	public SecurityController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SecurityView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindings

		verdictLabel.textProperty().bind(verdict);
		proxyDetectedCheck.selectedProperty().bind(proxyDetected);
		torDetectedCheck.selectedProperty().bind(torDetected);
		crawlerDetectedCheck.selectedProperty().bind(crawlerDetected);
		threatLevelLabel.textProperty().bind(threatLevel);
		potentialThreatTypesLabel.textProperty().bind(threatTypes);

		// listeners
		
		threatLevel.addListener((observable, oldValue, newValue) -> {
			switch (newValue.toLowerCase()) {
			case "low":
				verdict.set("This IP is safe. No threats have been detected.");
				break;
			case "medium":
				verdict.set("Potential threats detected. This IP may be unsafe.");
				break;
			case "high":
				verdict.set("Definitive threats detected. This IP is unsafe.");
				break;
			default:
				verdict.set("Threat level unknown.");
			}
		});
	}

	public BorderPane getView() {
		return view;
	}
	
	public void load(Ipapi result) {
		crawlerDetected.set(result.getSecurity().getIsCrawler());
		proxyDetected.set(result.getSecurity().getIsProxy());
		torDetected.set(result.getSecurity().getIsTor());
		threatLevel.set(result.getSecurity().getThreatLevel());
		
		if (result.getSecurity().getThreatTypes() != null) {
			threatTypes.set(result.getSecurity().getThreatTypes().stream().collect(Collectors.joining(", ")));
		} else {
			threatTypes.set("No threats have been detected for this IP address.");
		}
	}
}
