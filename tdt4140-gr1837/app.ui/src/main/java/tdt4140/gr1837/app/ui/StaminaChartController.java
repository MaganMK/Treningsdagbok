package tdt4140.gr1837.app.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckListView;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import tdt4140.gr1837.app.core.User;

public class StaminaChartController {

	private User user;
	
	@FXML
	public RadioButton longDistanceRadioButton, intervallRadioButton;
	public ToggleGroup dataRepresentation;
	public CheckListView checkList;
	
	public void setUser(User user){
		this.user = user;
		dataRepresentation.selectToggle(longDistanceRadioButton);
	}
	
	public void handleRadioButtons(){
		
		
	}
	
//	public void setCheckboxes() {
//		// List<String> exercisesName = user.getEnduranceExercises().stream().map(ex -> ex.getName()).distinct().collect(Collectors.toList());
//		checkList.getItems().clear();
//		checkList.getItems().addAll(exercisesName);
//		checkList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() { // Klikk-lyttere
//		     public void onChanged(ListChangeListener.Change<? extends String> c) {
//		    	 try {
//		    		 // setGraph(checkList.getCheckModel().getCheckedItems().stream().collect(Collectors.toList()));
//		    	 } catch (Exception e) { }
//		     }
//		 });
//	}
	
}
