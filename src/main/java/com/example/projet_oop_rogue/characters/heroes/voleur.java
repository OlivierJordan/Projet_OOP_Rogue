package com.example.projet_oop_rogue.characters.heroes;

/**
 * classe voleur enfant de hero
 */
public class voleur extends hero{
    /**
     * constructuer pour crée un voleur avec les bonnes stats
     * @param names : nom du joueur
     */
    public voleur(String names){
        super(0.7,0.8,0.45,0.35,0.35, names,40,3,0);
    }

    /**
     * Méthode : attaque_1 ou attaque normal
     * @return les conditions si des dégats physique (Oui) ou magique (Oui) dovant être calculer
     */
    public int[] attaque_1(){
        return new int[]{1, 1};
    }

    /**
     * Méthode : attaque_2 ou ulti
     * redonne 25 pv
     * @return les conditions si des dégats physique (Oui) ou magique (Non) dovant être calculer
     */
    public int[] attaque_2(){
        this.vie_actuel = vie_actuel + 25;
        if(vie_actuel > vie_max){
            this.vie_actuel = vie_max;
        }
        return new int[]{1, 0};
    }

    /**
     * Méthode : attaque_3 ou object
     * retone 100 PV
     * @return les conditions si des dégats physique (Non) ou magique (Non) dovant être calculer
     */
    public int[] attaque_3(){
        this.vie_actuel = vie_actuel + 100;
        if(vie_actuel > vie_max){
            this.vie_actuel = vie_max;
        }
        return new int[]{0, 0};
    }
}
