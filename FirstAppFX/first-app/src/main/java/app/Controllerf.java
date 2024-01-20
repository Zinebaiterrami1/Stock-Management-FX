package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controllerf {

    @FXML
    private Button deletefbtn;

    @FXML
    private TableColumn<Person, Integer> idColumn;

    @FXML
    private Button insertfbtn;
  @FXML
    private TextField idfTextField;
    
    @FXML
    private TableColumn<Person, String> nomColumn;

    @FXML
    private TextField nomfTextField;

    @FXML
    private TableColumn<Person, String> prenomColumn;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TableView<Person> table;

    @FXML
    private TextField teleTextField;

    @FXML
    private TableColumn<Person, String> telephoneColumn;

    @FXML
    private Button updatefbtn;

    @FXML
    void deleteRecord(ActionEvent event) {
        Person selectedPerson = table.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            // Appeler la méthode pour supprimer de la base de données
            deletePersonFromDatabase(selectedPerson.getIdf());
            // Rafraîchir la table après la suppression
            selectRecords();
        } else {
            showAlert("Aucune personne sélectionnée", "Veuillez sélectionner une personne à supprimer.");
        }
    }

    @FXML
    void insertRecord(ActionEvent event) {
        try {
            int idf = Integer.parseInt(idfTextField.getText());
            String nomf = nomfTextField.getText();
            String prenomf = prenomTextField.getText();
            String tele = teleTextField.getText();

            // Valider les champs (ajoutez vos propres validations si nécessaire)

            // Appeler la méthode pour insérer dans la base de données
            insertPersonToDatabase(idf, nomf, prenomf, tele);
            // Rafraîchir la table après l'insertion
            selectRecords();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez saisir un ID valide (nombre entier).");
        }
    }

    @FXML
    void updateRecord(ActionEvent event) {
        Person selectedPerson = table.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                String newNom = nomfTextField.getText();
                String newPrenom = prenomTextField.getText();
                String newTele = teleTextField.getText();

                // Valider les champs (ajoutez vos propres validations si nécessaire)

                // Appeler la méthode pour mettre à jour dans la base de données
                updatePersonInDatabase(selectedPerson.getIdf(), newNom, newPrenom, newTele);
                // Rafraîchir la table après la mise à jour
                selectRecords();
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Veuillez saisir un ID valide (nombre entier).");
            }
        } else {
            showAlert("Aucune personne sélectionnée", "Veuillez sélectionner une personne à mettre à jour.");
        }
    }

    @FXML
void handleTableSelection(MouseEvent event) {
    Person selectedPerson = table.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
        // Remplissez le formulaire avec les données sélectionnées
        idfTextField.setText(String.valueOf(selectedPerson.getIdf()));
        nomfTextField.setText(selectedPerson.getNomf());
        prenomTextField.setText(selectedPerson.getPrenomf());
        teleTextField.setText(selectedPerson.getTele());
    }
}


    @FXML
    void initialize() {
        configureTableView();
        selectRecords();

        table.setOnMouseClicked(this::handleTableSelection);
    }

    private void configureTableView() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idfProperty().asObject());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomfProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomfProperty());
        telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().teleProperty());
    }

    private void selectRecords() {
        // Code pour se connecter à la base de données, exécuter la requête et récupérer les données
        DatabaseConnection connectNow = new DatabaseConnection();
        try (Connection connectDB = connectNow.getConnection()) {
            String query = "SELECT idf, nomf, prenomf, tele FROM fournisseur";
            try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<Person> data = FXCollections.observableArrayList();

                    while (resultSet.next()) {
                        int idf = resultSet.getInt("idf");
                        String nomf = resultSet.getString("nomf");
                        String prenomf = resultSet.getString("prenomf");
                        String tele = resultSet.getString("tele");

                        data.add(new Person(idf, nomf, prenomf, tele));
                    }

                    // Définir les données dans la TableView
                    table.setItems(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de base de données", "Erreur lors de la récupération des données depuis la base de données.");
        }
    }

    private void insertPersonToDatabase(int idf, String nomf, String prenomf, String tele) {
        // Code pour insérer dans la base de données
        String query = "INSERT INTO fournisseur (idf, nomf, prenomf, tele) VALUES (?, ?, ?, ?)";
        try (Connection connectDB = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setInt(1, idf);
            preparedStatement.setString(2, nomf);
            preparedStatement.setString(3, prenomf);
            preparedStatement.setString(4, tele);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur d'insertion", "Erreur lors de l'insertion dans la base de données.");
        }
    }

    private void updatePersonInDatabase(int idf, String newNom, String newPrenom, String newTele) {
        // Code pour mettre à jour dans la base de données
        String query = "UPDATE fournisseur SET nomf=?, prenomf=?, tele=? WHERE idf=?";
        try (Connection connectDB = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, newNom);
            preparedStatement.setString(2, newPrenom);
            preparedStatement.setString(3, newTele);
            preparedStatement.setInt(4, idf);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de mise à jour", "Erreur lors de la mise à jour dans la base de données.");
        }
    }

    private void deletePersonFromDatabase(int idf) {
        // Code pour supprimer de la base de données
        String query = "DELETE FROM fournisseur WHERE idf=?";
        try (Connection connectDB = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setInt(1, idf);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de suppression", "Erreur lors de la suppression dans la base de données.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
