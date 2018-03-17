package tdt4140.gr1837.app.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckListView;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXListView;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1837.app.core.Exercise;
import tdt4140.gr1837.app.core.MuscleImage;
import tdt4140.gr1837.app.core.SQLConnector;
import tdt4140.gr1837.app.core.Session;
import tdt4140.gr1837.app.core.StrengthExercise;
import tdt4140.gr1837.app.core.User;

// Styrketab som viser graf for styrkeovelser samt checkboxes som velger hva som skal vises
public class StrengthTabController {
	
	User user;
	
	// Oversikt over exercises som allerede er graphed
	List<String> graphedExercises = new ArrayList<>();
	
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
	@FXML RadioButton rmRadioButton, weightVolumeRadioButton;
		
	//Graf
	@FXML LineChart<Number,Number> strengthChart;
	@FXML NumberAxis xAxis;
	
	private MuscleImage muscleMan;
	
	private Map<String, ImageView> muscles = new HashMap<>();
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}
	
	// Setter musklene til grad av rødfarge
	public void updateMuscles() {
		Map<String, Double> musclesWithPrecentage = muscleMan.getMusclesWithPrecentages();
		
		for (String name : musclesWithPrecentage.keySet()){
			muscles.get(name).setOpacity(musclesWithPrecentage.get(name)*5); //ganger med 5 for sterkere roedfarge
		}
	}
	
	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
		this.user = user;
		clientName.setText(user.getName());
		muscleMan = new MuscleImage(user);
		initMuscleMap();
		updateMuscles();
		graphedExercises.clear();
		strengthChart.getData().clear();
		setCheckboxes();
		dataRepresentation.selectToggle(rmRadioButton);
		xAxis.setTickLabelsVisible(false);
		resetCheckBoxes();
	}
	
	// Checker og unchecker checkboxer for aa cleare fra forrige bruker
	private void resetCheckBoxes() {
		List<String> exercisesName = user.getExercises().stream().map(ex -> ex.getName()).distinct().collect(Collectors.toList());
		checkList.getItems().clear();
		checkList.getItems().addAll(exercisesName);
		checkList.getCheckModel().checkAll();
		checkList.getCheckModel().clearChecks();
	}

	// Mapper muskler til muskelnavn
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
		    List<Exercise> exercises;
			try {
				exercises = SQLConnector.getAllExercises(session.getId());
			} catch (SQLException e) {
				e.printStackTrace();
				exercises = new ArrayList<>();
			}
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
	
	// Setter inn testdata i grafen som erstatning for database
	public void setGraph(List<String> exercises) {	
		strengthChart.getXAxis().setLabel("Session");
		String Ylabel = dataRepresentation.getSelectedToggle().equals(rmRadioButton) ? "1RM - kg" : "Vektvolum - kg";
		strengthChart.getYAxis().setLabel(Ylabel);
		for(String name : exercises){
				if (!graphedExercises.contains(name)) {
					List<StrengthExercise> exerciseType = user.getStrengthExercise(name);
					XYChart.Series<Number, Number> series = new XYChart.Series<>();
					series.setName(name);
					int counter = 0;
					for (StrengthExercise current : exerciseType) { // Legger en series inn i charten (feks en sekvens for en ovelse)
						Double data = getRepresentation(current);
						series.getData().add(new XYChart.Data<>(counter++, data));
					}
					strengthChart.getData().add(series);
					setSeriesNodeControls(series, exerciseType);
					graphedExercises.add(name);
				}
			}
		
		// Gar igjennom graphedexercises og ser om den inneholder en serie som er removet, fjerner den isafall
		for (String name : graphedExercises) {
			if (!exercises.contains(name)) {
				XYChart.Series<Number, Number> serie = strengthChart.getData().stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList()).get(0);
				strengthChart.getData().remove(serie);
				graphedExercises.remove(name);
			}
		}
	}
	
	private double getRepresentation(StrengthExercise exercise){
		if(dataRepresentation.getSelectedToggle().equals(rmRadioButton)) {
			return exercise.getOneRepMax();
		}
		else {
			return exercise.getWeightVolume();
		}
	}
	
	public void handleRadioButtons() {
		for (String item : checkList.getCheckModel().getCheckedItems()) {
			XYChart.Series<Number, Number> serie = strengthChart.getData().stream().filter(s -> s.getName().equals(item)).collect(Collectors.toList()).get(0);
			strengthChart.getData().remove(serie);
			graphedExercises.remove(item);
		}
		setGraph(checkList.getCheckModel().getCheckedItems().stream().collect(Collectors.toList()));
	}
	
	// Henter ovelser som en bruker har gjort og laget checkboxer
	public void setCheckboxes() {
		List<String> exercisesName = user.getExercises().stream().map(ex -> ex.getName()).distinct().collect(Collectors.toList());
		checkList.getItems().clear();
		checkList.getItems().addAll(exercisesName);
		checkList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() { // Klikk-lyttere
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		    	 try {
		    		 setGraph(checkList.getCheckModel().getCheckedItems().stream().collect(Collectors.toList()));
		    	 } catch (Exception e) { }
		     }
		 });
	}
	
	// Lager popover til en node og setter inn tilhorende treningsdata
	private void createPopOver(XYChart.Series<Number, Number> series,List<StrengthExercise> strengthExercises, int i) {
		series.getData().get(i).getNode().setOnMousePressed(e -> {
			PopOver pop = new PopOver();
			pop.setWidth(100);
			pop.setHeight(100);
			
			Session session = SQLConnector.getSessionByExercise(strengthExercises.get(i).getSessionId());
			
			Text date = new Text(String.valueOf(session.getDate()));
			
			TableView<Exercise> exerciseList = new TableView<Exercise>();
			TableColumn<Exercise, Integer> set = new TableColumn<Exercise,Integer>();
			TableColumn<Exercise, Integer> weight = new TableColumn<Exercise,Integer>();
			TableColumn<Exercise, Integer> repetitions = new TableColumn<Exercise,Integer>();
			TableColumn<Exercise, String> type = new TableColumn<Exercise,String>();
			
			exerciseList.maxWidth(100);
			exerciseList.maxHeight(100);
			exerciseList.setMaxSize(288, 230);
			
			type.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
			set.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("set"));
			repetitions.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("repetitions"));
			weight.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("weight"));
			
			set.setText("Set");
			type.setText("Session");
			repetitions.setText("Repetisjoner");
			weight.setText("Vekt");
			
			exerciseList.getColumns().add(type);
			exerciseList.getColumns().add(set);
			exerciseList.getColumns().add(repetitions);
			exerciseList.getColumns().add(weight);
			
			exerciseList.getItems().setAll(SQLConnector.getAllExercises(session.getId()));
			
			VBox container = new VBox();
			container.setPadding(new Insets(5,0,0,5));
			container.getChildren().addAll(date,exerciseList);
			
			pop.setContentNode(date);
			pop.setContentNode(container);
			
			Bounds boundsInScreen = series.getData().get(i).getNode().localToScreen(series.getData().get(i).getNode().getBoundsInLocal());
			Double y = boundsInScreen.getMaxY() - 44;
			Double x = boundsInScreen.getMinX() + 4;
		    pop.setX(x);
		    pop.setY(y);
		    pop.setMinSize(300, 150);
		    
			pop.show((Stage)series.getData().get(i).getNode().getScene().getWindow());
		});
	}
	
	
	// Gjor nodene til en serie i grafen klikkbare
	public void setSeriesNodeControls(XYChart.Series<Number, Number> series, List<StrengthExercise> strengthExercises) {
	    for (int i = 0; i <	series.getData().size(); i++) {
			final int j = i;
			createPopOver(series, strengthExercises, i);
			
			series.getData().get(i).getNode().setOnMouseMoved(e -> { // Setter cursoren til hand ved hover
				Scene scene = series.getData().get(j).getNode().getScene();
				scene.setCursor(Cursor.HAND);
				series.getData().get(j).getNode().setStyle("-fx-scale-x: 1.3;-fx-scale-y: 1.3;");
			});
			
			series.getData().get(i).getNode().setOnMouseExited(e -> { // Setter cursoren til default naar den er ute av noden
				Scene scene = series.getData().get(j).getNode().getScene();
				scene.setCursor(Cursor.DEFAULT);
				series.getData().get(j).getNode().setStyle("-fx-scale-x: 1;-fx-scale-y: 1");
			});
		}
	}
}
