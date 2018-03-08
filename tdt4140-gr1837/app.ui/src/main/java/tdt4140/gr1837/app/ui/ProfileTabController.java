package tdt4140.gr1837.app.ui;

import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableView;

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
	@FXML Text clientName, ageField, phoneNumberField;
	@FXML JFXListView<Session> trainingList;
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
		ageField.setText("Alder: " + Integer.toString(user.getAge()));
		phoneNumberField.setText("Telefonnummer: " + user.getPhoneNumber());
		int id = user.getId();
		List<Session> sessions = SQLConnector.getSessions(id);
		List<Session> sessionsReverse = sessions.subList(0, sessions.size());
		Collections.reverse(sessionsReverse);
		trainingList.setItems(FXCollections.observableArrayList(sessionsReverse));
		showFirstExercise();
	}
	
	// Handler for museklikk paa okter, henter ovelsene til okta og displayer dem i tabellen, setter notatfeltet
	@FXML public void handleMouseClickSession(MouseEvent arg0) {
		try {
			Session session = trainingList.getSelectionModel().getSelectedItem();
			setExercises(session);
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa en note.
		}
	}
	
	// Handler for museklikk paa overlser, setter notat-feltet til riktig notat
	@FXML public void handleMouseClickExercise(MouseEvent arg0) {
		try {
			Exercise exercise = exerciseList.getSelectionModel().getSelectedItem();
			this.addNoteView(exercise.getNote());
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa en note.
		} 
	}
	
	private void showFirstExercise() {
		try {
			Session session = trainingList.getItems().get(0);
			trainingList.getSelectionModel().select(0);
			trainingList.getFocusModel().focus(0);
			setExercises(session); }
		catch (Exception e) {
			//Får ingen feilmelding hvis man ikke finner noen treningsøkter
		}
	}
	
	private void setExercises(Session session) {
		List<Exercise> exercises = SQLConnector.getAllExercises(session.getId());
		this.addTableView(exercises);
		this.addNoteView(session.getNote());
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
		this.note.setEditable(false);
	}
}
