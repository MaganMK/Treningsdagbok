package tdt4140.gr1837.app.ui;

import org.junit.Test;
import org.junit.BeforeClass;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxAppTest extends ApplicationTest {
	
	@BeforeClass
    public static void headless() {
    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
    			GitlabCISupport.headless();
    		}
    }

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }


    @Test
	public void test() 
	{
		assertTrue("Automatically successful test :)", true);
	}
}
