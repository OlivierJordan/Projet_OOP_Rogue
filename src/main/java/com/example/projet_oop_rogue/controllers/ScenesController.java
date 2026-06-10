package com.example.projet_oop_rogue.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe qui contient tout les méthode pour changer de scene
 */
public class ScenesController {
        private static Stage stage;

    /**
     * Méthode qui configure la classe
     * @param primaryStage
     */
    public static void setStage(Stage primaryStage) {
            stage = primaryStage;
        }

    /**
     * Méthode qui change de scene
     * @param fxmlFile : choisie la scene a afficher
     */
    public static void switchScene(String fxmlFile) {

            try {

                FXMLLoader loader = new FXMLLoader(
                        ScenesController.class.getResource(fxmlFile)
                );

                Parent root = loader.load();

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Méthode qui chage de jeu pour basculer vers le jeu secondaire
     */
    public static void switchGame(){
            try {

                FXMLLoader loader = new FXMLLoader(
                        ScenesController.class.getResource("/com/example/projet_oop_rogue/fxml/games/seconde_game.fxml")
                );

                Parent root = loader.load();

                Scene scene = new Scene(root);

                // Récupération automatique du contrôleur pour lui lier les événements (Souris et Clavier)
                PongGameController controller = loader.getController();
                controller.setupInputs(scene);

                stage.setTitle("Bonus game");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void openShop(){
        try {

            FXMLLoader loader = new FXMLLoader(
                    ScenesController.class.getResource("/com/example/projet_oop_rogue/fxml/ShopPage.fxml")
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Récupération automatique du contrôleur pour lui lier les événements (Souris et Clavier)
            ShopController controller = loader.getController();

            stage.setTitle("Shop");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void go_to_lobby(){
        try {
            // 1. Initialiser le chargeur FXML avec le chemin vers la vue du jeu principal
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(ScenesController.class.getResource("/com/example/projet_oop_rogue/fxml/games/MainGame.fxml"));

            // 2. Charger l'arbre des composants (la vue racine)
            javafx.scene.Parent root = loader.load();

            // 3. Récupérer l'instance du contrôleur qui vient d'être créé par le FXMLLoader
            MainGameController gameController = loader.getController();
            // 6. Créer une nouvelle scène avec la vue chargée, et l'appliquer à la fenêtre
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.setTitle("Rogue-like - En jeu");
            stage.show();

        } catch (java.io.IOException e) {
            // Gestion de l'exception obligatoire lors du chargement de fichiers externes
            System.err.println("Erreur critique : Impossible de charger MainGame.fxml");
            e.printStackTrace();
        }
    }

}
