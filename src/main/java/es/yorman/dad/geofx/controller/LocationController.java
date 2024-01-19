package es.yorman.dad.geofx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import es.yorman.dad.geofx.App;
import es.yorman.dad.geofx.api.model.Ipapi;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class LocationController implements Initializable {

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Label callingCodeLabel;

	@FXML
	private Label cityStateLabel;

	@FXML
	private ImageView flagImage;

	@FXML
	private Label currencyLabel;

	@FXML
	private Label ipLocationLabel;

	@FXML
	private Label languageLabel;

	@FXML
	private Label latitudeLabel;

	@FXML
	private Label longitudeLabel;

	@FXML
	private Label timeZoneLabel;

	@FXML
	private Label zipCodeLabel;

	// model

	private DoubleProperty latitude = new SimpleDoubleProperty();
	private DoubleProperty longitude = new SimpleDoubleProperty();
	private StringProperty ipLocation = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty cityState = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty zipCode = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty language = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty timeZone = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty callingCode = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty currency = new SimpleStringProperty(App.LOADING_MESSAGE);

	public LocationController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LocationView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindings

		latitudeLabel.textProperty().bind(latitude.asString("%1.6f"));
		longitudeLabel.textProperty().bind(longitude.asString("%1.6f"));
		ipLocationLabel.textProperty().bind(ipLocation);
		cityStateLabel.textProperty().bind(cityState);
		zipCodeLabel.textProperty().bind(zipCode);
		languageLabel.textProperty().bind(language);
		timeZoneLabel.textProperty().bind(timeZone);
		callingCodeLabel.textProperty().bind(callingCode);
		currencyLabel.textProperty().bind(currency);
	}

	public BorderPane getView() {
		return view;
	}

	public void load(Ipapi result) {
		latitude.set(result.getLatitude());
		longitude.set(result.getLongitude());
		ipLocation.set(result.getCountryName() + " (" + result.getCountryCode() + ")");
		flagImage.setImage(new Image("/assets/flags/64x42/" + result.getCountryCode() + ".png"));
		cityState.set(result.getCity() + " (" + result.getRegionName() + ")");
		zipCode.set(result.getZip());
		language.set(result.getLocation().getLanguages().get(0).getName() + " ("
				+ result.getLocation().getLanguages().get(0).getCode().toUpperCase() + ")");
		timeZone.set(result.getTimeZone().getCode());
		callingCode.set("+" + result.getLocation().getCallingCode());
		currency.set(result.getCurrency().getName() + " (" + result.getCurrency().getSymbol() + ")");
	}
}
