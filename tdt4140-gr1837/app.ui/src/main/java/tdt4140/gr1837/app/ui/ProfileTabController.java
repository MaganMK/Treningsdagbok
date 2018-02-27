package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

public class ProfileTabController {
	
	// Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName;
	
	// ManagerController for kommunikasjon med andre controllers
	public ManagerController managerController;
	private SessionListController sessionListController = new SessionListController();
	
	// Initialiserer managerController for a fa riktig controller pga fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		
	}

	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
		clientName.setText(user.getName());
	}
		

}
