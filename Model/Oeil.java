package Model;

public class Oeil extends Case {
    public Oeil(Modele modele, Vecteur vecteur){
        super(modele, vecteur);
    }

    @Override
    public String toString(){
        return "Oeil";
    }

    @Override
    public String filename(){ return "Oeil";}

    @Override
    public void echange(Direction dir) {
        Case caseEchangee = super.getVoisin(dir);
        if(caseEchangee instanceof Oeil){
            return;
        }
        Tuile tuile = (Tuile)caseEchangee;
        tuile.ensable();
        super.echange(dir);
    }
}
