package com.example.projet_oop_rogue.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Random;

public class PongGameController extends com.example.projet_oop_rogue.controllers.Game {
    // On injecte TOUS les composants du fichier FXML
    @FXML private Pane gamePane;
    @FXML private Rectangle paddle;
    @FXML private Circle ball;
    @FXML private Text scoreText;
    @FXML private Text speedText;
    @FXML private VBox screenOverlay;
    @FXML private Text mainTitle;
    @FXML private Text subTitle;

    // =========================================================
    // NOUVEAU : Le lien de communication avec ta carte principale
    // =========================================================
    private MainGameController mainController;

    public void setMainController(MainGameController mainController) {
        this.mainController = mainController;
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public void setGamePane(Pane gamePane) {
        this.gamePane = gamePane;
    }

    private enum State { START_SCREEN, PLAYING, GAME_OVER }
    private State gameState = State.START_SCREEN;

    private String awardReceived = "None";
    private boolean awardCalculated = false;

    // Dimensions
    private final int width = 800;
    private final int height = 500;

    // Propriétés physiques de la balle
    private double ballX, ballY;
    private double ballSpeedX, ballSpeedY;
    private final double baseSpeed = 4.0;
    private int score = 0;

    @FXML
    public void initialize() {
        // On positionne la raquette une première fois verticalement
        paddle.setLayoutY(height - 15 - 20);

        // Configuration des effets CSS directement (équivalent du DropShadow en code)
        paddle.setStyle("-fx-effect: dropshadow(three-pass-box, #00dbff, 18, 0.0, 0, 0);");
        ball.setStyle("-fx-effect: dropshadow(three-pass-box, #ffaa00, 20, 0.0, 0, 0);");
        mainTitle.setStyle("-fx-effect: dropshadow(three-pass-box, #00dbff, 25, 0.0, 0, 0);");

        resetGameVariables();
        updateVisuals(); // Met les formes aux bonnes places initiales

        // Boucle de jeu
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameState == State.PLAYING) {
                    updatePhysics();
                    updateVisuals();
                }
            }
        };
        gameLoop.start();
    }

    public void setupInputs(Scene scene) {
        scene.setOnMouseMoved(e -> {
            if (gameState == State.PLAYING) {
                double mouseX = e.getX() - paddle.getWidth() / 2;
                // Contraintes des bords
                if (mouseX < 0) mouseX = 0;
                if (mouseX + paddle.getWidth() > width) mouseX = width - paddle.getWidth();

                paddle.setLayoutX(mouseX);
            }
        });

        scene.setOnKeyPressed(e -> {
            if (gameState == State.START_SCREEN && e.getCode() == KeyCode.SPACE) {
                startGame();
            } else if (gameState == State.GAME_OVER && e.getCode() == KeyCode.SPACE) {
                resetGameVariables();
                startGame();
            }
        });

        scene.setOnMouseClicked(e -> {
            if (gameState == State.START_SCREEN) {
                startGame();
            }
        });
    }

    private void startGame() {
        gameState = State.PLAYING;
        screenOverlay.setVisible(false); // Cache l'écran d'accueil XML
    }

    private void updatePhysics() {
        double radius = ball.getRadius();
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Rebond murs latéraux
        if (ballX - radius <= 0 || ballX + radius >= width) {
            ballSpeedX = -ballSpeedX;
            score++;
        }
        // Rebond plafond
        if (ballY - radius < 0) {
            ballSpeedY = -ballSpeedY;
            score++;
        }
        // Rebond Raquette
        if (ballY + radius >= paddle.getLayoutY() && ballY - radius <= paddle.getLayoutY() + paddle.getHeight()) {
            if (ballX + radius >= paddle.getLayoutX() && ballX - radius <= paddle.getLayoutX() + paddle.getWidth()) {
                ballSpeedY = -Math.abs(ballSpeedY);
                score++;

                if (score % 5 == 0 && Math.abs(ballSpeedY) < 16.0) {
                    ballSpeedX *= 1.15;
                    ballSpeedY *= 1.15;
                }
            }
        }
        // Défaite
        if (ballY - radius > height) {
            endGame();
        }
    }

    // C'est ici qu'on applique les coordonnées physiques sur les composants FXML
    private void updateVisuals() {
        ball.setLayoutX(ballX);
        ball.setLayoutY(ballY);
        scoreText.setText("Rebonds : " + score);

        double currentSpeed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
        double initialSpeed = Math.sqrt(baseSpeed * baseSpeed * 2);
        int speedPct = Math.max(0, (int) Math.round(((currentSpeed / initialSpeed) - 1) * 100));
        speedText.setText("Vittesse : " + speedPct + "%");
    }

