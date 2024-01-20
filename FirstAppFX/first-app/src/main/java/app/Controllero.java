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

public class Controllero {

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField graphicTextField;

    @FXML
    private TextField idoTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField numseriTextField;

    @FXML
    private TextField prixTextField;

    @FXML
    private TextField ramTextField;

    @FXML
    private TextField romTextField;

    @FXML
    private Button deleteobtn;

    @FXML
    private TableColumn<Ordinateur, Integer> clido;  // Renommé ici

    @FXML
    private Button insertobtn;

    @FXML
    private TableColumn<Ordinateur, String> clnom;  // Ajouté ici

    @FXML
    private TableView<Ordinateur> tableo;

    @FXML
    private TableColumn<Ordinateur, String> clram;  // Ajouté ici

    @FXML
    private TableColumn<Ordinateur, String> clrom;  // Ajouté ici

    @FXML
    private TableColumn<Ordinateur, String> clbrand;  // Ajouté ici

    @FXML
    private TableColumn<Ordinateur, String> clgraphic;  // Ajouté ici

    @FXML
    private TableColumn<Ordinateur, String> clserie;  // Ajouté ici

    @FXML
    private TableColumn<Ordinateur, String> clprix;  // Ajouté ici

    @FXML
    void deleteRecord(ActionEvent event) {
        Ordinateur selectedOrdinateur = tableo.getSelectionModel().getSelectedItem();
        if (selectedOrdinateur != null) {
            deleteOrdinateurFromDatabase(selectedOrdinateur.getIdo());
            selectRecords();
        } else {
            showAlert("Aucun ordinateur sélectionné", "Veuillez sélectionner un ordinateur à supprimer.");
        }
    }

    @FXML
    void insertRecord(ActionEvent event) {
        try {
            int ido = Integer.parseInt(idoTextField.getText());
            String nom = nomTextField.getText();
            String ram = ramTextField.getText();
            String rom = romTextField.getText();
            String brand = brandTextField.getText();
            String graphic = graphicTextField.getText();
            String numserie = numseriTextField.getText();
            String prix = prixTextField.getText();

            insertOrdinateurToDatabase(ido, nom, ram, rom, brand, graphic, numserie, prix);
            selectRecords();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez saisir un ID valide (nombre entier).");
        }
    }
    @FXML
    void updateRecord(ActionEvent event) {
        Ordinateur selectedOrdinateur = tableo.getSelectionModel().getSelectedItem();
        if (selectedOrdinateur != null) {
            try {
                int ido = selectedOrdinateur.getIdo();
                String nom = nomTextField.getText();
                String ram = ramTextField.getText();
                String rom = romTextField.getText();
                String brand = brandTextField.getText();
                String graphic = graphicTextField.getText();
                String numserie = numseriTextField.getText();
                String prix = prixTextField.getText();
    
                updateOrdinateurInDatabase(ido, nom, ram, rom, brand, graphic, numserie, prix);
                selectRecords();
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Veuillez saisir un ID valide (nombre entier).");
            }
        } else {
            showAlert("Aucun ordinateur sélectionné", "Veuillez sélectionner un ordinateur à mettre à jour.");
        }
    }

    @FXML
void handleTableSelection(MouseEvent event) {
    Ordinateur selectedOrdinateur = tableo.getSelectionModel().getSelectedItem();
    if (selectedOrdinateur != null) {
        // Remplissez le formulaire avec les données sélectionnées
        idoTextField.setText(String.valueOf(selectedOrdinateur.getIdo()));
        nomTextField.setText(selectedOrdinateur.getNom());
        ramTextField.setText(selectedOrdinateur.getRam());
        romTextField.setText(selectedOrdinateur.getRom());
        brandTextField.setText(selectedOrdinateur.getBrand());
        graphicTextField.setText(selectedOrdinateur.getGraphic());
        numseriTextField.setText(selectedOrdinateur.getNumserie());
        prixTextField.setText(selectedOrdinateur.getPrix());
    }
}


    private void updateOrdinateurInDatabase(int ido, String nom, String ram, String rom, String brand, String graphic, String numserie, String prix) {
        String query = "UPDATE ordinateur SET nom=?, ram=?, rom=?, brand=?, graphic=?, numserie=?, prix=? WHERE ido=?";
    
        try (Connection connectDB = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, ram);
            preparedStatement.setString(3, rom);
            preparedStatement.setString(4, brand);
            preparedStatement.setString(5, graphic);
            preparedStatement.setString(6, numserie);
            preparedStatement.setString(7, prix);
            preparedStatement.setInt(8, ido);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de base de données", "Erreur lors de la mise à jour dans la base de données.");
        }
    }
    
    @FXML
    void initialize() {
        configureTableView();
        selectRecords();

           // Ajoutez un écouteur d'événements pour gérer la sélection de la table
    tableo.setOnMouseClicked(this::handleTableSelection);
    }

    private void configureTableView() {
        // configuration des colonnes
        clido.setCellValueFactory(cellData -> cellData.getValue().idoProperty().asObject());
        clnom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        clram.setCellValueFactory(cellData -> cellData.getValue().ramProperty());
        clrom.setCellValueFactory(cellData -> cellData.getValue().romProperty());
        clbrand.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        clgraphic.setCellValueFactory(cellData -> cellData.getValue().graphicProperty());
        clserie.setCellValueFactory(cellData -> cellData.getValue().numserieProperty());
        clprix.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
    }

    private void selectRecords() {
        DatabaseConnection connectNow = new DatabaseConnection();
        try (Connection connectDB = connectNow.getConnection()) {
            String query = "SELECT ido, nom, ram, rom, brand, graphic, numserie, prix FROM ordinateur";
            try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<Ordinateur> data = FXCollections.observableArrayList();

                    while (resultSet.next()) {
                        int ido = resultSet.getInt("ido");
                        String nom = resultSet.getString("nom");
                        String ram = resultSet.getString("ram");
                        String rom = resultSet.getString("rom");
                        String brand = resultSet.getString("brand");
                        String graphic = resultSet.getString("graphic");
                        String numserie = resultSet.getString("numserie");
                        String prix = resultSet.getString("prix");

                        data.add(new Ordinateur(ido, nom, ram, rom, brand, graphic, numserie, prix));
                    }

                    tableo.setItems(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de base de données", "Erreur lors de la récupération des données depuis la base de données.");
        }
    }

    private void insertOrdinateurToDatabase(int ido, String nom, String ram, String rom, String brand, String graphic, String numserie, String prix) {
        String query = "INSERT INTO ordinateur (ido, nom, ram, rom, brand, graphic, numserie, prix) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connectDB = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setInt(1, ido);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, ram);
            preparedStatement.setString(4, rom);
            preparedStatement.setString(5, brand);
            preparedStatement.setString(6, graphic);
            preparedStatement.setString(7, numserie);
            preparedStatement.setString(8, prix);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de base de données", "Erreur lors de l'insertion dans la base de données.");
        }
    }

    private void deleteOrdinateurFromDatabase(int ido) {
        String query = "DELETE FROM ordinateur WHERE ido = ?";

        try (Connection connectDB = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setInt(1, ido);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de base de données", "Erreur lors de la suppression dans la base de données.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
