package com.example.projet_oop_rogue.characters;

/**
 * class abstraite character
 */
abstract public class Character {
    /// constante de vie, pour les calcules
    public final double const_vie = 1000;
    /// constante de dégat, pour les calcules
    public final double const_deg = 300;

    /// Vie max du personnage
    public double vie_max;
    ///  stats d'attaque physique
    public double att_phy_stats;
    ///  stast d'attaque magique
    public double att_mag_stats;
    ///  stats de défense physique
    public double def_phy_stats;
    ///  stats de défense magique
    public double def_mag_stats;
    /// nom du personnage
    private String name ;

    /**
     * Constructeur qui crée un character
     * @param vie : pourcentage de la constance de vie que doit avoir le perso
     * @param attp : Stats d'attaque physique
     * @param attm : Stats d'attaque magique
     * @param defp : Stats de défense physique
     * @param defm : Stast de défense magique
     * @param nom : nom du character
     */
    public Character(double vie, double attp, double attm, double defp, double defm, String nom){
        this.vie_max = const_vie * vie;
        this.att_phy_stats = attp;
        this.att_mag_stats = attm;
        this.def_phy_stats = defp;
        this.def_mag_stats = defm;
        this.name = nom;
    }

    /**
     * Méthode qui retourne la vie max du perso
     * @return vie_max
     */
    public double get_vie_max(){
        return(vie_max);
    }

    /**
     * Méthode qui retourne la stats d'attaque physique du perso
     * @return att_phy_stats
     */
    public double get_att_phy(){
        return(att_phy_stats);
    }
    /**
     * Méthode qui retourne la stats d'attaque magique du perso
     * @return att_mag_stats
     */
    public double get_att_mag(){
        return(att_mag_stats);
    }
    /**
     * Méthode qui retourne la stats de défense physique du perso
     * @return def_phy_stats
     */
    public double get_def_phy(){
        return(def_phy_stats);
    }
    /**
     * Méthode qui retourne la stats de magique physique du perso
     * @return def_mag_stats
     */
    public double get_def_mag(){
        return(def_mag_stats);
    }

    /**
     * Méthode qui retourne le nom du perso
     * @return name
     */
    public String get_name(){
        return(name);
    }

    /**
     * Méthode à implémenté plus tard qui doit retiré des pv selon des dégats donné
     * @param damages : PV a retiré
     */
    protected abstract void take_damages(double damages);

    /**
     * Méthode à implémenté plus tard qui doit retourné la vie courant du perso
     * @return
     */
    protected abstract double get_vie();

    /**
     * Méthode à implémenté plus tard qui doit calculer des dégats et les retiré
     * @param attp_stats : stats d'attaque physique de l'adversaire
     * @param attm_stats : stats d'attaque magique de l'adversaire
     * @param cp : condition pour faire des dégats physique
     * @param cm : condition pour faire des dégats magique
     * @return le nombres de dégats fait
     */
    protected abstract double calcul_damages(double attp_stats, double attm_stats, double cp, double cm);
}
