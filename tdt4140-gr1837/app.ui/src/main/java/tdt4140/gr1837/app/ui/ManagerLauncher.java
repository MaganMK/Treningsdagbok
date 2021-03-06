package tdt4140.gr1837.app.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tdt4140.gr1837.app.core.SQLConnector;

// Klasse for a kjore manager.fxml
public class ManagerLauncher extends Application {
	
	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Manager.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Treningsdagbok");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:../../Images/Div/icon.png"));
        stage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				SQLConnector.closeConnection();
			}
		});
    }

}
