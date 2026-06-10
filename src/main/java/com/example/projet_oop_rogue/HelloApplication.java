package com.example.projet_oop_rogue;

import com.example.projet_oop_rogue.controllers.sceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        sceneController.setStage(stage);
        // 1. On pointe vers le chemin absolu de ta nouvelle WelcomePage
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/projet_oop_rogue/fxml/games/first_screen.fxml"));        // 2. On ajuste la taille de la fenêtre pour correspondre à notre HBox (800x600)
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        // 3. On met un titre professionnel à la fenêtre
        stage.setTitle("Rogue-like - Start");
        stage.setScene(scene);
        stage.show();
    }
}
