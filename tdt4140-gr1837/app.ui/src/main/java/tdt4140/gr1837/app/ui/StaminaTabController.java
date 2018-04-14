package tdt4140.gr1837.app.ui;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.SQLConnector;
import tdt4140.gr1837.app.core.User;

// Staminatab for graf osv. til utholdenhetsovelser
public class StaminaTabController {
	
	// Tekstfelt for a se hvilken bruker man er inne pa
	@FXML Text clientName;
	

	@FXML DistanceBarController distanceBarController;

	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;
	public StaminaChartController staminaChartController;

	// Initialiserer managercontroller
	public void init(ManagerController managerController) {
		this.managerController = managerController;
		distanceBarController.init();
	}

	// Setter oppsokt bruker i tekstfelt
	public void setUser(User user) {
		clientName.setText(user.getName());
		staminaChartController.setUser(user);
		updatePieChart(user);
		distanceBarController.setProgress(1000, 1000);
	}
	
	@FXML PieChart piechart;
	// Oppdaterer pichartet i henhold til brukerens treningsdata
	public void updatePieChart(User user) {
		Map<String, Integer> enduranceDistribution = SQLConnector.getEnduranceDistribution(user.getId());
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		if (!enduranceDistribution.isEmpty()){
			for (String key : enduranceDistribution.keySet()) {
				pieChartData.add(new PieChart.Data(key, enduranceDistribution.get(key)));
			}
			this.piechart.setTitle("Tid brukt på ulike øvelser");
		}
		else {
			this.piechart.setTitle("Ingen utholdenhetsøvelser registrert");
		}
		this.piechart.setData(pieChartData);
		this.piechart.setLabelsVisible(false);
	}


}
