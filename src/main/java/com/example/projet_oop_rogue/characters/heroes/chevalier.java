package com.example.projet_oop_rogue.characters.heroes;

/**
 * class chevalier enfant de la classe hero
 */
public class chevalier extends hero{
    /**
     * chevalier constructeur : crée un chevalier avec les bonnes stats de départ
     * @param names le nom du joueur
     */
    public chevalier(String names){
        super(0.8,1,0.25,0.5,0.2, names,10,2,0);
    }

    /**
     * Méthode : attaque_1 ou attaque normal
     * @return les conditions si des dégats physique (Oui) ou magique (Non) dovant être calculer
     */
    public int[] attaque_1(){
        return new int[]{1, 0};
    }

    /**
     * Méthode : attaque_2 ou ulti
     * @return les conditions si des dégats physique (Oui) ou magique (Oui) dovant être calculer
     */
    public int[] attaque_2(){
        this.vie_actuel = vie_actuel - 12.5;
        if(vie_actuel <= 0){
            this.vie_actuel = 1;
        }
        return new int[]{1, 1};
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
