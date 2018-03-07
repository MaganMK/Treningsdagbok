package tdt4140.gr1837.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerTest extends ApplicationTest {

	Parent root;
	
    @BeforeClass
    public static void headless() {
    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
    			GitlabCISupport.headless();
    		}
    }

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Manager.fxml"));
        
        Scene scene = new Scene(root);
        this.root = scene.getRoot();
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void allTabsExists() {
    		Node searchTab = from(root).lookup("#searchTab1").query();
    		Node profileTab = from(root).lookup("#profileTab1").query();
    		Node strengthTab = from(root).lookup("#strengthTab1").query();
    		Node staminaTab = from(root).lookup("#staminaTab1").query();
    		List<Node> tabs = new ArrayList<>(Arrays.asList(searchTab,profileTab,strengthTab,staminaTab));
    		boolean exists = true;
    		for (Node tab : tabs) { 
    			if (tab == null) {
    				exists = false;
    			}
    		}
    		Assert.assertTrue(exists);
    }

    
    @Test
    public void tabPaneExists() {
    		Node tabPane = from(root).lookup("#tabPane").query();
    		Assert.assertTrue(tabPane instanceof JFXTabPane);
    }
    
  
    
    
    
}
