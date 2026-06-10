package com.example.projet_oop_rogue.controllers;

import com.example.projet_oop_rogue.core.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Controler du losing screen qui indique que le joueur est mort
 */
public class GameOverBattleController extends Game{
    @FXML
    private Label lbl_floor;

    /**
     * Méthode au démarage de la scene qui affiche les résultat de la run
     */
    public void initialize(){
        lbl_floor.setText("                         tu es arrivé aux " + (current_floor -1) + " étages \n                          ton score est : " + curremt_score);
    }

    /**
     * Bouton qui renvoit a la page de création du personnage
     * @throws IOException
     */
    @FXML
    protected void on_btn_restart() throws IOException {
        ScenesController.switchScene("/com/example/projet_oop_rogue/fxml/WelcomePage.fxml");
        //sceneController.switchGame();
    }
}
