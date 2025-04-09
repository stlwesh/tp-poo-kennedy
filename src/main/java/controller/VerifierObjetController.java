package controller;

import dao.MyJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Objet;

import java.io.IOException;

public class VerifierObjetController {
    @FXML private TextField searchIdField;
    @FXML private VBox resultContainer;
    @FXML private Label idLabel;
    @FXML private Label typeLabel;
    @FXML private Label marqueLabel;
    @FXML private Label modeleLabel;
    @FXML private Label proprietaireLabel;
    @FXML private Label statutLabel;

    private MyJDBC dbManager = new MyJDBC();

    @FXML
    void verifierObjet(ActionEvent event) {
        String id = searchIdField.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un numéro de série/ID.");
            return;
        }

        Objet objet = dbManager.rechercherParId(id);

        if (objet != null) {
            // Afficher les détails de l'objet
            idLabel.setText(objet.getId());
            typeLabel.setText(objet.getType_appareil());
            marqueLabel.setText(objet.getMarque());
            modeleLabel.setText(objet.getModele());
            proprietaireLabel.setText(objet.getNom_proprietere());

            if (objet.getEst_Vole()) {
                statutLabel.setText("VOLÉ");
                statutLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                statutLabel.setText("Non signalé comme volé");
                statutLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            }

            resultContainer.setVisible(true);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information",
                    "Aucun objet trouvé avec cet ID/numéro de série.");
            resultContainer.setVisible(false);
        }
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
