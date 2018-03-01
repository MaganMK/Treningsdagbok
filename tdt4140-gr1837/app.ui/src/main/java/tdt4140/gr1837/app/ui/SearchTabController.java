package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import tdt4140.gr1837.app.core.User;
import tdt4140.gr1837.app.core.UserDatabase;

public class SearchTabController {
	
	// Definerer sokefelt og feilmelding
	@FXML public Text errorMessage;
	@FXML public TextField autocompleteField;
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Initialiserer userController for a fa riktig controller ifht fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}
	
	// Utvides til faktisk sokefunksjonalitet med autocomplete-forslag i sprint 2
	@FXML public void searchForUser() {
		String userName = autocompleteField.getText();
		User user = UserDatabase.getUser(userName);
		if (user != null) {
			managerController.showUser(user);
			managerController.switchTab("profileTab");
		} else {
			errorMessage.setText("Fant ikke bruker");
		}
	}
	
	// Fjerner feilmelding nar sokefeltet blir endret
	@FXML public void removeErrorMessage() {
		errorMessage.setText("");
	}
	

}
