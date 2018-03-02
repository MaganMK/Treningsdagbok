package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

// Staminatab for graf osv. til utholdenhetsovelser
public class StaminaTabController {
	
	@FXML Text clientName;

	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;

	// Initialiserer managercontroller
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

/*
	public void setUser(User user) {
		clientName.setText(user.getName());
	}
	*/

}
