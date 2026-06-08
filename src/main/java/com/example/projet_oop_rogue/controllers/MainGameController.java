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

    // Configuration de la grille d'affichage V.1.0 pour le MainGame
/*
    private static final int GRID_WIDTH = 15;  // Nbr total de colonnes (axe X)
    private static final int GRID_HEIGHT = 10; // Nbr total de lignes (axe Y)
    private static final int TILE_SIZE = 50;   // Taille de chaque case en pixels (50x50)
*/

    // Configuration de la grille d'affichage V.2.0 pour le MainGame
    private static final int GRID_WIDTH = 20;  // <-- Doit être à 20 (et non plus 15)
    private static final int GRID_HEIGHT = 13; // <-- Doit être à 13 (et non plus 10)
    private static final int TILE_SIZE = 38;   // <-- Doit être à 38 (et non plus 50)

    // Entités sur la carte
    private int playerX; // Position actuelle du joueur sur l'axe X (Colonnes)
    private int playerY; // Position actuelle du joueur sur l'axe Y (Lignes)
    private javafx.scene.image.ImageView playerSprite; // L'image physique sur la grille

/*
    // Partie optionnelle pour la génération purement aléatoire d'obstacles sur la map

    // Tableau booléen pour la gestion des obstacles
    private static final int OBSTACLE_COUNT = 25; // Nombre de murs à générer
    private boolean[][] obstacleGrid; // Matrice logique : true = mur / false = vide
*/

/*
    // Partie pour la génération par blocs d'obstacles sur la map

    private static final int MAX_ROOMS = 4; // Static pour le nbr max de salles
    private static final int MIN_ROOM_SIZE = 3; // Static pour la taille min des salles
    private static final int MAX_ROOM_SIZE = 5; // Static pour la taille max des salles
    private boolean[][] obstacleGrid; // Tableau booléen pour la gestion des obstacles
*/

    // Génération de la map sans zones protégées (3)
