package Model;

public class Archeologue extends Joueur{
    public Archeologue(int id, String nom, Tuile emplacement, Modele modele){
        super(id, nom, emplacement, 4, 4, modele);
    }
    @Override
    public String role(){
        return "Archéologue";
    }

    @Override
    public void desensable() {
        desensable((Direction)null);
        if(emplacement.desensablable())
            desensable((Direction)null);
    }
    @Override
    public void desensable(Direction d) {
        if(desensablable(d)){
            if(d == null){ // case courante
                this.emplacement.desensable();
                if(emplacement.desensablable())
                    emplacement.desensable();
            }
            else {
                ((Tuile)this.emplacement.getVoisin(d)).desensable();
                if(((Tuile)this.emplacement.getVoisin(d)).desensablable())
                    ((Tuile)this.emplacement.getVoisin(d)).desensable();
            }
            this.action();
        }
        else{ throw new RuntimeException("Fun Archeo.desensable : Tuile non désensable");}
    }

}
