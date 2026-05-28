module com.example.projet_oop_rogue {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projet_oop_rogue to javafx.fxml;
    exports com.example.projet_oop_rogue;
}