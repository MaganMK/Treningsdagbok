package tdt4140.gr1837.app.ui;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StaminaChartTest extends ApplicationTest {

	Parent root;
	Stage stage;

	@BeforeClass
	public static void headless() {
		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
			GitlabCISupport.headless();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("StaminaChart.fxml"));

		Scene scene = new Scene(root);
		this.root = scene.getRoot();

		stage.setTitle("JavaFX and Maven");
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testGraphElementsExists() {
		Node strengthChart = from(root).lookup("#staminaChart").query();
		Node rmRadioButton = from(root).lookup("#averageSpeedRadioButton").query();

		Assert.assertTrue(strengthChart instanceof LineChart);
		Assert.assertTrue(rmRadioButton instanceof RadioButton);
	}
}
