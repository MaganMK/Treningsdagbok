package tdt4140.gr1837.app.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
<<<<<<< HEAD
import javafx.scene.control.TextArea;
=======
>>>>>>> ForsÃ¸k pÃ¥ Ã¥ fÃ¥ informasjon fra database til tabell, #8
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.Exercise;
import tdt4140.gr1837.app.core.User;

public class ProfileTabController {
	
	// Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName;
	@FXML ListView<User> TrainingList1;
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
	public void setUser(User user, List<User> users, List<Exercise> exercise) {
		clientName.setText(user.getName());
		TrainingList1.setItems(FXCollections.observableArrayList(users));
		this.addTableView(exercise);
	}
	
	// Users i denne metoden må endres til øvelser, bruker users for å teste
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
	
	// Users i denne metoden mï¿½ endres til ï¿½velser, bruker users for ï¿½ teste
	private void addTableView(List<User> users) {
		// TableView<Exercise> ExerciseList = new TableView<Exercise>();
		
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		notat.setCellValueFactory(new PropertyValueFactory<>("notat"));
		sett.setCellValueFactory(new PropertyValueFactory<>("sett"));
		repetisjoner.setCellValueFactory(new PropertyValueFactory<>("repetisjoner"));
		vekt.setCellValueFactory(new PropertyValueFactory<>("vekt"));
		
		ExerciseList.setItems(FXCollections.observableArrayList());
		
		
		
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
