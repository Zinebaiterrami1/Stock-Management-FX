package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class App extends Application{

    @Override
    public void start (Stage primaryStage)
    {
    
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
			e.printStackTrace();

        }
     
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

