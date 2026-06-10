package com.example.projet_oop_rogue.core;

import com.example.projet_oop_rogue.characters.heroes.hero;
import com.example.projet_oop_rogue.characters.enemies.monstre;

/**
 * classe mère de tous les jeux : Game
 */
public class Game {
    /// valeur du nombre d'objets de la run
    public static int nbr_obj ;
    ///  score de la run
    public static int curremt_score;
    /// étage de la run
    public static int current_floor;
    ///  ennemie de la run
    public static monstre current_enemie ;
    /// personnage du joueur durant le run
    public static hero main_character;
    /// Texte descriptive de l'objet de joueur
    public static String text_objets;
    ///  somme d'argent du joueur durant la run
    public static int money;
    /// nom du fichier contenant le leaderboarde
    public static String file_Score;

    public static int stock_potion;
}