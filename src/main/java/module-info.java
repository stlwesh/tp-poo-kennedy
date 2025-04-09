module com.example.projet2poo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;


    opens controller to javafx.fxml;
    opens modele to javafx.base;

    exports Applications;
}