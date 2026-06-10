package com.example.projet_oop_rogue.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controler de la première scene du jeux
 */
public class FirstScreenControler {
    /**
     * Bouton qui lance le jeu
     * @throws IOException
     */
    @FXML
    protected void on_btn_start() throws IOException {
        ScenesController.switchScene("/com/example/projet_oop_rogue/fxml/WelcomePage.fxml");
    }
}
