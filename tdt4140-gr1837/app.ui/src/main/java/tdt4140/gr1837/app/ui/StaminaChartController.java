package tdt4140.gr1837.app.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckListView;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import tdt4140.gr1837.app.core.EnduranceExercise;
import tdt4140.gr1837.app.core.StrengthExercise;
import tdt4140.gr1837.app.core.User;

public class StaminaChartController {

	private User user;
	
	@FXML
	public RadioButton averageSpeedRadioButton, distanceRadioButton;
	public ToggleGroup dataRepresentation;
	public CheckListView<String> checkList;
	public LineChart<Number,Number> staminaChart;
	public NumberAxis xAxis;
	
	List<String> graphedExercises = new ArrayList<>();
	
	public void setUser(User user){
		this.user = user;
		graphedExercises.clear();
		staminaChart.getData().clear();
		setCheckboxes();
		dataRepresentation.selectToggle(averageSpeedRadioButton);	
		xAxis.setTickLabelsVisible(false);
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
		String Ylabel = dataRepresentation.getSelectedToggle().equals(averageSpeedRadioButton) ? "Gjennomsnittsfart - km/t" : "Distanse - km";
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
					// setSeriesNodeControls(series, exerciseType);
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
		if(dataRepresentation.getSelectedToggle().equals(averageSpeedRadioButton)) {
			return exercise.getAverageSpeed();
		}
		else {
			return exercise.getDistanceRepresantation();
		}
	}
	
	/*
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
	*/

}
	








