package tdt4140.gr1837.app.ui;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;
import tdt4140.gr1837.app.core.UserDatabase;

public class SearchTabController {
	
	// Definerer sokefelt og feilmelding
	@FXML public Text errorMessage;
	@FXML public CustomTextField searchField;
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Initialiserer userController for a fa riktig controller ifht fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		bindAutocomplete();
	}
	
	// Finner bruker nar man trykker pa sokeknappen
	// Utvides til faktisk sokefunksjonalitet med autocomplete-forslag i sprint 2
	@FXML public void searchForUser() {
		String userName = searchField.getText();
		if(userName.length() == 0) {
			errorMessage.setText("Tomt felt");
			return;
		}
		User user = UserDatabase.getUser(userName);
		if (user != null) {
			errorMessage.setText("");
			managerController.showUser(user);
			managerController.switchTab("profileTab");
		} else {
			errorMessage.setText("Fant ikke " + userName);
		}
	}
	
	//Soker paa bruker nar man skriver i sokefeltet og trykker enter
	@FXML public void enterPressed() {
		searchForUser();
	}
	
	//Fester brukerlista til sokefeltet, slik at den kan komme med forslag ved inntasting
	public void bindAutocomplete() {
		TextFields.bindAutoCompletion(searchField, UserDatabase.getUsers()).setPrefWidth(searchField.getPrefWidth());
	}
}
