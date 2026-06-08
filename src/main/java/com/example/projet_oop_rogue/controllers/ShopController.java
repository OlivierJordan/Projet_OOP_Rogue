package com.example.projet_oop_rogue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShopController {

    @FXML
    private Label goldLabel;
    @FXML
    private Label messageLabel;

    // Variables temporaires pour simuler l'économie (à lier à ta vraie classe Joueur plus tard)
    private int playerGold = 100;

    @FXML
    public void initialize() {
        updateGoldDisplay();
    }

    @FXML
    private void buyPotion() {
        if (playerGold >= 20) {
            playerGold -= 20;
            messageLabel.setText("Excellente infusion ! Vos HP sont restaurés.");
            messageLabel.setTextFill(javafx.scene.paint.Color.web("#2ecc71"));
            updateGoldDisplay();
            // TODO : Ajouter la logique pour soigner le vrai joueur ici
        } else {
            showError();
        }
    }

    @FXML
    private void buySword() {
        if (playerGold >= 50) {
            playerGold -= 50;
            messageLabel.setText("Une lame tranchante. Vos dégâts augmentent !");
            messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
            updateGoldDisplay();
            // TODO : Ajouter la logique pour augmenter les dégâts du vrai joueur ici
        } else {
            showError();
        }
    }

    private void showError() {
        messageLabel.setText("Fonds insuffisants !");
        messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
    }

    private void updateGoldDisplay() {
        goldLabel.setText("Votre Or : " + playerGold + " \uD83E\uDE99");
    }

    @FXML
    private void closeShop() {
        // Récupère la fenêtre (Stage) actuelle à partir du bouton et la ferme
        Stage stage = (Stage) goldLabel.getScene().getWindow();
        stage.close();
    }
}
