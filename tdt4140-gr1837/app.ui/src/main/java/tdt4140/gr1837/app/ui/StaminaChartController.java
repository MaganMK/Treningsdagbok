package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import tdt4140.gr1837.app.core.User;

public class StaminaChartController {

	private User user;
	
	@FXML
	public RadioButton longDistanceRadioButton, intervallRadioButton;
	public ToggleGroup dataRepresentation;
	
	public void setUser(User user){
		this.user = user;
		dataRepresentation.selectToggle(longDistanceRadioButton);
	}
	
	public void handleRadioButtons(){
		
		
	}
}
