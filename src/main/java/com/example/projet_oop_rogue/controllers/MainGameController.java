package com.example.projet_oop_rogue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class MainGameController {

    // ========================================================================
    // 1. DÉCLARATION DES VARIABLES FXML (INTERFACE GRAPHIQUE)
    // ========================================================================
    // Connexion aux éléments de MainGame.fxml via leurs identifiants fx:id

    @FXML
    private Label playerInfoLabel; // Label pour affichage nom et classe du joueur

    @FXML
    private Label hpLabel; // Label pour affichage points de vie (HP) actuels et maximums

    @FXML
    private Label damageLabel; // Label pour affichage des dégâts de base de la classe

    @FXML
    private TextArea battleLogs; // Console textuelle pour l'historique des actions (lecture seule)

    @FXML
    private GridPane gameBoard; // Grille centrale pour la carte du donjon

    // ========================================================================
    // 2. VARIABLES DE LOGIQUE INTERNE (ÉTAT DU JEU)
    // ========================================================================

    private String playerName; // Variable pour stocker nom du joueur (reçu de l'écran d'accueil)
    private String heroClass; // Variable pour stocker la classe choisie : Mage, Chevalier ou Voleur

    // ========================================================================
    // 3. MÉTHODES D'INITIALISATION ET DE TRANSFERT DE DONNÉES
    // ========================================================================


    // Ancienne fonction initData() :
    // sans le MainGame et juste avec les entrées utilisateurs de la WelcomePage

//    /*
//     * Méthode appelée par la WelcomePage pour injecter les données du joueur
//     * AVANT que la scène ne soit affichée à l'écran.
//     * * @param name Le nom tapé par le joueur
//     * @param heroClass La classe choisie (Mage, Chevalier, Voleur)
//     */
//    public void initData(String name, String heroClass) {
//        this.playerName = name;
//        this.heroClass = heroClass;
//
//        // On met à jour l'interface du jeu principal immédiatement
//        if (playerInfoLabel != null) {
//            playerInfoLabel.setText("Joueur : " + playerName + " | Classe : " + heroClass);
//        }
//
//        System.out.println("Données reçues dans MainGame : " + playerName + " (" + heroClass + ")");
//
//        // C'est ici que tu pourras instancier tes vrais objets métiers (ex: new Mage(playerName))
//    }


    // Nouvelle fonction initData() :
    // Avec MainGame

    /**
     * Initialisation des données du jeu transmises par le WelcomePageController.
     * Exécutée explicitement par le contrôleur précédent juste avant l'affichage de cette scène.
     * * @param name Nom saisi par le joueur.
     * @param heroClass Classe sélectionnée (Mage, Chevalier, Voleur).
     */
    public void initData(String name, String heroClass) {
        this.playerName = name;
        this.heroClass = heroClass;

        // Rafraîchissement immédiat de l'interface avec les nouvelles données
        updateUI();

        // Ajout d'un premier message d'ambiance dans la console de combat
        logAction(this.playerName + " le " + this.heroClass + " entre dans le donjon !");
    }

    // ========================================================================
    // 4. MÉTHODES DE MISE À JOUR DE L'INTERFACE (UI)
    // ========================================================================

    /**
     * Mise à jour de l'interface utilisateur (Labels) en fonction des statistiques en cours.
     * Isole la logique d'affichage pour pouvoir être appelée à tout moment (ex: après avoir pris un coup).
     */
    private void updateUI() {
        // Sécurisation (Null Check) : vérification de l'existence du label dans le FXML
        if (playerInfoLabel != null) {
            playerInfoLabel.setText("Joueur: " + playerName + "\nClasse: " + heroClass);
        }

        // TODO : Remplacement futur de ces valeurs fixes par des appels dynamiques
        // aux méthodes (ex: myHero.getHp(), myHero.getDamage()) des objets créés par tes coéquipiers.
        if ("Mage".equals(heroClass)) {
            hpLabel.setText("HP : 80 / 80");
            damageLabel.setText("Dégâts : 25");
        } else if ("Chevalier".equals(heroClass)) {
            hpLabel.setText("HP : 150 / 150");
            damageLabel.setText("Dégâts : 15");
        } else if ("Voleur".equals(heroClass)) {
            hpLabel.setText("HP : 100 / 100");
            damageLabel.setText("Dégâts : 20");
        }
    }

    // ========================================================================
    // 5. MÉTHODES UTILITAIRES (MOTEUR DE JEU)
    // ========================================================================

    /**
     * Ajout d'un nouveau message textuel dans la console d'historique des combats.
     * * @param message Le texte descriptif de l'action à afficher.
     */
    public void logAction(String message) {
        // Sécurisation (Null Check) : vérification de l'existence de la zone de texte
        if (battleLogs != null) {
            // appendText() ajoute le texte à la suite de l'existant sans l'écraser
            // L'ajout de "\n" crée un saut de ligne automatique pour chaque nouvelle action
            battleLogs.appendText("\n> " + message);
        }
    }
}



