package tdt4140.gr1837.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.controlsfx.control.CheckListView;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1837.app.core.Exercise;
import tdt4140.gr1837.app.core.MuscleImage;
import tdt4140.gr1837.app.core.SQLConnector;
import tdt4140.gr1837.app.core.Session;
import tdt4140.gr1837.app.core.User;

// Styrketab som viser graf for styrkeovelser samt checkboxes som velger hva som skal vises
public class StrengthTabController {
	
	// Bakgrunn
	@FXML Pane pane;
	
	// Klientnavn
	@FXML Text clientName;
	
	// Elementer i tabellen
	@FXML JFXListView<Session> trainingList;
	@FXML TableView<Exercise> exerciseList;
	@FXML TextArea note;
	@FXML TableColumn<Exercise, Integer> set;
	@FXML TableColumn<Exercise, Integer> repetitions;
	@FXML TableColumn<Exercise, Integer> weight;

	//Muskelgruppene 
	@FXML ImageView muscleManImage;
	@FXML ImageView underarmer;
	@FXML ImageView abs;
	@FXML ImageView triceps;
	@FXML ImageView teres;
	@FXML ImageView postDelts;
	@FXML ImageView obliques;
	@FXML ImageView lats;
	@FXML ImageView frontDelts;
	@FXML ImageView erector;
	@FXML ImageView calves;
	@FXML ImageView biceps;
	@FXML ImageView quads;
	@FXML ImageView rumpe;
	@FXML ImageView traps;
	@FXML ImageView hofteUt;
	@FXML ImageView bryst;
	@FXML ImageView hamstring;
	@FXML ImageView hofteIn;
	
	//Checkboxes
	@FXML CheckListView<String> checkList;
		
	//Radiobuttons
	@FXML ToggleGroup dataRepresentation;
	@FXML RadioButton rmRadioButton, vektvolumRadioButton;
		
	//Graf
	@FXML LineChart<Number,Number> strengthChart;
	
	private MuscleImage muscleMan;
	
	private Map<String, ImageView> muscles = new HashMap<>();
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		setTestData();
	}
	
	//Setter musklene til grad av rÃ¸dfarge
	public void updateMuscles() {
		Map<String, Double> musclesWithPrecentage = muscleMan.getMusclesWithPrecentages();
		
		for (String name : musclesWithPrecentage.keySet()){
			muscles.get(name).setOpacity(musclesWithPrecentage.get(name)*5); //ganger med 5 for sterkere roedfarge
		}
	}
	
	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
			clientName.setText(user.getName());
			muscleMan = new MuscleImage(user);
			int id = user.getId();
			List<Session> sessions = SQLConnector.getSessions(id);
			List<Session> sessionsReverse = sessions.subList(0, sessions.size());
			Collections.reverse(sessionsReverse);
			trainingList.setItems(FXCollections.observableArrayList(sessionsReverse));
			initMuscleMap();
			updateMuscles();
		}

	private void initMuscleMap(){
		muscles.put("underarmer", underarmer);
		muscles.put("abs", abs);
		muscles.put("triceps", triceps);
		muscles.put("teres", teres);
		muscles.put("postDelts", postDelts);
		muscles.put("obliques", obliques);
		muscles.put("lats", lats);
		muscles.put("frontDelts", frontDelts);
		muscles.put("erector", erector);
		muscles.put("calves", calves);
		muscles.put("biceps", biceps);
		muscles.put("quads", quads);
		muscles.put("rumpe", rumpe);
		muscles.put("traps", traps);
		muscles.put("hofteUt", hofteUt);
		muscles.put("bryst", bryst);
		muscles.put("hamstring", hamstring);
		muscles.put("hofteIn", hofteIn);
	}
	
	@FXML public void handleMouseClickSession(MouseEvent arg0) {
		try {
			Session session = trainingList.getSelectionModel().getSelectedItem();
		    List<Exercise> exercises = SQLConnector.getAllExercises(session.getId());
			this.addTableView(exercises);
			this.addNoteView(session.getNote());
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa en note.
		}
	    
	}
	
	// Setter verdier i tabellen med ovelser
	private void addTableView(List<Exercise> exercises) {
		//type.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		set.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("set"));
		repetitions.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("repetitions"));
		weight.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("weight"));
		exerciseList.getItems().setAll(exercises);
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
	
	// Setter notatfeltet
	private void addNoteView(String note) {
		this.note.setText(note);
	}
	
	//Setter inn testdata i grafen som erstatning for database
	public void setTestData() {
		strengthChart.getXAxis().setLabel("Økt");
		strengthChart.getYAxis().setLabel("Vekt");
		
		//Legger en series inn i charten (feks en sekvens for en øvelse)
		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
	    series1.setName("Benkpress");
	    series1.getData().add(new XYChart.Data<>(1, 70));
	    series1.getData().add(new XYChart.Data<>(2, 72.4));
	    series1.getData().add(new XYChart.Data<>(3, 80));
	    series1.getData().add(new XYChart.Data<>(4, 75));
	    series1.getData().add(new XYChart.Data<>(5, 70));
	    series1.getData().add(new XYChart.Data<>(6, 82));
	    strengthChart.getData().add(series1);
	    setSeriesNodeControls(series1);
	}
	
	// Gjor nodene til en serie i grafen klikkbare
	public void setSeriesNodeControls(XYChart.Series<Number, Number> series) {
	    for (int i = 0; i <	series.getData().size(); i++) {
			final int j = i;
			// aapner popup-vindu til noden som trykkes paa
			series.getData().get(i).getNode().setOnMousePressed(e -> {
				 
				 PopOver pop = new PopOver();
				 
				 Bounds boundsInScreen = series.getData().get(j).getNode().localToScreen(series.getData().get(j).getNode().getBoundsInLocal());
				 Double y = boundsInScreen.getMaxY() - 44;
				 Double x = boundsInScreen.getMinX() + 4;
				 
				 
			     pop.setX(x);
			     pop.setY(y);
			     
			     pop.setMinSize(300, 150);
				 pop.show((Stage)series.getData().get(j).getNode().getScene().getWindow());
			});
			
			//Setter cursoren til hand ved hover
			series.getData().get(i).getNode().setOnMouseMoved(e -> {
				Scene scene = series.getData().get(j).getNode().getScene();
				scene.setCursor(Cursor.HAND);
			});
			
			//Setter cursoren til default når den er ute av noden
			series.getData().get(i).getNode().setOnMouseExited(e -> {
				Scene scene = series.getData().get(j).getNode().getScene();
				scene.setCursor(Cursor.DEFAULT);
			});
		}
	}
}
