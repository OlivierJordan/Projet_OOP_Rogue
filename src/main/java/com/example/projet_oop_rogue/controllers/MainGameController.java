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

    // Configuration de la grille d'ffichage pour le MainGame
    private static final int GRID_WIDTH = 15;  // Nombre de colonnes (axe X)
    private static final int GRID_HEIGHT = 10; // Nombre de lignes (axe Y)
    private static final int TILE_SIZE = 50;   // Taille de chaque case en pixels (50x50)

    // Entités sur la carte
    private int playerX; // Position actuelle du joueur sur l'axe X (Colonnes)
    private int playerY; // Position actuelle du joueur sur l'axe Y (Lignes)
    private javafx.scene.image.ImageView playerSprite; // L'image physique sur la grille


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

        // 1. Appelle méthode pour la génération visuelle de la carte (Le sol)
        generateDungeon();

        // 2. Appelle méthode pour l'apparition du joueur
        spawnPlayer();

        // 3. Appelle méthode pour l'activation des contrôles clavier
        setupControls();



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

    // ========================================================================
    // 6. GÉNÉRATION DE LA CARTE (MOTEUR 2D)
    // ========================================================================

    /**
     * Génère la grille visuelle du donjon de manière dynamique.
     * Utilise une double boucle (X, Y) pour remplir le GridPane avec des cases (StackPane).
     */
    private void generateDungeon() {
        // 1. Nettoyage de sécurité : on vide la grille au cas où elle contiendrait déjà des éléments
        gameBoard.getChildren().clear();

        // 2. Parcours mathématique de la matrice (Lignes d'abord, puis Colonnes)
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {

                // 3. Création de la case (tuile)
                javafx.scene.layout.StackPane tile = new javafx.scene.layout.StackPane();

                // 4. Forcer la taille de la case pour avoir un carré parfait
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                tile.setMinSize(TILE_SIZE, TILE_SIZE);
                tile.setMaxSize(TILE_SIZE, TILE_SIZE);

                // 5. Stylisation temporaire via CSS (Couleur de fond et bordure pour voir le quadrillage)
                // Plus tard, nous remplacerons cela par des ImageView pour le sol
                tile.setStyle("-fx-background-color: #34495e; -fx-border-color: #2c3e50; -fx-border-width: 1px;");

                // 6. Ajout de la case dans la grille graphique
                // ATTENTION : En JavaFX, la méthode add() prend les arguments dans l'ordre (Colonne X, Ligne Y)
                gameBoard.add(tile, x, y);
            }
        }
    }

    // ========================================================================
    // 7. GESTION DES ENTITÉS (JOUEUR & ENNEMIS)
    // ========================================================================

    /**
     * Initialise l'image du joueur et le place sur la carte.
     */
    private void spawnPlayer() {
        // 1. Récupération dynamique de l'image (on convertit le nom de la classe en minuscules)
        String fileName = heroClass.toLowerCase() + ".png";

        try {
            String imagePath = getClass().getResource("/com/example/projet_oop_rogue/assets/characters/" + fileName).toExternalForm();
            playerSprite = new javafx.scene.image.ImageView(new javafx.scene.image.Image(imagePath));

            // 2. Ajustement de la taille de l'image pour qu'elle rentre dans la case (légèrement plus petite que TILE_SIZE)
            playerSprite.setFitWidth(40);
            playerSprite.setFitHeight(40);
            playerSprite.setPreserveRatio(true);

            // 3. Définition des coordonnées de départ (au centre mathématique de la grille)
            playerX = GRID_WIDTH / 2;
            playerY = GRID_HEIGHT / 2;

            // 4. Ajout de l'image dans le GridPane par-dessus le sol
            gameBoard.add(playerSprite, playerX, playerY);

        } catch (Exception e) {
            System.err.println("Erreur au chargement du sprite du joueur : " + fileName);
        }
    }

    /**
     * Tente de déplacer le joueur selon des vecteurs de direction (dx, dy).
     * @param dx Déplacement sur l'axe X (Colonnes : -1 gauche, 1 droite)
     * @param dy Déplacement sur l'axe Y (Lignes : -1 haut, 1 bas)
     */
    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        // 1. Détection de collision : On vérifie que la nouvelle case est bien dans les limites de la matrice
        if (newX >= 0 && newX < GRID_WIDTH && newY >= 0 && newY < GRID_HEIGHT) {

            // 2. Validation mathématique : mise à jour des coordonnées internes
            playerX = newX;
            playerY = newY;

            // 3. Mise à jour visuelle : on déplace l'image existante dans la nouvelle case du GridPane
            javafx.scene.layout.GridPane.setColumnIndex(playerSprite, playerX);
            javafx.scene.layout.GridPane.setRowIndex(playerSprite, playerY);

        } else {
            // Le joueur essaie de sortir de la carte
            logAction("Le mur du donjon vous bloque le passage !");
        }
    }

    /**
     * Configure l'écouteur d'événements clavier sur la grille de jeu.
     */
    // Ancienne version de la methode setup setupControls() -> BUG 1 !!!
    /*
    private void setupControls() {
        // Autorise la grille à recevoir le focus (indispensable pour capter le clavier)
        gameBoard.setFocusTraversable(true);

        // Définition des actions pour chaque touche pressée
        gameBoard.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:    movePlayer(0, -1); break;
                case DOWN:  movePlayer(0, 1); break;
                case LEFT:  movePlayer(-1, 0); break;
                case RIGHT: movePlayer(1, 0); break;
                default: break; // On ignore les autres touches
            }
        });

        // Astuce technique : on force JavaFX à mettre le focus sur la grille une fois la fenêtre chargée
        javafx.application.Platform.runLater(() -> gameBoard.requestFocus());
    }
    */

    /**
     * Configure l'écouteur d'événements clavier sur la grille de jeu.
     *
     * BUG 1 : !!! INCLUT LA GESTION STRICTE DU FOCUS POUR EVITER LE BLOCAGE DES CONTROLES CLAVIERS !!!
     */
    private void setupControls() {
        // 1. Autorise la grille à recevoir le focus
        gameBoard.setFocusTraversable(true);

        // BUG 1 : !!! 2. On interdit à la console de texte et aux labels de "voler" le focus !!!
        if (battleLogs != null) {
            // 1. Bloque la navigation au clavier vers cet élément
            battleLogs.setFocusTraversable(false);

            // 2. Bloque la capture du focus par clic de souris
            // On écoute le changement d'état du focus de la zone de texte
            battleLogs.focusedProperty().addListener((observable, oldValue, newValue) -> {
                // Si newValue est 'true', la zone vient d'être cliquée
                if (newValue) {
                    // On renvoie immédiatement le focus à la grille du jeu !
                    gameBoard.requestFocus();
                }
            });
        }

        // 3. Définition des actions pour chaque touche pressée
        gameBoard.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:    movePlayer(0, -1); break;
                case DOWN:  movePlayer(0, 1); break;
                case LEFT:  movePlayer(-1, 0); break;
                case RIGHT: movePlayer(1, 0); break;
                default: return; // Si c'est une autre touche (ex: Espace), on arrête la méthode ici
            }

            // BUG 1 : !!! 4. Consommation de l'événement !!!
            // Cela indique à JavaFX : "J'ai utilisé cette frappe de clavier, ne la transmets pas au reste de l'interface"
            event.consume();
        });

        // 5. On force JavaFX à mettre le focus sur la grille une fois la fenêtre chargée
        javafx.application.Platform.runLater(() -> gameBoard.requestFocus());

        // BUG 1 : !!! 6. Sécurité supplémentaire si le joueur clique ailleurs avec sa souris !!!
        // Si on clique n'importe où sur la carte, on redonne le focus à la grille
        gameBoard.setOnMouseClicked(event -> gameBoard.requestFocus());
    }


}



