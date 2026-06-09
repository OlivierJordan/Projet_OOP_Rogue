package com.example.projet_oop_rogue.controllers;

/**
 * Classe mère abstraite représentant la structure globale d'un jeu ou d'une interface jouable.
 * Regroupe les attributs et méthodes partagés par tous les contrôleurs enfants.
 */
public abstract class Game {

    // Utilisation de 'protected' pour que les classes enfants y aient accès directement
    protected int playerGold = 100;
    protected int playerHP = 150;
    protected int playerMaxHP = 150;
    protected int playerDamage = 15;

    // ========================================================================
    // MÉTHODES PARTAGÉES (GETTERS & SETTERS)
    // ========================================================================

    public int getPlayerGold() {
        return playerGold;
    }

    public void setPlayerGold(int playerGold) {
        this.playerGold = playerGold;
    }

    public int getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }

    public int getPlayerMaxHP() {
        return playerMaxHP;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public void setPlayerDamage(int playerDamage) {
        this.playerDamage = playerDamage;
    }

}



