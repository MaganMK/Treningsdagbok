package tdt4140.gr1837.app.ui;

import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import tdt4140.gr1837.app.core.TrainerDatabase;
import tdt4140.gr1837.app.core.User;
import tdt4140.gr1837.app.core.UserDatabase;

// Generell controller til hele managersiden
public class ManagerController {
	
	// Instansierer controllers for alle tabs, for kommunikasjon
	@FXML public ProfileTabController profileTabController;
	@FXML public SearchTabController searchTabController;
	@FXML public StrengthTabController strengthTabController;
	@FXML public StaminaTabController staminaTabController;
	
	// Tabs som brukes til a skifte mellom tabs
	@FXML TabPane tabPane;
	@FXML Tab profileTab1;
	@FXML Tab searchTab1;
	@FXML Tab strengthTab1;
	@FXML Tab staminaTab1;
	
	// Mapper Streng til tab for a kunne skifte tab med metode
	HashMap<String,Tab> tabMap;
	
	// Initialiserer controllers med seg selv som hovedcontroller
	@FXML public void initialize() {
		UserDatabase.initialize();
		TrainerDatabase.initialize();
		profileTabController.init(this);
		searchTabController.init(this);
		strengthTabController.init(this);
		staminaTabController.init(this);
		
		// Setter inn tabs i tabmappen
		tabMap = new HashMap<>();
		tabMap.put("profileTab", profileTab1);
		tabMap.put("searchTab", searchTab1);
		tabMap.put("strengthTab", strengthTab1);
		tabMap.put("staminaTab", staminaTab1);
		
		profileTab1.setDisable(true);
		strengthTab1.setDisable(true);
		staminaTab1.setDisable(true);
	}
	
	// Far profilsiden til a vise bruker
	public void showUser(User user) {
		profileTab1.setDisable(false);
		strengthTab1.setDisable(false);
		staminaTab1.setDisable(false);
		profileTabController.setUser(user);
		strengthTabController.setUser(user);
		staminaTabController.setUser(user);
	}

	// Endrer tab til angitt tab
	public void switchTab(String tab) {
		try {
			tabPane.getSelectionModel().select(tabMap.get(tab));
			
		} catch (Exception e) {
			System.out.println("Kunne ikke finne tab");
		}
	}
	
	
	

}