/*
    private static final int RANDOM_OBSTACLES_COUNT = 6; // Définit le nombre de petits obstacles aléatoires à ajouter
    private int[][] obstacleGrid; // Matrice logique 2D mémorisant les collisions : 0 = vide, 1 = mur fixe, 2 = rocher
    // Matrice représentant le "Level Design" fixe (2 = rocher, 1 = mur infranchissable, 0 = espace libre)
    private final int[][] worldMap = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Ligne 0 : Mur de délimitation supérieur
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // Ligne 1 : Arène fermée à gauche (Pong)
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // Ligne 2 : Arène fermée à gauche (Pong)
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1}, // Ligne 3 : Ouverture d'accès et séparation centrale
            {1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1}, // Ligne 4 : Couloir étroit
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // Ligne 5 : Route principale dégagée (Zone de Spawn)
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1}, // Ligne 6 : Murs fragmentés pour abriter des ennemis
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1}, // Ligne 7 : Couloirs verticaux
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1}, // Ligne 8 : Couloirs verticaux
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}  // Ligne 9 : Mur de délimitation inférieur
    };
*/

    // Génération de la map avec zones protégées (3)
    private static final int RANDOM_OBSTACLES_COUNT = 8; // On peut mettre un peu plus de rochers car la carte est plus grande
    private int[][] obstacleGrid; // Matrice logique 2D mémorisant les collisions : 0 = vide, 1 = mur fixe, 2 = rocher, 3 = zone protégée

    /**
     * Carte du monde (Macro-Level Design) :
     * 0 = Espace libre (Rochers aléatoires autorisés)
     * 1 = Mur structurel infranchissable (Gris foncé)
     * 3 = Zone Protégée (Espace libre, mais rochers aléatoires INTERDITS)
     */
    // Matrice représentant le "Level Design" fixe (3 = zone protégée, 2 = rocher, 1 = mur infranchissable, 0 = espace libre)
    /**
     * Carte du monde (Macro-Level Design) :
     * 0 = Espace libre (Rochers aléatoires autorisés)
     * 1 = Mur structurel infranchissable (Gris foncé)
     * 3 = Chemin protégé (Espace libre, rochers aléatoires INTERDITS)
     * 4 = Ennemi (Point d'interaction de combat)
     * 5 = Mini-Jeu (Point d'interaction Pong)
     * 6 = Magasin (Point d'interaction Boutique) <-- NOUVEAU
     */
    private final int[][] worldMap = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0}, // Ligne 1 : Pong à gauche, Ennemi 1 à droite
            {0, 3, 3, 3, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 3, 3, 0},
            {0, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 0, 1, 4, 3, 3, 1, 0, 0, 1, 1, 1, 1, 0}, // Ligne 4 : Ennemi 2 dans la structure centrale
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Ligne 5 : Spawn Joueur au centre
            {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 1, 4, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 4, 3, 1, 0}, // Ligne 7 : Ennemi 3 (gauche) et Ennemi 4 (droite)
            {0, 0, 1, 3, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 3, 1, 0},
            {0, 0, 3, 3, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 3, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Ligne 10 : Ennemi 5 (bas centre)
            {0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    // Classe pour la génération par blocs d'obstacles sur la map

    /*
     * Classe utilitaire interne pour définir la géométrie d'une salle.
     */
/*
    private static class Room {
        int x, y, width, height;

        public Room(int x, int y, int width, int height) {
            this.x = x; this.y = y; this.width = width; this.height = height;
        }

        public int getCenterX() { return x + width / 2; }
        public int getCenterY() { return y + height / 2; }

        public boolean intersects(Room other) {
            return (x - 1 <= other.x + other.width && x + width + 1 >= other.x &&
                    y - 1 <= other.y + other.height && y + height + 1 >= other.y);
        }
    }
*/


    // ========================================================================
    // 3. MÉTHODES D'INITIALISATION ET DE TRANSFERT DE DONNÉES
    // ========================================================================

    // Ancienne fonction initData() :
    // sans le MainGame et juste avec les entrées utilisateurs de la WelcomePage

    /*
     * Méthode appelée par la WelcomePage pour injecter les données du joueur
     * AVANT que la scène ne soit affichée à l'écran.
     *
     * @param name Le nom tapé par le joueur
     * @param heroClass La classe choisie (Mage, Chevalier, Voleur)
     */
/*
    public void initData(String name, String heroClass) {
        this.playerName = name;
        this.heroClass = heroClass;

        // On met à jour l'interface du jeu principal immédiatement
        if (playerInfoLabel != null) {
            playerInfoLabel.setText("Joueur : " + playerName + " | Classe : " + heroClass);
        }

        System.out.println("Données reçues dans MainGame : " + playerName + " (" + heroClass + ")");

    }
*/

    // Nouvelle fonction initData() :
    // Avec MainGame


    // !!! L'ORDRE D'APPEL DES METHODES EST TRES IMPORTANT (RENDU PAR COUCHES) !!!
    /**
     * Initialisation des données du jeu transmises par le WelcomePageController.
     * Exécutée explicitement par le contrôleur précédent juste avant l'affichage de cette scène.
     *
     * @param name Nom saisi par le joueur.
     * @param heroClass Classe sélectionnée (Mage, Chevalier, Voleur).
     */
    public void initData(String name, String heroClass) {
        this.playerName = name;
        this.heroClass = heroClass;

        // Rafraîchissement immédiat de l'interface avec les nouvelles données
        updateUI();

        // Ajout d'un premier message d'ambiance dans la console de combat
        logAction(this.playerName + " le " + this.heroClass + " entre dans le donjon !");


        // !!! L'ORDRE D'APPEL DES METHODES EST TRES IMPORTANT (RENDU PAR COUCHES) !!!

        // Couche 0 : Appelle méthode pour la génération visuelle de la carte (le sol)
        generateDungeon();
        // Couche 1 : Appelle méthode les murs (obstacles sur la map)
        generateObstacles();
        // Couche 2 : Appelle méthode pour l'apparition du joueur (récupération image)
        spawnPlayer();

        // Appelle méthode pour l'activation des contrôles clavier
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
     *
     * @param message Le texte descriptif de l'action à afficher.
     */
    public void logAction(String message) {
        // Sécurisation (Null Check) : vérification de l'existence de la zone de texte
        if (battleLogs != null) {
            // appendText() ajoute le texte à la suite de l'existant sans l'écraser
            // L'ajout de "\n" crée un saut de ligne automatique pour chaque nouvelle action
            battleLogs.appendText("\n> " + message);
        }
    }

    /**
     * Ouvre l'interface de combat sous forme de fenêtre modale superposée.
     * Interrompt l'exécution de la carte principale jusqu'à la résolution du combat.
     * En cas de victoire (fermeture de la fenêtre), l'entité ennemie est retirée de la matrice.
     *
     * @param enemyX La coordonnée X de l'ennemi sur la grille logique.
     * @param enemyY La coordonnée Y de l'ennemi sur la grille logique.
     */
    private void startBattle(int enemyX, int enemyY) {
        try {
            // 1. Chargement de la vue du combat (Il faudra créer ce fichier FXML dans le dossier approprié)
            // Assure-toi que le chemin correspond bien à l'arborescence de ton projet
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/projet_oop_rogue/fxml/games/Battle.fxml"));
            javafx.scene.Parent root = loader.load();

            // 2. Création de la nouvelle fenêtre (Stage)
            javafx.stage.Stage battleStage = new javafx.stage.Stage();
            battleStage.setTitle("⚔️ Phase de Combat !");
            battleStage.setScene(new javafx.scene.Scene(root));

            // 3. Verrouillage Modale : Empêche de cliquer sur la carte tant que le combat est ouvert
            battleStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // NOUVEAU : On interdit au joueur de réduire ou redimensionner la fenêtre de combat
            battleStage.setResizable(false);

            // 4. Affichage bloquant (Le code s'arrête ici et attend que la fenêtre battleStage soit fermée)
            battleStage.showAndWait();

            // ====================================================================
            // 5. RETOUR SUR LA CARTE : Ce code s'exécute uniquement APRÈS le combat
            // ====================================================================

            // On supprime l'ennemi de la matrice logique (la case redevient traversable)
            obstacleGrid[enemyX][enemyY] = 0;

            // Astuce visuelle : On place un carré de la couleur du sol pour "masquer" la silhouette morte
            // (Idéalement, il faudrait retirer l'image, mais c'est une rustine très efficace)
            javafx.scene.layout.StackPane emptyFloor = new javafx.scene.layout.StackPane();
            emptyFloor.setPrefSize(TILE_SIZE, TILE_SIZE);
            emptyFloor.setStyle("-fx-background-color: transparent;"); // On laisse voir le fond
            gameBoard.add(emptyFloor, enemyX, enemyY);

            if (battleLogs != null) {
                battleLogs.appendText("\n> Victoire ! L'ennemi s'est volatilisé. Le chemin est libre.");
            }

        } catch (Exception e) {
            // Gestion de l'exception si le fichier Battle.fxml n'existe pas encore
            System.err.println("Erreur critique : Fichier Battle.fxml introuvable.");
            e.printStackTrace();
            if (battleLogs != null) {
                battleLogs.appendText("\n> [Erreur Système] Impossible de charger l'arène de combat.");
            }
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

    /**
     * Instancie un obstacle physique et visuel sur la grille de jeu.
     * Cette méthode met à jour la mémoire logique des collisions et ajoute le rendu graphique.
     *
     * @param x La position en colonne (axe des abscisses) où placer l'obstacle.
     * @param y La position en ligne (axe des ordonnées) où placer l'obstacle.
     * @param colorHex Le code couleur hexadécimal de l'obstacle pour le rendu CSS (ex: "#34495e").
     * @param obstacleType L'identifiant logique (1 pour mur, 2 pour rocher).
     */
    private void placeObstacle(int x, int y, String colorHex, int obstacleType) {
        // 1. Verrouillage logique
        obstacleGrid[x][y] = obstacleType; // Enregistre le type spécifique au lieu d'un simple 'true'

        // 2. Création visuelle
        javafx.scene.layout.StackPane wall = new javafx.scene.layout.StackPane(); // Instancie un nouveau conteneur graphique pour la tuile
        wall.setPrefSize(TILE_SIZE, TILE_SIZE); // Force les dimensions préférentielles du conteneur
        wall.setStyle("-fx-background-color: " + colorHex + "; -fx-border-color: #2c3e50; -fx-border-width: 1px;"); // Applique le style CSS pour la couleur et la bordure

        // 3. Ajout dans le moteur de rendu
        gameBoard.add(wall, x, y);
    }

    /**
     * Instancie une entité interactive (Ennemi, Mini-jeu) sur la carte.
     * Charge une image PNG, ou applique un carré de couleur en cas d'absence du fichier.
     *
     * @param x Colonne de placement
     * @param y Ligne de placement
     * @param imageName Le nom du fichier image (ex: "silhouette.png")
     * @param type L'identifiant logique (4 pour ennemi, 5 pour Pong)
     * @param fallbackColor Couleur de secours si l'image manque
     */
    private void placeEntity(int x, int y, String imageName, int type, String fallbackColor) {
        obstacleGrid[x][y] = type; // Verrouillage logique de la case

        try {
            // Tente de charger l'image depuis le dossier characters
            String path = getClass().getResource("/com/example/projet_oop_rogue/assets/characters/" + imageName).toExternalForm();
            javafx.scene.image.ImageView sprite = new javafx.scene.image.ImageView(new javafx.scene.image.Image(path));

            // Redimensionnement pour s'intégrer parfaitement dans une case (TILE_SIZE = 38)
            sprite.setFitWidth(30);
            sprite.setFitHeight(30);
            sprite.setPreserveRatio(true);

            gameBoard.add(sprite, x, y);

        } catch (Exception e) {
            // Sécurité (Fallback) : Si l'image n'est pas trouvée, on dessine un bloc de couleur
            placeObstacle(x, y, fallbackColor, type);
        }
    }


    // Partie optionnelle pour la génération purement aléatoire d'obstacles sur la map
    /*
     * Génère des obstacles aléatoires sur la grille et met à jour la matrice logique.
     */
/*
    private void generateObstacles() {
        // Initialisation de la matrice (par défaut, Java remplit tout avec 'false')
        obstacleGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];

        // Utilisation de random pour faire spawn les obstacles de manière aléatoire sur la map
        java.util.Random random = new java.util.Random();

        int obstaclesPlaced = 0;

        // On calcule le centre pour protéger la zone d'apparition du joueur
        int startX = GRID_WIDTH / 2;
        int startY = GRID_HEIGHT / 2;

        // On boucle tant qu'on n'a pas posé le bon nombre de murs
        while (obstaclesPlaced < OBSTACLE_COUNT) {
            int rx = random.nextInt(GRID_WIDTH);
            int ry = random.nextInt(GRID_HEIGHT);

            // On vérifie que la case est vide ET que ce n'est pas la position de départ
            if (!obstacleGrid[rx][ry] && !(rx == startX && ry == startY)) {

                // 1. Verrouillage logique : on marque la case comme occupée
                obstacleGrid[rx][ry] = true;
                obstaclesPlaced++;

                // 2. Création visuelle : on fabrique la tuile du mur
                javafx.scene.layout.StackPane wall = new javafx.scene.layout.StackPane();
                wall.setPrefSize(TILE_SIZE, TILE_SIZE);

                // Style temporaire gris foncé (tu pourras y mettre une ImageView plus tard)
                wall.setStyle("-fx-background-color: #7f8c8d; -fx-border-color: #2c3e50; -fx-border-width: 1px;");

                // 3. Ajout dans la grille par-dessus le sol
                gameBoard.add(wall, rx, ry);
            }
        }
    }
*/

    // Partie pour la génération par blocs d'obstacles sur la map
    /*
     * Génère un donjon structuré en créant des pièces et des couloirs.
     */
    /*
    private void generateObstacles() {
        obstacleGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];

        // 1. État initial : La grille entière est un mur massif (true)
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                obstacleGrid[x][y] = true;
            }
        }

        java.util.List<Room> rooms = new java.util.ArrayList<>();
        java.util.Random random = new java.util.Random();

        // 2. Tentatives de placement de pièces
        for (int i = 0; i < 20; i++) { // On essaie 20 fois au maximum
            if (rooms.size() >= MAX_ROOMS) break; // On s'arrête si on a assez de pièces

            // Dimensions et positions aléatoires
            int w = random.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1) + MIN_ROOM_SIZE;
            int h = random.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1) + MIN_ROOM_SIZE;
            int x = random.nextInt(GRID_WIDTH - w - 1) + 1;
            int y = random.nextInt(GRID_HEIGHT - h - 1) + 1;

            Room newRoom = new Room(x, y, w, h);

            // Vérification de collision avec les pièces existantes
            boolean failed = false;
            for (Room other : rooms) {
                if (newRoom.intersects(other)) {
                    failed = true;
                    break;
                }
            }

            // Si la place est libre, on creuse la pièce
            if (!failed) {
                // Creuser le vide (false) pour la pièce
                for (int rx = x; rx < x + w; rx++) {
                    for (int ry = y; ry < y + h; ry++) {
                        obstacleGrid[rx][ry] = false;
                    }
                }

                // Relier la pièce ou placer le joueur
                if (rooms.isEmpty()) {
                    // C'est la toute première salle : c'est ici que le joueur doit apparaître !
                    playerX = newRoom.getCenterX();
                    playerY = newRoom.getCenterY();
                } else {
                    // Ce n'est pas la première salle : on creuse un couloir depuis la salle précédente
                    Room prev = rooms.get(rooms.size() - 1);
                    carveCorridor(prev.getCenterX(), prev.getCenterY(), newRoom.getCenterX(), newRoom.getCenterY());
                }
                rooms.add(newRoom);
            }
        }

        // 3. Affichage graphique : on ne dessine que les cases restées à 'true' (les murs)
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                if (obstacleGrid[x][y]) {
                    javafx.scene.layout.StackPane wall = new javafx.scene.layout.StackPane();
                    wall.setPrefSize(TILE_SIZE, TILE_SIZE);
                    wall.setStyle("-fx-background-color: #7f8c8d; -fx-border-color: #2c3e50; -fx-border-width: 1px;");
                    gameBoard.add(wall, x, y);
                }
            }
        }
    }
    */

    /*
     * Creuse un couloir en forme de L entre deux points donnés.
     */
/*
    private void carveCorridor(int x1, int y1, int x2, int y2) {
        // Couloir horizontal
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        for (int x = minX; x <= maxX; x++) {
            obstacleGrid[x][y1] = false;
        }

        // Couloir vertical
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        for (int y = minY; y <= maxY; y++) {
            obstacleGrid[x2][y] = false;
        }
    }
*/

    /**
     * Génère l'environnement via une approche algorithmique hybride.
     * Superpose la carte prédéfinie (worldMap) et un bruit stochastique (obstacles aléatoires)
     * tout en protégeant la zone d'apparition du joueur.
     */
    private void generateObstacles() {
        // Initialise la matrice de collision toutes les cases sont à 0 (vide) par défaut
        obstacleGrid = new int[GRID_WIDTH][GRID_HEIGHT];

        // Étape 1 : Lecture de la matrice prédéfinie
        // Attention !!! : y correspond aux Lignes (1er crochet), x correspond aux Colonnes (2ème crochet)
        for (int y = 0; y < GRID_HEIGHT; y++) { // Boucle parcourant les ordonnées (lignes) de haut en bas
            for (int x = 0; x < GRID_WIDTH; x++) { // Boucle parcourant les abscisses (colonnes) de gauche à droite
                int cellData = worldMap[y][x];
                if (cellData == 1) { // Vérifie si la valeur de la matrice fixe à ces coordonnées dicte la présence d'un mur
                    placeObstacle(x, y, "#1a252f", 1); // Fait appel à la sous-méthode pour créer un obstacle (mur) : Couleur gris/bleu très sombre et type 1
                }
                else if (cellData == 4) { // Vérifie si la valeur de la matrice fixe à ces coordonnées dicte la présence d'un ennemi
                    placeEntity(x, y, "silhouette.png", 4, "#c0392b"); // Place un ennemi (cherche l'image silhouette.png, sinon carré rouge)
                }
                else if (cellData == 5) { // Vérifie si la valeur de la matrice fixe à ces coordonnées dicte la présence du mini jeu
                    placeEntity(x, y, "statsgame.png", 5, "#f1c40f"); // Place le déclencheur Pong (cherche l'image pong.png, sinon carré jaune)
                }
                else if (cellData == 6) {
                    // Place le magasin (cherche shop.png, sinon carré marron)
                    // N'oublie pas de modifier le chemin dans ta méthode placeEntity
                    // ou d'en créer une spécifique pour l'environnement si les chemins sont codés en dur !
                    placeEntity(x, y, "environment/map/shop.png", 6, "#8e44ad");
                }
                // Si worldMap[y][x] == 3, on ne dessine rien (c'est de l'herbe), mais on mémorise la protection plus tard
            }
        }

        // Étape 2 : Génération des obstacles aléatoires
        java.util.Random random = new java.util.Random(); // Instancie le générateur de nombres pseudo-aléatoires de Java
        int randomsPlaced = 0; // Initialise le compteur d'obstacles placés avec succès

        int startX = 10; // Enregistre la coordonnée X fixe où le joueur apparaîtra
        int startY = 5; // Enregistre la coordonnée Y fixe où le joueur apparaîtra

        while (randomsPlaced < RANDOM_OBSTACLES_COUNT) { // Continue la boucle tant que le quota d'obstacles défini n'est pas atteint
            int rx = random.nextInt(GRID_WIDTH); // Génère une coordonnée X aléatoire comprise dans les limites de la grille
            int ry = random.nextInt(GRID_HEIGHT); // Génère une coordonnée Y aléatoire comprise dans les limites de la grille

            // Évaluation de validité :
            // On ne pose un rocher QUE si la matrice physique est vide (0)
            // ET que le plan du monde indique que ce n'est pas une zone protégée (worldMap != 3)
            // ET que ce n'est pas le point d'apparition du joueur.
            if (obstacleGrid[rx][ry] == 0 && worldMap[ry][rx] != 3 && !(rx == startX && ry == startY)) {
                // On place un petit rocher (couleur légèrement différente pour les différencier)
                placeObstacle(rx, ry, "#7f8c8d", 2); // Fait appel à la sous-méthode pour créer un rocher aléatoire : Couleur gris clair et type 2
                randomsPlaced++; // Incrémente le compteur pour valider la pose de cet obstacle
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
        String fileName = heroClass.toLowerCase() + "_map.png";

        try {
            String imagePath = getClass().getResource("/com/example/projet_oop_rogue/assets/characters/" + fileName).toExternalForm();
            playerSprite = new javafx.scene.image.ImageView(new javafx.scene.image.Image(imagePath));

            // 2. Ajustement de la taille de l'image pour qu'elle rentre dans la case (légèrement plus petite que TILE_SIZE)
            playerSprite.setFitWidth(40);
            playerSprite.setFitHeight(40);
            playerSprite.setPreserveRatio(true);

            // PLUS BESOIN DE DETERMINER DES COORDONNEES FIXES POUR LE JOUEUR !!! : car maintenant c'est notre
            // algorithme pour la Génération par Blocs d'obstacles sur la map qui détermine l'endroit sécurisé
            // où placer le héros (au centre de la première salle générée).
/*
            // 3. Définition des coordonnées de départ (au centre mathématique de la grille)
            playerX = GRID_WIDTH / 2;
            playerY = GRID_HEIGHT / 2;
*/
            // 3. Définition des coordonnées de départ
            playerX = 10; // Assigne mathématiquement la colonne de départ au joueur
            playerY = 5; // Assigne mathématiquement la ligne de départ au joueur

            // 4. Ajout de l'image dans le GridPane par-dessus le sol
            gameBoard.add(playerSprite, playerX, playerY);

        } catch (Exception e) {
            System.err.println("Erreur au chargement du sprite du joueur : " + fileName);
        }
    }

    /**
     * Tente de déplacer le joueur selon des vecteurs de direction (dx, dy).
     *
     * @param dx Déplacement sur l'axe X (Colonnes : -1 gauche, 1 droite)
     * @param dy Déplacement sur l'axe Y (Lignes : -1 haut, 1 bas)
     */
    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        // 1. Détection des limites de la map
        if (newX >= 0 && newX < GRID_WIDTH && newY >= 0 && newY < GRID_HEIGHT) {

            // Partie pour la génération purement aléatoire d'obstacles sur la map
/*
            // 2. Détection des collisions avec le décor (Complexité O(1))
            if (obstacleGrid[newX][newY]) {

                // Si la case n'est pas un mur (false), on avance !
                playerX = newX;
                playerY = newY;
                javafx.scene.layout.GridPane.setColumnIndex(playerSprite, playerX);
                javafx.scene.layout.GridPane.setRowIndex(playerSprite, playerY);

            }
            else {
                // Le joueur percute un mur intérieur
                if (battleLogs != null) {
                    battleLogs.appendText("\n> Un éboulement bloque le passage !");
                }
            }
        }
        else {
            if (battleLogs != null) {
                battleLogs.appendText("\n> Le mur du donjon vous bloque le passage !");
            }
        }
*/
            // 2. Détection des collisions typées
            int targetCell = obstacleGrid[newX][newY];

            if (targetCell == 0) {
                // La case est vide, on avance !
                playerX = newX;
                playerY = newY;
                javafx.scene.layout.GridPane.setColumnIndex(playerSprite, playerX);
                javafx.scene.layout.GridPane.setRowIndex(playerSprite, playerY);

            }
            else if (targetCell == 1) {
                // Collision avec un mur fixe (obstacle type 1)
                if (battleLogs != null) {
                    battleLogs.appendText("\n> Un mur d'enceinte infranchissable vous bloque !");
                }
            }
            else if (targetCell == 2) {
                // Collision avec un rocher aléatoire (obstacle type 2)
                if (battleLogs != null) {
                    battleLogs.appendText("\n> Un éboulement de rochers vous bloque le passage !");
                }
            }
            else if (targetCell == 4) {
                // NOUVEAU : Interaction avec un Ennemi
                if (battleLogs != null) {
                    battleLogs.appendText("\n> [!] Un ennemi se dresse devant vous ! (Combat imminent)");
                }
                // TODO : Lancer la scène de combat ici
                // Appel de la méthode de combat en transmettant les coordonnées de la cible
                startBattle(newX, newY);

            }
            else if (targetCell == 5) {
                // NOUVEAU : Interaction avec le Mini-jeu
                if (battleLogs != null) {
                    battleLogs.appendText("\n> Vous avez trouvé la borne d'arcade mystère !");
                }
                // TODO : Lancer la scène du mini-jeu Pong ici

            }
            else if (targetCell == 6) {
                // Interaction avec le Magasin
                if (battleLogs != null) {
                    battleLogs.appendText("\n> 🪙 Bienvenue dans la boutique ! (Ouverture de l'inventaire...)");
                }
                // TODO : Lancer la scène du magasin ici

            }
        }
        else {
            if (battleLogs != null) {
                // Collision avec les limites de la map (obstacle type null)
                battleLogs.appendText("\n> Vous avez atteint la limite du monde !");
            }
        }
    }


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



