package controller;

import dao.MyJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.Objet;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListeObjetsController implements Initializable {
    @FXML private TableView<Objet> tableObjets;
    @FXML private TableColumn<Objet, String> colId;
    @FXML private TableColumn<Objet, String> colType;
    @FXML private TableColumn<Objet, String> colMarque;
    @FXML private TableColumn<Objet, String> colModele;
    @FXML private TableColumn<Objet, String> colProprietaire;

    private MyJDBC dbManager = new MyJDBC();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurer les colonnes de la table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type_appareil"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        colProprietaire.setCellValueFactory(new PropertyValueFactory<>("nom_proprietere"));

        // Charger les données
        chargerDonnees();
    }

    @FXML
    void rafraichirListe(ActionEvent event) {
        chargerDonnees();
    }

    private void chargerDonnees() {
        List<Objet> objetsVoles = dbManager.getObjetsVoles();
        ObservableList<Objet> data = FXCollections.observableArrayList(objetsVoles);
        tableObjets.setItems(data);
    }

    @FXML
    void retourAccueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Page_Accueil.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible de charger la page d'accueil.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
