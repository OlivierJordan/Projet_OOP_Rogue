package com.example.projet_oop_rogue.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controler de la première scene du jeux
 */
public class first_screen_controler {
    /**
     * Bouton qui lance le jeu
     * @throws IOException
     */
    @FXML
    protected void on_btn_start() throws IOException {
        sceneController.switchScene("/com/example/projet_oop_rogue/fxml/WelcomePage.fxml");
    }
}
