package tdt4140.gr1837.app.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tdt4140.gr1837.app.core.Exercise;
import tdt4140.gr1837.app.core.SQLConnector;
import tdt4140.gr1837.app.core.User;
import tdt4140.gr1837.app.core.Session;

public class ProfileTabController {
	
	// Elementer i ProfileTab.fxml
	@FXML Tab profileTab;
	@FXML Text clientName, ageField, phoneNumberField, motivationField, userFeedback;
	@FXML JFXListView<Session> trainingList;
	@FXML TableView<Exercise> exerciseList;
	@FXML TableColumn<Exercise, String> type;
	@FXML Text note;
	@FXML TextArea feedback;
	@FXML Button submit;
	@FXML TableColumn<Exercise, Integer> set;
	@FXML TableColumn<Exercise, Integer> repetitions;
	@FXML TableColumn<Exercise, Integer> weight;
	@FXML TableColumn<Exercise, String> notat;
	
	private Session currentSession;
	
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
		motivationField.setText("Motivasjon: " + user.getMotivation());
		phoneNumberField.setText("Telefonnummer: " + user.getPhoneNumber());
		int id = user.getId();
		List<Session> sessions;
		try {
			sessions = SQLConnector.getSessions(id);
		} catch (SQLException e) {
			sessions = new ArrayList<>();
		}
		List<Session> sessionsReverse = sessions.subList(0, sessions.size());
		Collections.reverse(sessionsReverse);
		trainingList.setItems(FXCollections.observableArrayList(sessionsReverse));
		showFirstExercise();
	}
	
	// Handler for museklikk paa okter, henter ovelsene til okta og displayer dem i tabellen, setter notatfeltet
	@FXML public void handleMouseClickSession(MouseEvent arg0) {
		try {
			Session session = trainingList.getSelectionModel().getSelectedItem();
			currentSession = session;
			setExercises(session);
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa en note.
		}
	}
	
	// Handler for museklikk paa overlser, setter notat-feltet til riktig notat
	@FXML public void handleMouseClickExercise(MouseEvent arg0) {
		try {
			Exercise exercise = exerciseList.getSelectionModel().getSelectedItem();
			//this.addNoteView(exercise.getNote());
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa en note.
		} 
	}
	
	private void showFirstExercise() {
		try {
			Session session = trainingList.getItems().get(0);
			currentSession = session;
			trainingList.getSelectionModel().select(0);
			trainingList.getFocusModel().focus(0);
			setExercises(session); 
		} catch (Exception e) {
			// Faar ingen feilmelding hvis man ikke finner noen treningskter
		}
	}
	
	private void setExercises(Session session) {
		List<Exercise> exercises;
		try {
			exercises = SQLConnector.getAllExercises(session.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			exercises = new ArrayList<>();
		}
		this.addTableView(exercises);
		this.addNoteView(session.getNote());
		this.addPreviousFeedback(SQLConnector.getFeedback(session.getId()));
		this.userFeedback.setText("");
	}
	
	// Setter verdier i tabellen med ovelser
	private void addTableView(List<Exercise> exercises) {
		type.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		set.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("set"));
		repetitions.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("repetitions"));
		weight.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("weight"));
		notat.setCellValueFactory(new PropertyValueFactory<Exercise, String>("note"));
		exerciseList.getItems().setAll(exercises);
	}
	
	// Setter notatfeltet
	private void addNoteView(String note) {
		this.note.setText(note);	
	}
	
	// Setter tilbakemeldingsfeltet til tidligere lagret tilbakemelding
	private void addPreviousFeedback(String feedback) {
		this.feedback.setText(feedback);
	}
	
	// Lagre teksten i feedback i databasen
	public void submitFeedback() {
		updateUserFeedback(true, "");
		String text = feedback.getText();
		if (SQLConnector.registerFeedback(text, currentSession.getId())) {
			updateUserFeedback(true, "Tilbakemelding oppdatert");
		}
		else {
			updateUserFeedback(false, "Kunne ikke oppdatere");
		}
	}
	
	private void updateUserFeedback(boolean ok, String message) {
		if (ok) {
			userFeedback.setFill(Color.BLACK);
		}
		else {
			userFeedback.setFill(Color.RED);
		}
		userFeedback.setText(message);
		FadeTransition ft = new FadeTransition(Duration.millis(2000), userFeedback);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
		/*Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ae -> userFeedback.setText("")));
		timeline.play();*/
	}
}
