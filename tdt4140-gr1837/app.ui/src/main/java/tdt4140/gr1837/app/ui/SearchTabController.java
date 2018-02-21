package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import tdt4140.gr1837.app.core.User;
import tdt4140.gr1837.app.core.UserDatabase;

public class SearchTabController {
	
	//Definerer s�kefelt og feilmelding
	@FXML public Text errorMessage;
	@FXML public TextField autocompleteField;
	
	//Midlertidig database med brukere
	public UserDatabase users = new UserDatabase();
	
	//Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	//Initialiserer userController for � f� riktig controller ifht fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}
	
	//Finner bruker n�r man trykker g�
	//Utvides til faktisk s�kefunksjonalitet med autocomplete-forslag i sprint 2
	@FXML public void searchForUser() {
		String userName = autocompleteField.getText();
		User user = users.getUser(userName);
		if (user != null) {
			managerController.showUser(user);
			managerController.switchTab("profileTab");
		} else {
			errorMessage.setText("Fant ikke bruker");
		}
	}
	
	

}
