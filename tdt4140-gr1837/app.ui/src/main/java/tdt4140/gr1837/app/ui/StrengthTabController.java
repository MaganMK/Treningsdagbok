package tdt4140.gr1837.app.ui;

import java.util.HashMap;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PopOver;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1837.app.core.User;

//Styrketab som viser graf for styrkeøvelser samt checkboxes som velger hva som skal vises
public class StrengthTabController {
	
	//Bakgrunn
	@FXML Pane pane;
	
	//Klientnavn
	@FXML Text clientName;

	//Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	
	//Setter managerController
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}
	
	//Setter user som skrives i søkefeltet
	public void setUser(User user) {
			clientName.setText(user.getName());
		}

}
