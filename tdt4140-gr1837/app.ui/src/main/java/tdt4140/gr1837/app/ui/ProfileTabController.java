package tdt4140.gr1837.app.ui;

import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

public class ProfileTabController {
	
	// Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName;
	@FXML ImageView profilePicture;
	
	//Elementer til tabellen over siste øvelser
	@FXML JFXTreeTableView<?> exerciseTable;
	@FXML JFXScrollPane scrollPane;
	@FXML TreeTableColumn<String,String> colExercise, colSet1, colSet2, colSet3;
	
	
	// ManagerController for kommunikasjon med andre controllers
	public ManagerController managerController;
	
	// Initialiserer managerController for a fa riktig controller pga fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
		clientName.setText(user.getName());
	}
		

}
