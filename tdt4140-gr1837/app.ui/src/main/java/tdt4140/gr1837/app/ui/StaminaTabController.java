package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

//Staminatab for graf osv. til utholdenhets�velser
public class StaminaTabController {
	
	//Tekstfelt for � se hvilken bruker man er inne p�
	@FXML Text clientName;

	//Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;

	//Initialiserer managercontroller
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	//Setter opps�kt bruker i tekstfelt
	public void setUser(User user) {
		clientName.setText(user.getName());
	}

}
