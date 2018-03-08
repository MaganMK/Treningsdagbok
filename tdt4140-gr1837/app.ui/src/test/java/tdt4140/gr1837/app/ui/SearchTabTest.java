package tdt4140.gr1837.app.ui;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import com.jfoenix.controls.JFXButton;
import static org.testfx.api.FxAssert.verifyThat;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchTabTest extends ApplicationTest {

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
        Parent root = FXMLLoader.load(getClass().getResource("SearchTab.fxml"));
        
        Scene scene = new Scene(root);
        this.root = scene.getRoot();
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testErrorFieldExists() {
    		Node field = from(root).lookup("#errorMessage").query();
    		Assert.assertTrue(field instanceof Text);
    }
    
    @Test
    public void testSearchFieldExists() {
    		Node field = from(root).lookup("#searchField").query();
    		Assert.assertTrue(field instanceof CustomTextField);
    }
    
    @Test
    public void testSearchButtonExists() {
    		Node button = from(root).lookup("#searchButton").query();
    		Assert.assertTrue(button instanceof JFXButton);
    }
    
    // Denne funker visst ikke i gitlab, ma finne en annen losning
    
    @Test
    public void testSearchAction() {
    		clickOn("#searchField");
    		write("skau");
    		clickOn("#searchButton");
    		verifyThat("#searchField", NodeMatchers.hasText("skau"));
    }
    
    
}
