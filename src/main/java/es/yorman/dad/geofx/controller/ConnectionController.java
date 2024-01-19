package es.yorman.dad.geofx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import es.yorman.dad.geofx.App;
import es.yorman.dad.geofx.api.model.Ipapi;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ConnectionController implements Initializable {

	// view
	
    @FXML
    private BorderPane view;
	
    @FXML
    private Label asnLabel;

    @FXML
    private Label hostnameLabel;

    @FXML
    private Label ipAddressLabel;

    @FXML
    private Label ispLabel;

    @FXML
    private Label typeLabel;
    
    // model
    
    private StringProperty ipAddress = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty isp = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty type = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty asn = new SimpleStringProperty(App.LOADING_MESSAGE);
	private StringProperty hostname = new SimpleStringProperty(App.LOADING_MESSAGE);
    
	public ConnectionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConnectionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindings
		
		ipAddressLabel.textProperty().bind(ipAddress);
		ispLabel.textProperty().bind(isp);
		typeLabel.textProperty().bind(type);
		asnLabel.textProperty().bind(asn);
		hostnameLabel.textProperty().bind(hostname);
	}
	
	public BorderPane getView() {
		return view;
	}

	public void load(Ipapi result) {
		ipAddress.set(result.getIp());
		isp.set(result.getConnection().getIsp());
		type.set(result.getType());
		asn.set(result.getConnection().getAsn().toString());
		hostname.set(result.getHostname());
	}
}
