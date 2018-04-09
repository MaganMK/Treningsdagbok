package tdt4140.gr1837.app.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;
import tdt4140.gr1837.app.core.User;

// Staminatab for graf osv. til utholdenhetsovelser
public class StaminaTabController {
	
	// Tekstfelt for a se hvilken bruker man er inne pa
	@FXML Text clientName;

	// Managercontroller for kommunikasjon mellom controllers
	public ManagerController managerController;

	// Initialiserer managercontroller
	public void init(ManagerController managerController) {
		this.managerController = managerController;
	}

	// Setter oppsokt bruker i tekstfelt
	public void setUser(User user) {
		clientName.setText(user.getName());
		updatePieChart(user);
	}
	
	@FXML PieChart piechart;
	// Oppdaterer pichartet i henhold til brukerens treningsdata
	public void updatePieChart(User user) {
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 130),
                new PieChart.Data("Oranges", 250),
                new PieChart.Data("Plums", 100),
                new PieChart.Data("Pears", 220),
                new PieChart.Data("Apples", 300));
		this.piechart.setData(pieChartData);
		this.piechart.setLegendVisible(false);
	}

}
