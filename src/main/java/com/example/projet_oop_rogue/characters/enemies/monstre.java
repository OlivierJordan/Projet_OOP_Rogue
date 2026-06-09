package com.example.projet_oop_rogue.characters.enemies;

import com.example.projet_oop_rogue.characters.Character;

/**
 * Classe monstre enfant de character
 */
public class monstre extends Character {
    /// stats de vie courante du monstre
    public double vie_actuel;
    ///  classe du monstre de 0 à 6 (0 = unknow | 1 = squellette | 2 = werewolf | 3 = vampire | 4 = dragon | 5 = reine des vampire | 6 = roi dragon)
    private int monster_classe;
    /// nombre de point donné au joueur a l'élimination
    private int points;
    ///  condition si le monstre fait des dégats magique
    private double cond_mag;
    ///  condition si le monstre fait des dégats physique
    private  double cond_phy;

    /**
     * Constructeur pour crée un Monstre
     * @param vie : pourcentage de la constance de vie que doit avoir le perso
     * @param attp : Stats d'attaque physique
     * @param attm : Stats d'attaque magique
     * @param defp : Stats de défense physique
     * @param defm : Stast de défense magique
     * @param nom : nom du monstre
     * @param cls : classe du monstre
     * @param pts : point
     * @param cm : condition pour les dégats magique
     * @param cp : condition pour les dégats physique
     */
    public monstre(double vie, double attp, double attm, double defp, double defm, String nom, int cls, int pts, double cm, double cp){
        super(vie, attp, attm, defp, defm, nom);
        this.vie_actuel = const_vie * vie;
        this.monster_classe = cls;
        this.points = pts;
        this.cond_mag = cm;
        this.cond_phy = cp;
    }

    /**
     * Méthode qui inflige des dégats au monstre
     * @param damages : nombre de pv à retiré
     */
    public void take_damages(double damages){
        this.vie_actuel = vie_actuel - damages;
    }

    /**
     * Méthode qui retourne la vie du monstre
     * @return la vie courante du monstre
     */
    public double get_vie(){
        return(vie_actuel);
    }

    /**
     * Méthode qui retourne la classe du monstre
     * @return la classe du monstre
     */
    public int get_calsse(){
        return(monster_classe);
    }

    /**
     * Méthode qui retourne la valeur en point du monstre
     * @return points
     */
    public int get_points(){
        return(points);
    }

    /**
     * Méthode qui retourne si le monstre fait des dégats magique
     * @return cond_mag
     */
    public double get_cond_mag(){
        return(cond_mag);
    }

    /**
     * Méthode qui retourne si le monstre fait des dégats physique
     * @return cond_phy
     */
    public double get_cond_phy(){
        return(cond_phy);
    }

    /**
     * Méthode qui calcule les dégats qui doivent être retiré est qui les enlèvent . retourne aussi le nombre de pv retiré.
     * @param attp_stats : Stats d'attaque physique de l'adversarial
     * @param attm_stats : Stats d'attaque magique  de l'adversarial
     * @param cp : conditon si l'adversaire fait des dégats physique
     * @param cm : conditon si l'adversaire fait des dégats magique
     * @return damages
     */
    public double calcul_damages(double attp_stats, double attm_stats, double cp, double cm){
        double damages = ((1-def_phy_stats) * const_deg * attp_stats) * cp + ((1-def_mag_stats) * const_deg * attm_stats) * cm;
        this.vie_actuel = vie_actuel - damages;
        return(damages);
    }
}
