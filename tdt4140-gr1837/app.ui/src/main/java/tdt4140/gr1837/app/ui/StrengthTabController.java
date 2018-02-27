package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

// Styrketab som viser graf for styrkeovelser samt checkboxes som velger hva som skal vises
public class StrengthTabController {
	
	// Bakgrunn
	@FXML Pane pane;
	
	//Klientnavn
	@FXML Text clientName;

	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}
	
/*
	public void setUser(User user) {
			clientName.setText(user.getName());
		}
		*/

}
