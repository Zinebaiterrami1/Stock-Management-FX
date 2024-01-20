package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controllera {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button interfacefbtn;

    @FXML
    private Button interfaceobtn;
 @FXML
    void openOrdinateur(ActionEvent event) {
        openInterface("pageO.fxml");
        
    }

  

    @FXML
    void openFournisseur(ActionEvent event) {
        openInterface("pageF.fxml");
    }

    private void openInterface(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        assert interfacefbtn != null : "fx:id=\"interfacefbtn\" was not injected: check your FXML file 'accueil.fxml'.";
        assert interfaceobtn != null : "fx:id=\"interfaceobtn\" was not injected: check your FXML file 'accueil.fxml'.";

    }

}

