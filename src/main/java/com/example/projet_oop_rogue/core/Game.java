package com.example.projet_oop_rogue.core;


import com.example.projet_oop_rogue.characters.enemies.monstre;
import com.example.projet_oop_rogue.characters.heroes.hero;

/**
 * Classe mère abstraite représentant la structure globale d'un jeu ou d'une interface jouable.
 * Regroupe les attributs et méthodes partagés par tous les contrôleurs enfants.
 */
public abstract class Game {

    // =========================================================================
    // TES VARIABLES (Système d'économie et stats de base)
    // Utilisation de 'protected' pour que les classes enfants y aient accès
    // =========================================================================
    protected int playerGold = 100;
    protected int playerHP = 150;
    protected int playerMaxHP = 150;
    protected int playerDamage = 15;

    // =========================================================================
    // VARIABLES DU CAMARADE (Système de combat et progression)
    // Utilisation de 'public static' pour un accès global entre les scènes
    // =========================================================================
    public static int nbr_obj;
    public static int curremt_score;
    public static int current_floor;
    public static monstre current_enemie;
    public static hero main_character;
    public static String text_objets;
    public static int money; // On garde sa variable 'money' pour ne pas faire crasher son code, même si on utilise 'playerGold' pour le Shop
    public static String file_Score;



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



