package tdt4140.gr1837.app.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.Exercise;
import tdt4140.gr1837.app.core.SQLConnector;
import tdt4140.gr1837.app.core.User;
import tdt4140.gr1837.app.core.Session;

public class ProfileTabController {
	
	// Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName;
	@FXML ListView<Session> TrainingList1;
	@FXML TableView<Exercise> ExerciseList;
	@FXML TableColumn<Exercise, String> type;
	@FXML TextArea notat;
	@FXML TableColumn<Exercise, Integer> sett;
	@FXML TableColumn<Exercise, Integer> repetisjoner;
	@FXML TableColumn<Exercise, Integer> vekt;
	
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
		int id = user.getId();
		List<Session> sessions = SQLConnector.getSessions(id);
		TrainingList1.setItems(FXCollections.observableArrayList(sessions));
		// this.addTableView(exercise);
	}
	
	// Users i denne metoden maa endres til ovelser, bruker users for aa teste
	private void addTableView(List<Exercise> exercise) {
		// TableView<Exercise> ExerciseList = new TableView<Exercise>();
		
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		sett.setCellValueFactory(new PropertyValueFactory<>("sett"));
		repetisjoner.setCellValueFactory(new PropertyValueFactory<>("repetisjoner"));
		vekt.setCellValueFactory(new PropertyValueFactory<>("vekt"));
		
		ExerciseList.setItems(FXCollections.observableArrayList(exercise));
		ExerciseList.getColumns().addAll(type, sett, repetisjoner, vekt);	
		
		
		
		
		/*TableColumn type = new TableColumn("Type");
		type.setCellValueFactory(new PropertyValueFactory<Exercise, String>("type"));
		
		TableColumn sett = new TableColumn("Sett");
		sett.setCellValueFactory(new PropertyValueFactory<Exercise, String>("sett"));
		
		TableColumn repetisjoner = new TableColumn("Repetisjoner");
		type.setCellValueFactory(new PropertyValueFactory<Exercise, String>("Type"));
		
		TableColumn vekt = new TableColumn("Vekt");
		vekt.setCellValueFactory(new PropertyValueFactory<Exercise, String>("vekt"));*/
		
	}
		

}
