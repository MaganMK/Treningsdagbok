package tdt4140.gr1837.app.ui;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXListView;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML private Tab profileTab;
	@FXML private Text clientName, ageField, phoneNumberField, motivationField, userFeedback, trainingFrequency;
	@FXML private JFXListView<Session> trainingList;
	@FXML private CheckBox enduranceCheckBox;
	@FXML private CheckBox strengthCheckBox;
	@FXML private TableView<Exercise> exerciseList;
	@FXML private Text note;
	@FXML private TextArea feedback;
	@FXML private Button submit;
	@FXML private JFXListView<String> mostUsedList;


	private List<Session> sessions;
	private Session currentSession;
	// Kolonner til ovelselista, settes dynamisk
	private TableColumn<Exercise, String> typeCol;
	private TableColumn<Exercise, String> noteCol;
	private TableColumn<Exercise, Integer> distanceCol;
	private TableColumn<Exercise, Time> timeCol;
	private TableColumn<Exercise, Integer> setCol;
	private TableColumn<Exercise, Integer> repCol;
	private TableColumn<Exercise, Integer> weightCol;

	// ManagerController for kommunikasjon med andre controllers
	public ManagerController managerController;

	// Initialiserer managerController for a fa riktig controller pga fx:include
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	// Setter user som skrives i sokefeltet og initialiserer tabellen
	public void setUser(User user) {
		clientName.setText(user.getName());
		ageField.setText("Alder: " + Integer.toString(user.getAge()));
		motivationField.setText("Motivasjon: " + user.getMotivation());
		phoneNumberField.setText("Telefonnummer: " + user.getPhoneNumber());
		String frequency = String.valueOf(user.getWeeklyTrainingFrequency());
		String result = "";
		for(char c : frequency.toCharArray())
		{
			if(c == '.')
			{
				c = ',';
			}
			result += c;
		}
		trainingFrequency.setText("Treningsfrekvens per uke: " + result);
		
		initializeColumns();
		configureCheckboxes();
		configureFeedback();
		setSessions(user);
		showFirstExercise();
		updateMostUsedTable(user);
	}

	private void setSessions(User user) {
		try {
			sessions = SQLConnector.getSessions(user.getId());
		} catch (SQLException e) {
			sessions = new ArrayList<>();
		}
		trainingList.setItems(FXCollections.observableArrayList(sessions));
		
	}

	private void configureFeedback() {
		submit.setDisable(false);
		feedback.setDisable(false);
		
	}

	// Handler for museklikk paa okter, henter ovelsene til okta og displayer dem i
	// tabellen, setter notatfeltet
	@FXML
	public void handleMouseClickSession() {
		try {
			Session session = trainingList.getSelectionModel().getSelectedItem();
			setExercises(session);
			setCurrentSession(session);
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa
			// en note.
		}
	}
	
	private void setCurrentSession(Session session) {
		currentSession = session!=null ? session : currentSession;
	}


	private void showFirstExercise() {
		exerciseList.getItems().clear();
		addNoteView("");
		addPreviousFeedback("");
		try {
			Session session = trainingList.getItems().get(0);
			setCurrentSession(session);
			trainingList.getSelectionModel().select(0);
			trainingList.getFocusModel().focus(0);
			setColumns(session.isStrength());
			setExercises(session);
		} catch (IndexOutOfBoundsException e1) {
			// Ingen okter i tabellen, sett feedback til disable=true
			submit.setDisable(true);
			feedback.setDisable(true);
		}
	}
	
	private void initializeColumns() {
		typeCol = new TableColumn<>("Type");
		noteCol = new TableColumn<>("Notat");
		distanceCol = new TableColumn<>("Distanse");
		timeCol = new TableColumn<>("Tid");
		setCol = new TableColumn<>("Sett");
		repCol = new TableColumn<>("Repetisjoner");
		weightCol = new TableColumn<>("Vekt");
		
		typeCol.setPrefWidth(exerciseList.widthProperty().multiply(0.19).doubleValue());
		noteCol.setPrefWidth(exerciseList.widthProperty().multiply(0.5).doubleValue());
		distanceCol.setPrefWidth(exerciseList.widthProperty().multiply(0.15).doubleValue());
		timeCol.setPrefWidth(exerciseList.widthProperty().multiply(0.15).doubleValue());
		setCol.setPrefWidth(exerciseList.widthProperty().multiply(0.08).doubleValue());
		repCol.setPrefWidth(exerciseList.widthProperty().multiply(0.14).doubleValue());
		weightCol.setPrefWidth(exerciseList.widthProperty().multiply(0.08).doubleValue());
		
		typeCol.setCellValueFactory(new PropertyValueFactory<Exercise,String>("name"));
		noteCol.setCellValueFactory(new PropertyValueFactory<Exercise,String>("note"));
		distanceCol.setCellValueFactory(new PropertyValueFactory<Exercise,Integer>("distance"));
		timeCol.setCellValueFactory(new PropertyValueFactory<Exercise,Time>("time"));
		setCol.setCellValueFactory(new PropertyValueFactory<Exercise,Integer>("set"));
		repCol.setCellValueFactory(new PropertyValueFactory<Exercise,Integer>("repetitions"));
		weightCol.setCellValueFactory(new PropertyValueFactory<Exercise,Integer>("weight"));
	}
	
	private void setColumns(boolean strength) {
		exerciseList.getColumns().clear();
		exerciseList.getColumns().addAll(strength ? Arrays.asList(typeCol, setCol, repCol, weightCol, noteCol) 
												  : Arrays.asList(typeCol, distanceCol, timeCol, noteCol));
	}
	
	private void configureCheckboxes() {
		enduranceCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			toggleSessionView(strengthCheckBox.isSelected(), isNowSelected);
			handleMouseClickSession();
			
		});
		strengthCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			toggleSessionView(isNowSelected, enduranceCheckBox.isSelected());
			handleMouseClickSession();
		});
	}
	
	private void toggleSessionView(boolean showStrength, boolean showEndurance) {
		if ((showEndurance && showStrength) || !(showEndurance || showStrength)) {
			trainingList.setItems(FXCollections.observableArrayList(sessions));
		}
		else if (showEndurance) {
			trainingList.setItems(FXCollections.observableArrayList(sessions.stream().filter(s -> !s.isStrength()).collect(Collectors.toList())));
		}
		else {
			trainingList.setItems(FXCollections.observableArrayList(sessions.stream().filter(s -> s.isStrength()).collect(Collectors.toList())));
		}
	}

	private void setExercises(Session session) {
		if (session==null) {
			showFirstExercise();
		} else {
			List<Exercise> exercises;
			try {
				exercises = SQLConnector.getAllExercises(session.getId(), session.isStrength());
			} catch (SQLException e) {
				e.printStackTrace();
				exercises = new ArrayList<>();
			}
			if (currentSession.isStrength() != session.isStrength()) setColumns(session.isStrength());
			addTableView(exercises);
			addNoteView(session.getNote());
			addPreviousFeedback(SQLConnector.getFeedback(session.getId()));
			userFeedback.setText("");
		}
	}

	// Setter verdier i tabellen med ovelser
	private void addTableView(List<Exercise> exercises) {
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

	// Lagre teksten i tilbakemeldingsfeltet i databasen
	public void submitFeedback() {
		updateUserFeedback(true, "");
		String text = feedback.getText();
		if (SQLConnector.registerFeedback(text, currentSession.getId())) {
			updateUserFeedback(true, "Tilbakemelding oppdatert");
		} else {
			updateUserFeedback(false, "Kunne ikke oppdatere");
		}
	}

	// Oppdaterer feedbackfeltet
	private void updateUserFeedback(boolean ok, String message) {
		if (ok) {
			userFeedback.setFill(Color.BLACK);
		} else {
			userFeedback.setFill(Color.RED);
		}
		userFeedback.setText(message);
		FadeTransition ft = new FadeTransition(Duration.millis(2000), userFeedback);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
	}
	
	
	 // Fyller ListView slik at de mest brukte øvelsene dukker opp i ui
	private void updateMostUsedTable(User user){
		List<String> mostUsedExercises = user.getMostUsedExercises();
		mostUsedList.setItems(FXCollections.observableArrayList(mostUsedExercises));
	}
}








