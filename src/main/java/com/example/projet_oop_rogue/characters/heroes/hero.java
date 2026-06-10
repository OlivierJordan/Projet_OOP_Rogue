package com.example.projet_oop_rogue.characters.heroes;

import com.example.projet_oop_rogue.characters.Character;

import java.util.Random;

/**
 * classe hero enfant de character
 */
public class hero extends Character {
    /// Stats de vitesse : définit les chances d'esquivé une attaque sur 100%
    private double vit_stats;
    /// Stats de vie courant : vie du joueur
    public double vie_actuel;
    ///  Classe du héro de 1 à 3 (mage|chavalier|voleur)
    public int hero_classe;
    /// Score courant du joueur
    public int score;

    /**
     * Constructeur pour crée un héro
     * @param vie : pourcentage de la constance de vie que doit avoir le perso
     * @param attp : Stats d'attaque physique
     * @param attm : Stats d'attaque magique
     * @param defp : Stats de défense physique
     * @param defm : Stast de défense magique
     * @param nom : nom du joueur
     * @param vit : Stast de vittesse du joueur
     * @param cls : Classe du jouer
     * @param src : mise a 0 du score
     */
    public hero(double vie, double attp, double attm, double defp, double defm, String nom, double vit, int cls, int src){
        super(vie, attp, attm, defp, defm, nom);
        this.vit_stats = vit;
        this.vie_actuel = const_vie * vie;
        this.hero_classe = cls;
        this.score = src;
    }

    /**
     * Méthode take damages
     * enlève des PV
     * @param damages : nombre de pv à enlever
     */
    public void take_damages(double damages){
        this.vie_actuel = vie_actuel - damages;
    }

    /**
     * Métode pour retourner la vie courante du hero
     * @return vie_actuel
     */
    public double get_vie(){
        return(vie_actuel);
    }

    /**
     * Métode pour retourner la stats de vie max du hero
     * @return vie_stats
     */
    public double get_vit_stat(){
        return(vit_stats);
    }

    /**
     * Métode pour retourner la classe du hero
     * @return hero_classe
     */
    public int get_calsse(){
        return(hero_classe);
    }

    /**
     * Métode pour retourner le score du hero
     * @return score
     */
    public int get_score(){
        return(score);
    }

    /**
     * Métode pour modifier le score du joueur
     * prend en entrée le point à ajouter
     * @return rien
     */
    public void set_score(int point){
        this.score = score + point;
    }

    /**
     * Métode pour ajouter des point de stats de manière global
     */
    public void get_boosts(double num){
        this.vit_stats = vit_stats + num;
        double total_damages = get_vie_max() - vie_actuel;
        this.vie_max = vie_max + num;
        this.vie_actuel = get_vie_max() - total_damages;
        this.att_phy_stats = att_phy_stats + num * 0.05;
        this.att_mag_stats = att_mag_stats + num * 0.05;
        this.def_phy_stats = def_phy_stats + num * 0.05;
        this.def_mag_stats = def_mag_stats + num * 0.05;
        this.vit_stats = vit_stats + num * 2;
    }

    /**
     * Métode pour calculer les dégats qui doivent être retiré au joueur et les retire
     * @param attp_stats : stats d'attque physique de l'ennemi
     * @param attm_stats : stats d'attque magique de l'ennemi
     * @param cp : dit si des dégats physique doivent être prit en compte
     * @param cm : dit si des dégats megique doivent être prit en compte
     */
    public double calcul_damages(double attp_stats, double attm_stats, double cp, double cm){
        Random random = new Random();
        int counter_number =  random.nextInt(100);
        int cv = 1;
        if(vit_stats >= counter_number){
            cv = 0;
        }
        double damages = (((1-def_phy_stats) * const_deg * attp_stats) * cp + ((1-def_mag_stats) * const_deg * attm_stats) * cm) * cv;
        this.vie_actuel = vie_actuel - damages;
        return(damages);
    }
}
