package com.example.projet_oop_rogue.controllers;

import com.example.projet_oop_rogue.core.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.projet_oop_rogue.controllers.sceneController;
import java.util.Random;

public class ShopController extends Game {

    @FXML
    private Label goldLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private Label lbl_potion_stock;

    @FXML
    public void initialize(){
        updateGoldDisplay();
        lbl_potion_stock.setText("Restaure 150 HP / +2 objets | Stock : " + stock_potion);
    }

    @FXML
    private void buyPotion() {
        // 1. On vérifie sur la carte principale si le joueur a assez d'or
        if (money >= 50 && stock_potion > 0) {
            // 2. On soustrait l'or sur la carte principale
            money -= 50;

            // 3. On ajoute les dégâts sur la carte principale
            main_character.vie_actuel += 150;
            if(main_character.vie_actuel > main_character.vie_max){
                main_character.vie_actuel = main_character.vie_max;
            }
            nbr_obj += 2;

            // 5. On met à jour l'interface de la boutique
            messageLabel.setText("vous avez bu une potion + 250 HP! et obtenu 2 objets");
            messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
            updateGoldDisplay();
            stock_potion -= 1;
            lbl_potion_stock.setText("Restaure 150 HP / +2 objets | Stock : " + stock_potion);
        } else {
            showError();
        }
    }

    @FXML
    private void buySword() {
        // 1. On vérifie sur la carte principale si le joueur a assez d'or
        if (money >= 200) {
            // 2. On soustrait l'or sur la carte principale
            money -= 200;

            Random random = new Random();
            int lucky_number = random.nextInt(5);

            switch (lucky_number){
                case 1:
                    main_character.get_boosts(5);
                    messageLabel.setText("vous avez trouver un energie drink vous devenez plus fort !");
                    break;
                case 2:
                    main_character.vie_actuel -= 100;
                    messageLabel.setText("Un slime à bondie du coffre : -100 PV !");
                    if (main_character.vie_actuel <= 0){
                        sceneController.switchScene("/com/example/projet_oop_rogue/fxml/WelcomePage.fxml");
                    }
                    break;
                case 3:
                    messageLabel.setText("Dommage rien!");
                    break;
                case 4:
                    nbr_obj += 5;
                    messageLabel.setText("bof : 5 objets !");
                    break;
                case 5:
                    main_character.vie_actuel = main_character.vie_max;
                    messageLabel.setText("vous trouvez du gel hydroalchoolique : PV max !");
                    break;

            }

            // 5. On met à jour l'interface de la boutique
            messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
            updateGoldDisplay();

        } else {
            showError();
        }
    }

    private void showError() {
        messageLabel.setText("Fonds insuffisants !");
        messageLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
    }

    private void updateGoldDisplay() {
        goldLabel.setText("Votre Or : " + money + " Or");
    }

    @FXML
    private void closeShop() {
        sceneController.go_to_lobby();
    }
}