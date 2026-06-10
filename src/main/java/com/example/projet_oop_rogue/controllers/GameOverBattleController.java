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

        // TODO : Ecriture du score dans le fichier

        // Appelle methode pour sauvegarder le score dans le fichier (écriture) dès que l'écran de fin s'affiche
        sauvegarderScore();
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

    /**
     * Méthode qui écrit les statistiques de la partie dans un fichier texte (CSV)
     */
    private void sauvegarderScore() {

        // 1. Préparation des données (Format : Nom,Score,Etage)
        String nomJoueur = (main_character != null) ? main_character.get_name() : "Joueur Inconnu";

        // On récupère le nom de la classe (Mage, Chevalier, Voleur)
        String classeHeros = (main_character != null) ? main_character.getClass().getSimpleName() : "Inconnu";

        // On ajoute les données dans notre ligne CSV
        String ligneCsv = nomJoueur + "," + curremt_score + "," + (current_floor - 1) + "," + classeHeros;

        // 2. Écriture dans le fichier
        // Le paramètre 'true' dans FileWriter est crucial : il active le mode "Append"
        // pour ajouter la ligne à la fin du fichier sans effacer les scores précédents !
        try (java.io.FileWriter fw = new java.io.FileWriter("leaderboard.csv", true);
             java.io.PrintWriter pw = new java.io.PrintWriter(fw)) {

            pw.println(ligneCsv); // Écrit la ligne et passe à la ligne suivante

        } catch (java.io.IOException e) {
            System.out.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
        }
    }

}
