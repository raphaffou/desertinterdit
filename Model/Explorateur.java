package Model;

public class Explorateur extends Joueur{
    public Explorateur(int id, String nom, Tuile emplacement, Modele modele){
        super(id, nom, emplacement, 4, 5, modele);
        waterLvl = 4;
    }
    @Override
    boolean estVoisin(Direction direction){
        return this.emplacement.isTuile(direction);
    }
    @Override
    public String role(){
        return "Explorateur";
    }
}
