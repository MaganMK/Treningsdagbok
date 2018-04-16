package tdt4140.gr1837.app.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1837.app.core.EnduranceExercise;
import tdt4140.gr1837.app.core.Exercise;
import tdt4140.gr1837.app.core.SQLConnector;
import tdt4140.gr1837.app.core.Session;
import tdt4140.gr1837.app.core.User;

public class StaminaChartController {

	private User user;
	
	@FXML
	public RadioButton averageSpeedRadioButton, distanceRadioButton, timeRadioButton;
	public ToggleGroup dataRepresentation;
	public CheckListView<String> checkList;
	public LineChart<Number,Number> staminaChart;
	public NumberAxis xAxis;
	
	// Elementer i tabellen
	@FXML JFXListView<Session> trainingList;
	@FXML TableView<Exercise> exerciseList;
	@FXML TableColumn<Exercise, String> name;
	@FXML TableColumn<Exercise, Integer> distance;
	@FXML TableColumn<Exercise, String> time;
	
	List<String> graphedExercises = new ArrayList<>();
	
	public void setUser(User user){
		this.user = user;
		graphedExercises.clear();
		staminaChart.getData().clear();
		setCheckboxes();
		dataRepresentation.selectToggle(averageSpeedRadioButton);	
		xAxis.setTickLabelsVisible(false);
		staminaChart.getXAxis().setLabel("Session");
		String Ylabel = dataRepresentation.getSelectedToggle().equals(averageSpeedRadioButton) ? "Gjennomsnittsfart - km/t" 
				: dataRepresentation.getSelectedToggle().equals(distanceRadioButton) ? "Distanse - km" : "Tid - min";
		staminaChart.getYAxis().setLabel(Ylabel);
	
		//resetCheckBoxes(); // Trenger vi egentlig denne? 
	}
	
	@FXML public void handleMouseClickSession(MouseEvent arg0) {
		try {
			Session session = trainingList.getSelectionModel().getSelectedItem();
		    List<Exercise> exercises;
			try {
				exercises = SQLConnector.getAllExercises(session.getId(), session.isStrength());
			} catch (SQLException e) {
				e.printStackTrace();
				exercises = new ArrayList<>();
			}
			this.addTableView(exercises);
		} catch (NullPointerException e) {
			// Handterer unntak nar man trykker paa exercisetabellen, men ikke trykker paa en note.
		}
	    
	}
	
	// Setter verdier i tabellen med ovelser
	private void addTableView(List<Exercise> exercises) {
		name.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		distance.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("distance"));
		time.setCellValueFactory(new PropertyValueFactory<Exercise, String>("time"));
		exerciseList.getItems().setAll(exercises);
	}
	
	
	public void setCheckboxes() {
		List<String> exercisesName = user.getEnduranceExercises().stream().map(ex -> ex.getName()).distinct().collect(Collectors.toList());
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
	
	
	public void setGraph(List<String> exercises){
		staminaChart.getXAxis().setLabel("Session");
		String Ylabel = dataRepresentation.getSelectedToggle().equals(averageSpeedRadioButton) ? "Gjennomsnittsfart - km/t" 
				: dataRepresentation.getSelectedToggle().equals(distanceRadioButton) ? "Distanse - km" : "Tid - min";
		
		staminaChart.getYAxis().setLabel(Ylabel);
		for(String name : exercises){
				if (!graphedExercises.contains(name)) {
					List<EnduranceExercise> exerciseType = user.getEnduranceExercise(name);
					XYChart.Series<Number, Number> series = new XYChart.Series<>();
					series.setName(name);
					int counter = 0;
					for (EnduranceExercise current : exerciseType) { // Legger en series inn i charten (feks en sekvens for en ovelse)
						Double data = getRepresentation(current);
						series.getData().add(new XYChart.Data<>(counter++, data));
					}
					staminaChart.getData().add(series);
					setSeriesNodeControls(series, exerciseType);
					graphedExercises.add(name);
				}
			}
		
		// Gar igjennom graphedexercises og ser om den inneholder en serie som er removet, fjerner den isafall
		for (String name : graphedExercises) {
			if (!exercises.contains(name)) {
				XYChart.Series<Number, Number> serie = staminaChart.getData().stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList()).get(0);
				staminaChart.getData().remove(serie);
				graphedExercises.remove(name);
			}
		}
	}
	public void handleRadioButtons() {
		for (String item : checkList.getCheckModel().getCheckedItems()) {
			XYChart.Series<Number, Number> serie = staminaChart.getData().stream().filter(s -> s.getName().equals(item)).collect(Collectors.toList()).get(0);
			staminaChart.getData().remove(serie);
			graphedExercises.remove(item);
		}
		setGraph(checkList.getCheckModel().getCheckedItems().stream().collect(Collectors.toList()));
	}
	
	private double getRepresentation(EnduranceExercise exercise){
		return dataRepresentation.getSelectedToggle().equals(averageSpeedRadioButton) ? exercise.getAverageSpeed() : 
			dataRepresentation.getSelectedToggle().equals(distanceRadioButton) ? exercise.getDistanceRepresantation() : exercise.getTimeRepresentation();
	}
	
	
	// Gjor nodene til en serie i grafen klikkbare
	public void setSeriesNodeControls(XYChart.Series<Number, Number> series, List<EnduranceExercise> strengthExercises) {
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
	
	
	// Lager popover til en node og setter inn tilhorende treningsdata
	private void createPopOver(XYChart.Series<Number, Number> series, List<EnduranceExercise> enduranceExercises, int i) {
		series.getData().get(i).getNode().setOnMousePressed(e -> {
			PopOver pop = new PopOver();
			pop.setWidth(100);
			pop.setHeight(100);
			
			Session session;
			try {
				session = SQLConnector.getSession(enduranceExercises.get(i).getSessionId());
			} catch (SQLException e2) {
				session = null;
			}
			
			Text date = new Text(String.valueOf(session.getDate()));
			
			TableView<Exercise> exerciseList = new TableView<Exercise>();
			TableColumn<Exercise, String> name = new TableColumn<Exercise, String>();
			TableColumn<Exercise, Integer> distance = new TableColumn<Exercise,Integer>();
			TableColumn<Exercise, String> time = new TableColumn<Exercise,String>();
			
			exerciseList.maxWidth(100);
			exerciseList.maxHeight(100);
			exerciseList.setMaxSize(288, 230);
			
			name.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
			distance.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("distance"));
			time.setCellValueFactory(new PropertyValueFactory<Exercise, String>("time"));

			
			name.setText("Navn");
			distance.setText("Distanse");
			time.setText("Tid");


			exerciseList.getColumns().add(name);
			exerciseList.getColumns().add(distance);
			exerciseList.getColumns().add(time);
			
			try {
				exerciseList.getItems().setAll(SQLConnector.getAllExercises(session.getId(), session.isStrength()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				exerciseList.getItems().setAll(new ArrayList<>());
				e1.printStackTrace();
			}
			
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
	
	// Checker og unchecker checkboxer for aa cleare fra forrige bruker
	private void resetCheckBoxes() {
		List<String> exercisesName = user.getExercises().stream().map(ex -> ex.getName()).distinct().collect(Collectors.toList());
		checkList.getItems().clear();
		checkList.getItems().addAll(exercisesName);
		checkList.getCheckModel().checkAll();
		checkList.getCheckModel().clearChecks();
	}

}
	








