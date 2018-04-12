package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class DistanceBarController {
	// Distanse 
	@FXML Rectangle distanceBar;

	// Initialiserer managercontroller
	public void init() {
		//this.managerController = managerController;
		this.distanceBar.setHeight(100);
		this.distanceBar.setLayoutY(507);
	}
	
	public void setProgress() {
		
	}
}
