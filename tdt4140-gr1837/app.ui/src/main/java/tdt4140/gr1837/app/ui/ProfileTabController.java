package tdt4140.gr1837.app.ui;

import com.jfoenix.controls.JFXTreeTableView;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

public class ProfileTabController {
	
	//Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName;
	@FXML ImageView profilePicture;
	
	//Elementer til tabellen over siste øvelser
	@FXML JFXTreeTableView<?> exerciseTable;
	@FXML ScrollPane scrollPane;
	@FXML TreeTableColumn<String,String> colExercise, colSet1, colSet2, colSet3;
	
	
	//ManagerController for kommunikasjon med andre controllers
	public ManagerController managerController;
	
	//Initialiserer managerController for å få riktig controller pga fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	//Setter user som skrives i søkefeltet
	public void setUser(User user) {
		clientName.setText(user.getName());
	}
		

}
