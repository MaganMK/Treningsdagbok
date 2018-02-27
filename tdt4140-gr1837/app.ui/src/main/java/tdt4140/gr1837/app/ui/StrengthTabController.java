package tdt4140.gr1837.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	
	private List<ImageView> muscles ;
	
	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	// Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		muscles = Arrays.asList(underarmer, abs, 
				triceps, teres, postDelts, obliques, lats, frontDelts, erector,
				calves, biceps, quads, rumpe, traps, hofteUt, bryst, hamstring, hofteIn);
	}
	
	//Setter musklene til tilfeldig grad av rødfarge
	public void random() {
		Random r = new Random();
		for (ImageView muscle : muscles){
			muscle.setOpacity(r.nextDouble());
		}
	}
	
	// Setter user som skrives i sokefeltet
	public void setUser(User user) {
			clientName.setText(user.getName());
		}

}
