package Model;
import java.util.ArrayList;

public class Tunnel extends Tuile{
    public Tunnel(Modele modele, Vecteur position, int sable, ArrayList<Joueur> j, ArrayList<PiecesMoteur> pieces) {
        super(modele, position, sable, j, pieces);
    }
    public Tunnel(Modele modele, Vecteur position, int sable) {
        super(modele, position, sable);
    }
    public Tunnel(Modele modele, Vecteur position) {
        super(modele, position);
    }


    @Override
    public String filename(){ return "Tunnel";}




    @Override
    public String toString(){
        return "Tunnel";
    }
}
