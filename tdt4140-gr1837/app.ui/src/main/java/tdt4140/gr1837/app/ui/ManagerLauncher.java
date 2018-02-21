package tdt4140.gr1837.app.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Klasse for å kjøre manager.fxml
public class ManagerLauncher extends Application {
	
	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Manager.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Treningsdagbok");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
