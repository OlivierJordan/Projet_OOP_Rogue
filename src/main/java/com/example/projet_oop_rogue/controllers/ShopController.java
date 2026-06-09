package com.example.projet_oop_rogue.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class ShopController extends com.example.projet_oop_rogue.controllers.Game {

    @FXML
    private Label goldLabel;
    @FXML
    private Label messageLabel;

    private MainGameController mainController;

    public void setMainController(MainGameController mainController) {
        this.mainController = mainController;
        updateGoldDisplay();
    }

    @FXML
    private void buyPotion() {
        // On continue d'utiliser le mainController pour modifier les variables de la carte !
        if (mainController != null && mainController.getPlayerGold() >= 20) {
            mainController.setPlayerGold(mainController.getPlayerGold() - 20);

            int nouveauxHP = Math.min(mainController.getPlayerMaxHP(), mainController.getPlayerHP() + 50);
            mainController.setPlayerHP(nouveauxHP);

            mainController.updatePlayerStatsUI();

            messageLabel.setText("Excellente infusion ! Vos HP sont restaurés.");
            messageLabel.setTextFill(javafx.scene.paint.Color.web("#2ecc71"));
            updateGoldDisplay();
        } else {
            showError();
        }
    }

    @FXML
    private void buySword() {
        // 1. On vérifie sur la carte principale si le joueur a assez d'or
        if (mainController != null && mainController.getPlayerGold() >= 50) {

            // 2. On soustrait l'or sur la carte principale
            mainController.setPlayerGold(mainController.getPlayerGold() - 50);

            // 3. On ajoute les dégâts sur la carte principale
            mainController.setPlayerDamage(mainController.getPlayerDamage() + 15);

            // 4. On met à jour l'interface de la carte en arrière-plan
            mainController.updatePlayerStatsUI();

            // 5. On met à jour l'interface de la boutique
            messageLabel.setText("Une lame tranchante. Vos dégâts augmentent !");
            messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
            updateGoldDisplay();

        } else {
            showError();
        }
    }

    private void showError() {
        messageLabel.setText("Fonds insuffisants !");
        messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
    }

    private void updateGoldDisplay() {
        if (mainController != null) {
            goldLabel.setText("Votre Or : " + mainController.getPlayerGold() + " Or");
        }
    }

    @FXML
    private void closeShop() {
        // Récupère la fenêtre (Stage) actuelle à partir du bouton et la ferme
        Stage stage = (Stage) goldLabel.getScene().getWindow();
        stage.close();
    }
}