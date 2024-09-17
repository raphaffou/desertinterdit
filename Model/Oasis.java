package Model;

import java.util.ArrayList;


public class Oasis extends Tuile{
    boolean mirage = false;
    public Oasis(Modele modele, Vecteur position, int sable, ArrayList<Joueur> j, ArrayList<PiecesMoteur> pieces) {
        super(modele, position, sable, j, pieces);
    }
    public Oasis(Modele modele, Vecteur position, int sable) {
        super(modele, position, sable);
    }
    public Oasis(Modele modele, Vecteur position, int sable, boolean mirage) {
        super(modele, position, sable);
        this.mirage = mirage;
    }

    public Oasis(Modele modele, Vecteur position) {
        super(modele, position);
    }

    @Override
    public void action() {
        if(!mirage) {
            super.action();
            for (Joueur j : this.getJoueurs()) {
                j.boit(); // prend 2 eau d'un coup
            }
        }
    }

    @Override
    public String toString(){
        return "Oasis";
    }

    public String filename(){
        if(!mirage)
            return "Oasis";
        else
            return "Cite";
    }



}