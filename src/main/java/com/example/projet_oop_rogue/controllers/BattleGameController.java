package com.example.projet_oop_rogue.controllers;

import com.example.projet_oop_rogue.core.Game;
import com.example.projet_oop_rogue.controllers.sceneController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import com.example.projet_oop_rogue.characters.heroes.hero;
import com.example.projet_oop_rogue.characters.heroes.voleur;
import com.example.projet_oop_rogue.characters.heroes.mage;
import com.example.projet_oop_rogue.characters.heroes.chevalier;
import com.example.projet_oop_rogue.characters.monster.monstre;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

import static com.example.projet_oop_rogue.controllers.sceneController.switchScene;

/**
 * Controler des séquences de combats
 */
public class BattleGameController extends com.example.projet_oop_rogue.controllers.Game {

    // =========================================================
    // NOUVEAU : Le lien de communication avec ta carte principale
    // =========================================================
    private MainGameController mainController;
    private boolean battleWon = false; // Permet de savoir si le joueur a survécu pour fermer la fenêtre

    public void setMainController(MainGameController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private Label lbl_des_att;
    @FXML
    private Label lbl_des_ult;
    @FXML
    private Label lbl_des_object;
    @FXML
    private Label lbl_player_name;
    @FXML
    private Label lbl_player_pv;
    @FXML
    private Label lbl_ennemi_name;
    @FXML
    private Label lbl_ennemi_pv;
    @FXML
    private Label lbl_score;
    @FXML
    private Label lbl_stats;
    @FXML
    private ImageView pic_Dragon_King;
    @FXML
    private ImageView pic_vampier_queen;
    @FXML
    private ImageView pic_unknow_god;
    @FXML
    private ImageView pic_dragon;
    @FXML
    private ImageView pic_vampier;
    @FXML
    private ImageView pic_squeleton;
    @FXML
    private ImageView pic_werewolf;
    @FXML
    private ImageView pic_voleur;
    @FXML
    private ImageView pic_chevalier;
    @FXML
    private ImageView pic_mage;
    @FXML
    private TextArea Info_fight ;
    @FXML
    private Button btn_att;
    @FXML
    private Button btn_ult;
    @FXML
    private Button btn_object;

    /**
     * Méthode d'initialisation de la scene
     * afficjhe les descriptions des attaques selon la classe choisie
     * reset les valeurs au démarage
     * affiche le personnage du joueur selon sa classe
     * choisie le première ennemi
     */
    @FXML
    public void initialize() {

        // NOUVEAU : Synchronisation différée via Platform.runLater
        // On attend que JavaFX ait fini de charger la fenêtre avant de demander le mainController
        javafx.application.Platform.runLater(() -> {
            if (mainController != null) {
                // On remplace les PV du module de combat par tes VRAIS PV
                main_character.vie_actuel = mainController.getPlayerHP();
                main_character.vie_max = mainController.getPlayerMaxHP();

                // On ajoute tes VRAIS dégâts (ex: ceux de l'épée du Shop) aux statistiques de base
                main_character.att_phy_stats += mainController.getPlayerDamage();
                main_character.att_mag_stats += mainController.getPlayerDamage(); // Bonus hybride pour simplifier

                // On met à jour l'affichage avec les nouvelles valeurs
                lbl_player_pv.setText("PV : " + main_character.vie_max + "/" + main_character.vie_actuel);
            }
        });

        /// affichage des PV du joueur
        lbl_player_pv.setText("PV : " + main_character.vie_max + "/" + main_character.vie_actuel);
        /// affichage des PV du monstre
        lbl_player_name.setText("{ " + main_character.get_name() + " }");
        /// initialise les variable de la run
        current_floor = 1;
        curremt_score = 0;
        nbr_obj = 10;
        ///  choisie le premier monstre
        choose_monster(current_floor);
        /// rend invisible les images des heros
        pic_voleur.setOpacity(0);
        pic_chevalier.setOpacity(0);
        pic_mage.setOpacity(0);
        /// affiches l'image et les descriptions des attaques, selon la bonne classe
        switch (main_character.hero_classe) {
            case 1:
                /// classe mage
                pic_mage.setOpacity(1);
                lbl_des_att.setText("[Attaque normal] boule de feu : fait des dégats magic");
                lbl_des_ult.setText("[Ulti] foudre : fait le double des dégat magic mais fait pertre 12.5 PV");
                text_objets = "[Object] bombe : fait des dégat physique ";
                lbl_des_object.setText(text_objets + nbr_obj);
                break;
            case 2:
                /// classe chevalier
                pic_chevalier.setOpacity(1);
                lbl_des_att.setText("[Attaque normal] cout d'épée : fait des dégats physique");
                lbl_des_ult.setText("[Ulti] enchantemant magic : fait des dégats physique et magic (dégats physique réduit)");
                text_objets = "[Object] position de soin : redonne  100 PV";
                lbl_des_object.setText(text_objets + nbr_obj);
                break;
            case 3:
                /// classe voleur
                pic_voleur.setOpacity(1);
                lbl_des_att.setText("[Attaque normal] cout de dague magic : fait des dégats physique et magic");
                lbl_des_ult.setText("[Ulti] dague vole de vie : fait des dégats physique et redonne 25 PV");
                text_objets = "[Objet] position de soin : redonne  100 PV";
                lbl_des_object.setText(text_objets + " object restant : " + nbr_obj);
                break;
        }

    }

    /**
     * Méthode de choix du monstre selon l'étage
     * @param floor : étage actuel
     *              chaque 10 étage un boss
     *              chaque 5 étagq un monstre un peu plus puissant
     *              les autres étages un mob faible
     */
    public void choose_monster(int floor){
        /// génère le nombre ramdom entre 0 et 10
        Random random = new Random();
        int choise_monster = random.nextInt(10);
        /// rend invisible toutes les images de monstre
        pic_squeleton.setOpacity(0);
        pic_werewolf.setOpacity(0);
        pic_vampier.setOpacity(0);
        pic_dragon.setOpacity(0);
        pic_vampier_queen.setOpacity(0);
        pic_Dragon_King.setOpacity(0);
        pic_unknow_god.setOpacity(0);

        /// désactive les boutons pour stopper le jeu le temps du changement de monstre
        btn_att.setDisable(true);
        btn_ult.setDisable(true);
        btn_object.setDisable(true);

        /// met le jeux en pause pendant 4 seconde le temps de changer de monstre
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            /// si besoin Boss
            if(floor %10 == 0){
                /// si le nombre ramdom est 0 : nouveau monstre = unknow
                if(choise_monster == 0){
                    /// création de l'ennemi avec ces stats
                    current_enemie = new monstre(1.2,0.6,0.6,0.8,0.8,"inconu",0,400,1,1);
                    /// affichage de l'ennemi
                    pic_unknow_god.setOpacity(1);
                    /// petite phrase pour l'introduire dans la texte box
                    Info_fight.appendText("?????, souhaite vous jugé !" + "\n ");
                }
                /// si le nombre ramdom est entre 10 et 6 : nouveau monstre = roi dragon
                else if (choise_monster > 5) {
                    current_enemie = new monstre(1,1,0,0.8,0.5,"Roi Dragon : Bezencon-sama",6,330,0,1);
                    pic_Dragon_King.setOpacity(1);
                    Info_fight.appendText("Le Roi des Dragon, vous défie ! " + "\n ");
                }
                /// si le nombre ramdom est entre 5 et 1 : nouveau monstre = reine vampire
                else {
                    current_enemie = new monstre(1,0,1,0.5,0.8,"Reine des vampires : Kumi",5,330,1,0);
                    pic_vampier_queen.setOpacity(1);
                    Info_fight.appendText("La Reine des vampires, se rit de vous" + "\n ");
                }
            }
            /// si besoin ennemi fort
            else if (floor %5 == 0) {
                /// si le nombre ramdom est entre 10 et 6 : nouveau monstre = dragon
                if (choise_monster > 5) {
                    current_enemie = new monstre(0.7,0.5,0,0.5,0.3,"dragon",4,200,0,1);
                    pic_dragon.setOpacity(1);
                    Info_fight.appendText("Un dragon sauvage est tombé du ciel" + "\n ");
                }
                /// si le nombre ramdom est entre 5 et 1 : nouveau monstre = vampire
                else {
                    current_enemie = new monstre(0.5,0,0.5,0.25,0.5,"vampire",3,175,1,0);
                    pic_vampier.setOpacity(1);
                    Info_fight.appendText("Un vampire est apparu en Nissan" + "\n ");
                }
            }
            ///  si besoin ennemie faible
            else {
                /// si le nombre ramdom est entre 10 et 6 : nouveau monstre = squellette
                if (choise_monster > 5) {
                    current_enemie = new monstre(0.25,0.25,0,0.2,0.2,"squelette",1,85,0,1);
                    pic_squeleton.setOpacity(1);
                    Info_fight.appendText("Un squelette est tomber d'une montagne" + "\n ");
                }
                /// si le nombre ramdom est entre 5 et 1 : nouveau monstre = werewolf
                else {
                    current_enemie = new monstre(0.25,0.4,0,0.4,0.2,"werewolf",2,120,0,1);
                    pic_werewolf.setOpacity(1);
                    Info_fight.appendText("Un loup garou et revenu du brezil" + "\n ");
                }
            }
            /// mise à jour du nom est des PVs de l'ennemi
            lbl_ennemi_name.setText("{ " + current_enemie.get_name() + " }");
            lbl_ennemi_pv.setText("PV : " + current_enemie.vie_max + "/" + current_enemie.vie_actuel);
            btn_att.setDisable(false);
            btn_ult.setDisable(false);
            btn_object.setDisable(false);
        });

