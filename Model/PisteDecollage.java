package Model;

import java.util.ArrayList;

public class PisteDecollage extends Tuile{
    PisteDecollage(Modele modele, Vecteur position, int sable, ArrayList<Joueur> j, ArrayList<PiecesMoteur> pieces) {
        super(modele, position, sable, j, pieces);
    }
    PisteDecollage(Modele modele, Vecteur position, int sable) {
        super(modele, position, sable);
    }

    @Override
    public String filename(){ return "pisteDecollage";}

    @Override
    public void action(){
        super.action();
        modele.pisteDecouverte();
    }
}
