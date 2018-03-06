package tdt4140.gr1837.app.ui;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchTabTest extends ApplicationTest {


    @BeforeClass
    public static void headless() {
    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
    			GitlabCISupport.headless();
    		}
    }

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SearchTab.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testErrorFieldExists() {
    		Node field = lookup("#errorMessage").query();
    		Assert.assertTrue(field instanceof Text);
    }
    
    @Test
    public void testSearchFieldExists() {
    		Node field = lookup("#searchField").query();
    		Assert.assertTrue(field instanceof TextField);
    }
    
    @Test
    public void testSearchButtonExists() {
    		Node button = lookup("#searchButton").query();
    		Assert.assertTrue(button instanceof JFXButton);
    }
    
    @Test
    public void testSearchAction() {
    		SearchTabAction actionTest = new SearchTabAction();
    		actionTest.testSearchAction();
    }
    
  
}
