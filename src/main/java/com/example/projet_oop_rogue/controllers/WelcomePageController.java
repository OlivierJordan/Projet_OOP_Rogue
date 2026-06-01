package com.example.projet_oop_rogue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
// On importe la classe ImageView pour l'affichage de l'avatar du personnage
import javafx.scene.image.ImageView;

public class WelcomePageController {

    // ========================================================================
    // 1. DÉCLARATION DES VARIABLES LIÉES À L'INTERFACE GRAPHIQUE (FXML)
    // ========================================================================
    // Indique à Java que ces variables sont connectées aux éléments de Scene Builder (via "fx:id")

    // L'annotation @FXML indique à Java que ces variables sont connectées
    // aux éléments graphiques de Scene Builder ayant le même "fx:id".

    @FXML
    private TextField playerNameInput; // Champ pour la saisie du nom du joueur

    @FXML
    private Button mageButton; // Bouton pour choisir la classe Mage
    @FXML
    private Button knightButton; // Bouton pour choisir la classe Chevalier
    @FXML
    private Button thiefButton; // Bouton pour choisir la classe Voleur

    @FXML
    private TextArea statsDisplay; // Zone de texte affichant les statistiques de la classe

    @FXML
    private ListView<String> leaderboardList; // Liste affichant les meilleurs scores

    @FXML
    private Button playButton; // Bouton principal pour lancer le jeu

    @FXML
    private ImageView characterImageView; // Composant d'affichage de l'avatar du personnage

    // ========================================================================
    // 2. VARIABLES DE LOGIQUE INTERNE
    // ========================================================================

    // Variable pour garder en mémoire la classe choisie par le joueur (vide au démarrage)
    private String selectedClass = "";

    // ========================================================================
    // 3. INITIALISATION AUTOMATIQUE
    // ========================================================================

    /**
     * Méthode appelée automatiquement après le chargement de l'interface graphique.
     * Utilisée pour configurer les valeurs par défaut.
     */
    @FXML
    public void initialize() {
        // Ajout de scores factices pour tester l'affichage (à remplacer par la lecture de fichier)
        leaderboardList.getItems().add("1. Olivier - 5000 pts");
        leaderboardList.getItems().add("2. JoueurTest - 3200 pts");
        leaderboardList.getItems().add("3. Anonyme - 1500 pts");
    }

    // ========================================================================
    // 4. MÉTHODES DE RÉACTION AUX BOUTONS (CALLBACKS)
    // ========================================================================

    /**
     * Action au clic sur le bouton "Mage".
     */
    @FXML
    protected void onMageSelected() {
        selectedClass = "Mage"; // Mémorisation du choix
        // Mise à jour de l'affichage avec les statistiques du Mage
        statsDisplay.setText("Classe : MAGE\n\nPoints de vie (HP) : 80\nDégâts : 25\nSpécialité : Attaque magique à distance.");
        updateCharacterImage("mage.png"); // Chargement de l'image correspondante
    }

    /**
     * Action au clic sur le bouton "Chevalier".
     */
    @FXML
    protected void onKnightSelected() {
        selectedClass = "Chevalier"; // Mémorisation du choix
        // Mise à jour de l'affichage avec les statistiques du Chevalier
        statsDisplay.setText("Classe : CHEVALIER\n\nPoints de vie (HP) : 150\nDégâts : 15\nSpécialité : Haute défense et armure lourde.");
        updateCharacterImage("chevalier.png"); // Chargement de l'image correspondante
    }

    /**
     * Action au clic sur le bouton "Voleur".
     */
    @FXML
    protected void onThiefSelected() {
        selectedClass = "Voleur"; // Mémorisation du choix
        // Mise à jour de l'affichage avec les statistiques du Voleur
        statsDisplay.setText("Classe : VOLEUR\n\nPoints de vie (HP) : 100\nDégâts : 20\nSpécialité : Esquive rapide et coups critiques.");
        updateCharacterImage("voleur.png"); // Chargement de l'image correspondante
    }

