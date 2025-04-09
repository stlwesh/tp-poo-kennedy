package Applications;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.MyJDBC;
public class MainApplication extends Application {private MyJDBC dbManager;
    @Override

    public void start(Stage stage) throws Exception {
        dbManager = new MyJDBC();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Page_Accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("StopVolApp - Identifiez les objets volés");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() {
        // Fermer proprement la connexion à la fin
        if (dbManager != null) {
            dbManager.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
