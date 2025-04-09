package controller;
import dao.MyJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Objet;

import java.io.IOException;

    public class DeclarerObjetController {
        @FXML private TextField idField;
        @FXML private TextField typeField;
        @FXML private TextField marqueField;
        @FXML private TextField modeleField;
        @FXML private TextField proprietaireField;

        private MyJDBC dbManager = new MyJDBC();

        @FXML
        void declarerObjet(ActionEvent event) {
            if (validateFields()) {
                String id = idField.getText().trim();
                String type = typeField.getText().trim();
                String marque = marqueField.getText().trim();
                String modele = modeleField.getText().trim();
                String proprietaire = proprietaireField.getText().trim();

                // Vérifier si l'objet existe déjà
                Objet existingObjet = dbManager.rechercherParId(id);

                if (existingObjet != null) {
                    // Si l'objet existe, le marquer comme volé
                    boolean success = dbManager.marquerCommeVole(id);
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Succès",
                                "L'objet a été marqué comme volé dans la base de données.");
                        clearFields();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur",
                                "Impossible de mettre à jour le statut de l'objet.");
                    }
                } else {
                    // Sinon, créer un nouvel objet et l'ajouter comme volé
                    Objet nouvelObjet = new Objet(id, modele, proprietaire, type, marque, true);
                    boolean success = dbManager.ajouterObjet(nouvelObjet);

                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Succès",
                                "L'objet a été enregistré comme volé dans la base de données.");
                        clearFields();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur",
                                "Impossible d'ajouter l'objet à la base de données.");
                    }
                }
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

        private boolean validateFields() {
            if (idField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID/numéro de série est obligatoire.");
                return false;
            }
            if (typeField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le type d'appareil est obligatoire.");
                return false;
            }
            if (proprietaireField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom du propriétaire est obligatoire.");
                return false;
            }
            return true;
        }

        private void clearFields() {
            idField.clear();
            typeField.clear();
            marqueField.clear();
            modeleField.clear();
            proprietaireField.clear();
        }

        private void showAlert(Alert.AlertType type, String title, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

