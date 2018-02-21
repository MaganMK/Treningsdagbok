package tdt4140.gr1837.app.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

public class ProfileTabController {
	
	//Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName;
	@FXML ImageView profilePicture;
	
	//Elementer til tabellen over siste �velser
	@FXML TableView exerciseTable;
	@FXML ScrollPane scrollPane;
	@FXML TableColumn colExercise, colSet1, colSet2, colSet3, colSet4, colSet5;
	
	
	//ManagerController for kommunikasjon med andre controllers
	public ManagerController managerController;
	
	//Initialiserer managerController for � f� riktig controller pga fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	//Setter user som skrives i s�kefeltet
	public void setUser(User user) {
		clientName.setText(user.getName());
	}
		

}
