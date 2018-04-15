package tdt4140.gr1837.app.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.fxml.FXML;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DistanceBarController {
	// Distanse 
	@FXML Rectangle distanceBar;
	@FXML Line progressLine;
	@FXML Text progressText;
	@FXML Text distanceToRunText;
	@FXML Text distanceRunText;
	
	// Lagrer hoyden, saa man slipper aa maatte endre om hoyden endres i FXMLen 
	private double initialHeight;
	private double initialY;
	private final double textOffset = 2;
	
	// Initialiserer managercontroller
	public void init() {
		this.initialHeight = this.distanceBar.getHeight();
		this.initialY = this.distanceBar.getLayoutY();
	}
	
	// Setter teksten og possisjonen til alle elementene
	public void setProgress(int distanceToRun, int distanceRun) {
		double progress = Double.valueOf(distanceRun) / distanceToRun;
		NumberFormat formater = new DecimalFormat("#0.0");
		if(progress > 1) {
			progress = 1;
		}
		double newHeight = this.initialHeight * progress;
		double newY = this.initialY + initialHeight - newHeight;
		this.distanceBar.setHeight(newHeight);
		this.distanceBar.setLayoutY(newY);
		this.progressLine.setLayoutY(newY);
		this.progressText.setLayoutY(newY - textOffset);
		this.progressText.setText(formater.format(progress * 100) + "%");
		this.distanceToRunText.setText("Distansemål: " + formater.format(distanceToRun / 1000.0) + "km");
		this.distanceRunText.setText("Distanse løpt: " + formater.format(distanceRun / 1000.0) + "km");
	}
}
