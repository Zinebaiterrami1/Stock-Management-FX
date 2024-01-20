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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    void initialize() {
        
    }

    public void loginButtonOnAction(ActionEvent e)
    {
       

        if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
           // loginMessageLabel.setText("You Try To Login!");
           DatabaseConnection connectNow = new DatabaseConnection();
           Connection connectDB = connectNow.getConnection();
            validateLogin(connectDB);
                }
        else{
            loginMessageLabel.setText("Please Enter Your username and Your password!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(Connection connectDB) {
        String verifyLogin = "SELECT count(1) FROM admin WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordPasswordField.getText() + "'";
    
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
    
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    // Fermer la fenêtre de login
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                // Ouvrir la nouvelle interface
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("acceuil.fxml"));
                     Scene scene = new Scene(root);
                     
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Acceuil");
                newStage.show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               
                } else {
                    loginMessageLabel.setText("Invalid Login. Please try again");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   
    

}
