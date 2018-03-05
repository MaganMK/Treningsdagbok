package tdt4140.gr1837.app.ui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
	@FXML ListView<Session> trainingList;
	@FXML TableView<Exercise> exerciseList;
	@FXML TableColumn<Exercise, String> type;
	@FXML TextArea note;
	@FXML TableColumn<Exercise, Integer> set;
	@FXML TableColumn<Exercise, Integer> repetitions;
	@FXML TableColumn<Exercise, Integer> weight;
	
	// ManagerController for kommunikasjon med andre controllers
	public ManagerController managerController;
	// private SessionListController sessionListController = new SessionListController();
	
	// Initialiserer managerController for a fa riktig controller pga fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		
	}

	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
		clientName.setText(user.getName());
		int id = user.getId();
		List<Session> sessions = SQLConnector.getSessions(id);
		trainingList.setItems(FXCollections.observableArrayList(sessions));
	}
	
	// Handler for museklikk paa okter, henter ovelsene til okta og displayer dem i tabellen, setter notatfeltet
	@FXML public void handleMouseClickSession(MouseEvent arg0) {
	    Session session = trainingList.getSelectionModel().getSelectedItem();
	    List<Exercise> exercises = SQLConnector.getAllExercises(session.getId());
		this.addTableView(exercises);
		this.addNoteView(session.getNote());
	}
	
	// Handler for museklikk paa overlser, setter notat-feltet til riktig notat
	@FXML public void handleMouseClickExercise(MouseEvent arg0) {
	    Exercise exercise = exerciseList.getSelectionModel().getSelectedItem();
		this.addNoteView(exercise.getNote());
	}
	
	// Setter verdier i tabellen med ovelser
	private void addTableView(List<Exercise> exercises) {
		type.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		set.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("set"));
		repetitions.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("repetitions"));
		weight.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("weight"));
		
		exerciseList.getItems().setAll(exercises);
	}
	
	// Setter notatfeltet
	private void addNoteView(String note) {
		this.note.setText(note);
	}
}
