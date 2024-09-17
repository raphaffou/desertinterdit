package Model;

public class Porteuse extends Joueur{
    public Porteuse(int id, String nom, Tuile emplacement, Modele modele){
        super(id, nom, emplacement, 4, 6, modele);
        this.waterLvl = 5;
    }
    @Override
    public void donneEau(Joueur j){ //ne depense pas d'action comparé à la version de Joueur
        if (this.waterLvl > 0 && (this.getTuilesVoisines().contains(j.emplacement) || this.emplacement.equals(j.emplacement))){
            this.waterLvl--;
            j.waterLvl++;
        }else{
            throw new RuntimeException("Fun Joueur.donneEau : Joueur non voisin ou pas d'eau à donner");
        }
    }
    @Override
    public boolean peutDonnerEau(Case c){
        Vecteur v = c.getPosition();
        Case macase = modele.plateau[v.getLigne()][v.getColonne()];
        if(macase instanceof Tuile && (macase.equals(emplacement))){
            return ((Tuile) macase).besoinEauIci();
        }
        for(Direction d : Direction.values()){
            if(macase instanceof Tuile && (macase.equals(emplacement.getVoisin(d)))){
                return ((Tuile) macase).besoinEauIci();
            }
        }
        return false;
    }
    public boolean peutPiocherEau(){
        return this.emplacement instanceof Oasis && !((Oasis)emplacement).mirage && this.waterLvl < this.maxWaterLvl && emplacement.getSable() == 0 && emplacement.isExplored();
    }
    public void piocherEau(){
        if(peutPiocherEau()){
            this.waterLvl++;
            action();
        }
    }
    @Override
    public String role(){
        return "Porteuse d'eau";
    }
}
