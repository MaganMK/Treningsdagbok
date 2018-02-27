package tdt4140.gr1837.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.MuscleImage;
import tdt4140.gr1837.app.core.User;

// Styrketab som viser graf for styrkeovelser samt checkboxes som velger hva som skal vises
public class StrengthTabController {
	
	// Bakgrunn
	@FXML Pane pane;
	
	// Klientnavn
	@FXML Text clientName;

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
	
	private MuscleImage muscleMan;
	
	private Map<String, ImageView> muscles = new HashMap<>();
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		muscleMan = new MuscleImage();
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
	
	//Setter musklene til grad av rødfarge
	public void updateMuscles() {
		for (String name : muscleMan.getMuscles().keySet()){
			muscles.get(name).setOpacity(muscleMan.getMuscles().get(name));
		}
	}
	
	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
			clientName.setText(user.getName());
		}

}