    // ========================================================================
    // 5. MÉTHODE DE LANCEMENT DU JEU
    // ========================================================================

    /**
     * Action au clic sur le bouton "JOUER".
     * Vérifie la validité des données avant de lancer la partie.
     */
    @FXML
    protected void onPlayButtonClicked() {
        // Récupération du texte tapé par le joueur
        String playerName = playerNameInput.getText();

        // --- VÉRIFICATION DES DONNÉES (Validation) ---

        // 1. Vérification si le nom est vide (.trim() enlève les espaces avant/après)
        if (playerName == null || playerName.trim().isEmpty()) {
            showAlert("Nom manquant", "Veuillez entrer un nom pour votre personnage avant de jouer.");
            return; // Interruption de la méthode
        }

        // 2. Vérification si une classe a été sélectionnée
        if (selectedClass.isEmpty()) {
            showAlert("Classe manquante", "Veuillez sélectionner une classe (Mage, Chevalier, Voleur) avant de jouer.");
            return; // Interruption de la méthode
        }

        // --- LANCEMENT DU JEU ---

        System.out.println("Lancement du jeu réussi !");
        System.out.println("Joueur : " + playerName);
        System.out.println("Classe : " + selectedClass);

        // --- SI TOUT EST OK, ON LANCE LE JEU ---

        try {
            // 1. Initialiser le chargeur FXML avec le chemin vers la vue du jeu principal
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/projet_oop_rogue/fxml/games/MainGame.fxml"));

            // 2. Charger l'arbre des composants (la vue racine)
            javafx.scene.Parent root = loader.load();

            // 3. Récupérer l'instance du contrôleur qui vient d'être créé par le FXMLLoader
            MainGameController gameController = loader.getController();

            // 4. Passer les données au nouveau contrôleur via la méthode que l'on a créée à l'Étape 1
            gameController.initData(playerName, selectedClass);

            // 5. Récupérer la fenêtre actuelle (Stage) grâce au bouton sur lequel on vient de cliquer
            javafx.stage.Stage stage = (javafx.stage.Stage) playButton.getScene().getWindow();

            // 6. Créer une nouvelle scène avec la vue chargée, et l'appliquer à la fenêtre
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.setTitle("Rogue-like - En jeu");
            stage.show();

        } catch (java.io.IOException e) {
            // Gestion de l'exception obligatoire lors du chargement de fichiers externes
            System.err.println("Erreur critique : Impossible de charger MainGame.fxml");
            e.printStackTrace();
            showAlert("Erreur de chargement", "Le fichier du jeu principal est introuvable.");
        }

        // TODO: Code pour charger "MainGame.fxml" et transmettre playerName et selectedClass au contrôleur principal
    }

    // ========================================================================
    // 6. MÉTHODES UTILITAIRES
    // ========================================================================

    /**
     * Génère et affiche une fenêtre d'alerte (Pop-up).
     * @param title Titre de la fenêtre d'alerte
     * @param message Message d'erreur à afficher
     */
    private void showAlert(String title, String message) {
        // Création d'une alerte de type WARNING
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null); // Désactivation du sous-titre
        alert.setContentText(message);
        alert.showAndWait(); // Affichage de la fenêtre et attente de validation
    }

    /**
     * Charge et affiche dynamiquement l'image du personnage depuis le dossier des ressources.
     * @param fileName Nom du fichier image (avec extension .png)
     */
    private void updateCharacterImage(String fileName) {
        try {
            // Construction du chemin absolu vers le dossier assets
            String imagePath = getClass().getResource("/com/example/projet_oop_rogue/assets/characters/" + fileName).toExternalForm();

            // Instanciation de l'objet Image et injection dans la vue
            javafx.scene.image.Image characterImage = new javafx.scene.image.Image(imagePath);
            characterImageView.setImage(characterImage);

        } catch (NullPointerException e) {
            // Sécurisation : empêche le plantage du jeu si le fichier image est manquant ou mal nommé
            System.err.println("Avertissement : L'image '" + fileName + "' est introuvable dans le dossier assets/characters/");
        }
    }

}

