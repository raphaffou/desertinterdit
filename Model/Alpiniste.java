package Model;

public class Alpiniste extends Joueur{
    public Alpiniste(int id, String nom, Tuile emplacement, Modele modele){
        super(id, nom, emplacement, 4, 4, modele);
    }
    @Override
    boolean isAccessible(Tuile t){
        return t != null;
    }
    @Override
    public String role(){
        return "Alpiniste";
    }
}