/*
    private void endGame() {
        gameState = State.GAME_OVER;

        // Calcul des coffres
        if (!awardCalculated) {
            if (score >= 30) awardReceived = "Legendary chest";
            else if (score >= 20) awardReceived = "Gold chest";
            else if (score >= 10) awardReceived = "Silver chest";
            else if (score >= 5) awardReceived = "Wooden chest";
            else awardReceived = "None (Score too low)";
            awardCalculated = true;
        }

        // "GAME OVER" géant en rouge néon (sans ombre noire)
        mainTitle.setText("GAME OVER");
        mainTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 75px; -fx-effect: dropshadow(three-pass-box, #ff2200, 40, 0.6, 0, 0);");

        // Contenu textuel orange épuré
        subTitle.setText("Final score: " + score + " rebounds\n" +
                "Award: " + awardReceived + "\n\n" +
                "Press SPACE to restart");

        subTitle.setFill(javafx.scene.paint.Color.web("#ff7700"));
        subTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 26px;"); // Plus aucune ombre noire ici

        screenOverlay.setVisible(true); // Réaffiche l'écran
    }
*/
    private void endGame() {
        gameState = State.GAME_OVER;

        // Calcul des coffres ET attribution de la monnaie
        if (!awardCalculated) {
            int goldWon = 0; // NOUVEAU : On prépare la variable de gain

            if (score >= 30) { awardReceived = "Legendary chest"; goldWon = 100; }
            else if (score >= 20) { awardReceived = "Gold chest"; goldWon = 50; }
            else if (score >= 10) { awardReceived = "Silver chest"; goldWon = 20; }
            else if (score >= 5) { awardReceived = "Wooden chest"; goldWon = 5; }
            else { awardReceived = "None (Score too low)"; goldWon = 0; }

            // NOUVEAU : On transfère l'or au contrôleur principal si la connexion existe
            if (mainController != null) {
                mainController.setPlayerGold(mainController.getPlayerGold() + goldWon); // Ajoute le gain au portefeuille du joueur
                mainController.updatePlayerStatsUI(); // Actualise immédiatement l'affichage sur la carte en arrière-plan
            }

            awardCalculated = true;
        }

        // "GAME OVER" géant en rouge néon (sans ombre noire)
        mainTitle.setText("GAME OVER");
        mainTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 75px; -fx-effect: dropshadow(three-pass-box, #ff2200, 40, 0.6, 0, 0);");

        // NOUVEAU : Ajout de la consigne pour quitter le jeu (Touche ECHAP)
        subTitle.setText("Final score: " + score + " rebounds\n" +
                "Award: " + awardReceived + "\n\n" +
                "Press SPACE to restart\n" +
                "Press ESCAPE to leave");

        subTitle.setFill(javafx.scene.paint.Color.web("#ff7700"));
        subTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 26px;");

        screenOverlay.setVisible(true); // Réaffiche l'écran
    }



    private void resetGameVariables() {
        score = 0;
        ballX = width / 2.0;
        ballY = 100;
        awardCalculated = false;

        Random random = new Random();
        ballSpeedX = (random.nextBoolean() ? 1 : -1) * baseSpeed;
        ballSpeedY = baseSpeed;
        paddle.setLayoutX((width - paddle.getWidth()) / 2);
    }
}