        pause.play();
    }

    /**
     * Méthode game_play contient le déroulement d'un tour
     * @param action_phy : condition de dégats physique
     * @param action_mag : condition de dégats magique
     *                   étapes du tour :
     *                   1: attaque, joueur -> monstre
     *                   2: vérifie si le monstre est mort,
     *                                 si oui : choisie le prochaine ennemi et finit le tour.
     *                                 si non : continue
     *                   3: attaque, monstre -> joueur
     *                   4: vérifie si le joueur est mort,
     *      *                                 si oui : finit le tour, et envoie sur le lousing screen.
     *      *                                 si non : continue ver le prochain tour
     */
    public void game_play(int action_phy, int action_mag){
        double damage = 0;
        ///  rafraichie les information du combat
        lbl_ennemi_pv.setText("PV : " + current_enemie.vie_max + "/" + current_enemie.vie_actuel);
        lbl_player_pv.setText("PV : " + main_character.vie_max + "/" + main_character.vie_actuel);
        lbl_des_object.setText(text_objets + " object restant : " + nbr_obj);
        // action joueur
        double att_p = main_character.att_phy_stats;
        double att_m = main_character.att_mag_stats;
        /// calcule des dégats prit par le monstre est appliction de ces derniers
        damage = current_enemie.calcul_damages(att_p, att_m, action_phy, action_mag);
        Info_fight.appendText("Votre ennemie a prit " + damage + " dégats"+ "\n ");
        lbl_ennemi_pv.setText("PV : " + current_enemie.vie_max + "/" + current_enemie.vie_actuel);

        // vérification victoire
/*
        if(current_enemie.vie_actuel <= 0){
            current_floor = current_floor + 1;
            curremt_score = curremt_score + current_enemie.get_points();
            lbl_stats.setText("score actuel : " + curremt_score);
            Info_fight.appendText("vous avez survécue. Vous obtenez donc : " + current_enemie.get_points() + " points !!! \n");
            choose_monster(current_floor);
        }
        else{
            // action monstre
            /// calcule des dégats prit par le joueur est appliction de ces derniers
            damage = main_character.calcul_damages(current_enemie.att_phy_stats, current_enemie.att_mag_stats, current_enemie.get_cond_phy(), current_enemie.get_cond_mag());
            Info_fight.appendText("Vous avez prit " + damage + " dégats"+ "\n ");
            lbl_player_pv.setText("PV : " + main_character.vie_max + "/" + main_character.vie_actuel);
            // vérification défaite
            /// change de scene ver la loosing screen
            if(main_character.vie_actuel <= 0){
                sceneController.switchScene("/com/example/projet_oop_rogue/fxml/games/losing_screen.fxml");
            }
        }
        /// actualise le score
        lbl_score.setText("you are at the : " + current_floor + " floor");
    }
 */
        if(current_enemie.vie_actuel <= 0) {
            current_floor = current_floor + 1;
            curremt_score = curremt_score + current_enemie.get_points();
            lbl_stats.setText("score actuel : " + curremt_score);

            // NOUVEAU : Récompense en or (50 pièces par monstre)
            int goldReward = 50;
            Info_fight.appendText("Vous avez survécu ! Vous gagnez " + goldReward + " Or !\n");

            if (mainController != null) {
                mainController.setPlayerGold(mainController.getPlayerGold() + goldReward); // Ajoute l'or
                mainController.setPlayerHP((int) main_character.vie_actuel); // Sauvegarde les PV restants
                mainController.updatePlayerStatsUI(); // Met à jour la carte en arrière-plan

                // Ferme la fenêtre de combat pour retourner sur la carte
                javafx.stage.Stage stage = (javafx.stage.Stage) lbl_player_pv.getScene().getWindow();
                stage.close();
            }
        }
        else { // S'exécute si le monstre est toujours vivant après l'attaque du joueur
            damage = main_character.calcul_damages(current_enemie.att_phy_stats, current_enemie.att_mag_stats, current_enemie.get_cond_phy(), current_enemie.get_cond_mag()); // Calcule et applique les dégâts infligés par le monstre au joueur
            Info_fight.appendText("Vous avez prit " + damage + " dégats" + "\n "); // Affiche la sentence des dégâts reçus dans la console de combat
            lbl_player_pv.setText("PV : " + main_character.vie_max + "/" + main_character.vie_actuel); // Actualise l'étiquette visuelle des points de vie du joueur

            if (main_character.vie_actuel <= 0) { // ÉTAPE RECHERCHÉE : Vérifie si les points de vie du joueur sont tombés à zéro ou moins
                if (mainController != null) { // Vérifie si la liaison avec la carte principale est active pour éviter un plantage
                    mainController.setPlayerHP(0); // Écrase la valeur des PV sur la carte principale pour enregistrer la mort
                    mainController.updatePlayerStatsUI(); // Actualise instantanément le bandeau de statistiques gauche de la carte
                } // Ferme le bloc de synchronisation de la carte

                try { // Ouvre un bloc sécurisé pour intercepter les pannes de chargement du fichier FXML
                    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/projet_oop_rogue/fxml/games/GameOverBattle.fxml")); // Prépare l'affichage du nouvel écran de défaite
                    javafx.scene.Parent root = loader.load(); // Désérialise le fichier XML en objets graphiques exploitables par Java
                    javafx.stage.Stage gameOverStage = new javafx.stage.Stage(); // Crée une nouvelle fenêtre système pour le Game Over
                    gameOverStage.setScene(new javafx.scene.Scene(root)); // Injecte la scène graphique de défaite dans cette nouvelle fenêtre
                    gameOverStage.show(); // Affiche la fenêtre de Game Over à l'écran du joueur

                    ((javafx.stage.Stage) lbl_player_pv.getScene().getWindow()).close(); // Récupère et ferme la fenêtre actuelle du combat
                    if (mainController != null) { // Vérifie si la carte principale existe toujours en mémoire
                        ((javafx.stage.Stage) mainController.getGameBoard().getScene().getWindow()).close(); // Ferme également la fenêtre de la carte pour terminer proprement l'application
                    } // Ferme le traitement de fermeture de la carte
                } catch (IOException e) { // Attrape les exceptions d'entrée/sortie si le fichier FXML est introuvable
                    e.printStackTrace(); // Imprime l'arborescence technique de l'erreur dans la console pour faciliter le débogage
                } // Ferme le bloc de capture d'erreur catch
            } // Ferme la condition de vérification de défaite
        } // Ferme le bloc général du tour du monstre else

    /**
     * Bouton qui lance la méthode game_play avec pour action joueur une attaque normal
     */
    @FXML
    protected void on_btn_att(){
        ///  condition pour faire des dégats physique
        int act_phy = 0;
        ///  condition pour faire des dégats magique
        int act_mag = 0;
        /// choisie quelle est l'attaque normal selon la classe du joueur
        switch (main_character.hero_classe){
            case 1:
                /// récupère les condition de dégats
                act_phy = ((mage)main_character).attaque_1()[0];
                act_mag = ((mage)main_character).attaque_1()[1];
                /// Indique l'action faite
                Info_fight.appendText("vous envoyer une boule de feu" + "\n> ");
                break;
            case 2:
                act_phy = ((chevalier)main_character).attaque_1()[0];
                act_mag = ((chevalier)main_character).attaque_1()[1];
                Info_fight.appendText("vous envoyer un coup d'épée" + "\n ");
                break;
            case 3:
                act_phy = ((voleur)main_character).attaque_1()[0];
                act_mag = ((voleur)main_character).attaque_1()[1];
                Info_fight.appendText("vous envoyer un coup de dague" + "\n ");
                break;
        }
        /// lance game_play avec les bonnes conditions
        game_play(act_phy, act_mag);
    }

    /**
     * Bouton qui lance la méthode game_play avec pour action joueur un ultimate
     */
    @FXML
    protected void on_btn_ult(){
        int act_phy = 0;
        int act_mag = 0;
        switch (main_character.hero_classe){
            case 1:
                act_phy = ((mage)main_character).attaque_2()[0];
                act_mag = ((mage)main_character).attaque_2()[1];
                Info_fight.appendText("vous invoquer la foudre sur votre ennemie" + "\n");
                break;
            case 2:
                act_phy = ((chevalier)main_character).attaque_2()[0];
                act_mag = ((chevalier)main_character).attaque_2()[1];
                Info_fight.appendText("vous envoyer un coup d'épée enchantée" + "\n ");
                break;
            case 3:
                act_phy = ((voleur)main_character).attaque_2()[0];
                act_mag = ((voleur)main_character).attaque_2()[1];
                Info_fight.appendText("vous avez volé une partie des PV de votre ennemie" + "\n");
                break;
        }
        game_play(act_phy, act_mag);
    }

    /**
     * Bouton qui lance la méthode game_play avec pour action joueur l'utilisation d'un object
     * si le joueur n'a plus d'object ne fait rien, et affiche de faire une autre action
     */
    @FXML
    protected void on_btn_object(){
        int act_phy = 0;
        int act_mag = 0;
        /// Vérifie le nombre d'object
        if(nbr_obj != 0){
            switch (main_character.hero_classe){
                case 1:
                    act_phy = ((mage)main_character).attaque_3()[0];
                    act_mag = ((mage)main_character).attaque_3()[1];
                    Info_fight.appendText("vous avez en voyer une bombe sur votre ennemie" + "\n ");
                    break;
                case 2:
                    act_phy = ((chevalier)main_character).attaque_3()[0];
                    act_mag = ((chevalier)main_character).attaque_3()[1];
                    Info_fight.appendText("vous aves prit une potion de soin + 100PV" + "\n ");
                    break;
                case 3:
                    act_phy = ((voleur)main_character).attaque_3()[0];
                    act_mag = ((voleur)main_character).attaque_3()[1];
                    Info_fight.appendText("vous aves prit une potion de soin + 100PV" + "\n ");
                    break;
            }
            nbr_obj = nbr_obj -1;
            game_play(act_phy, act_mag);
        }
        else {
            /// affiche l'érreur sur le nombre d'object
            Info_fight.appendText("vous n'avez plus d'object, faite une autre action" + "\n ");
        }
    }
}
