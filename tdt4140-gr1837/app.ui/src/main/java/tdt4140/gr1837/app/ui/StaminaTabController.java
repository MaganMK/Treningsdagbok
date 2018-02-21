package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

//Staminatab for graf osv. til utholdenhetsøvelser
public class StaminaTabController {
	
	//Tekstfelt for å se hvilken bruker man er inne på
	@FXML Text clientName;

	//Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;

	//Initialiserer managercontroller
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	//Setter oppsøkt bruker i tekstfelt
	public void setUser(User user) {
		clientName.setText(user.getName());
	}

}
