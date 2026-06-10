package com.example.projet_oop_rogue.characters.heroes;

/**
 * classe mage enfant de hero
 */
public class mage extends hero{
    /**
     * constructuer pour crée un mage avec les bonnes stats
     * @param names : nom du joueur
     */
    public mage(String names){
        super(0.8,0.25,1,0.25,0.5, names,25,1,0);
    }

    /**
     * Méthode : attaque_1 ou attaque normal
     * @return les conditions si des dégats physique (Non) ou magique (Oui) dovant être calculer
     */
    public int[] attaque_1(){
        return new int[]{0, 1};
    }

    /**
     * Méthode : attaque_2 ou ulti
     * retire 12,5 pv
     * fait le double de dégats magique
     * @return les conditions si des dégats physique (Non) ou magique (Oui) dovant être calculer
     */
    public int[] attaque_2(){
        this.vie_actuel = vie_actuel - 12.5;
        if(vie_actuel <= 0){
            this.vie_actuel = 1;
        }
        return new int[]{0, 2};
    }

    /**
     * Méthode : attaque_3 ou object
     * @return les conditions si des dégats physique ou magique dovant être calculer
     */
    public int[] attaque_3(){
        return new int[]{1, 0};
    }
}