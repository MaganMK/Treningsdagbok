package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

// Staminatab for graf osv. til utholdenhetsovelser
public class StaminaTabController {
	
	// Tekstfelt for a se hvilken bruker man er inne pa
	@FXML Text clientName;

	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;

	// Initialiserer managercontroller
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	// Setter oppsokt bruker i tekstfelt
	public void setUser(User user) {
		clientName.setText(user.getName());
	}

}
