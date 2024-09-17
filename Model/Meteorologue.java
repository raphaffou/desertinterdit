package Model;

public class Meteorologue extends Joueur{
    //!!!! modifier ctrlPioche
    public Meteorologue(int id, String nom, Tuile emplacement, Modele modele){
        super(id, nom, emplacement, 4, 5, modele);
        waterLvl = 4;
    }
    @Override
    public String role(){
        return "Météorologue";
    }
}
