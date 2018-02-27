package tdt4140.gr1837.app.ui;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.MuscleImage;
import tdt4140.gr1837.app.core.User;

// Styrketab som viser graf for styrkeovelser samt checkboxes som velger hva som skal vises
public class StrengthTabController {
	
	// Bakgrunn
	@FXML Pane pane;
	
	// Klientnavn
	@FXML Text clientName;

	//Muskelgruppene 
	@FXML ImageView muscleManImage;
	@FXML ImageView underarmer;
	private MuscleImage muscleMan;
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		setMuscleMan();
	}
	
	
	private void setMuscleMan() {
		Random r = new Random();
		underarmer.setOpacity(1.0);
	}
	
	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
			clientName.setText(user.getName());
		}

}
