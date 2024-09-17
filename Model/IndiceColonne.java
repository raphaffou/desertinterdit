package Model;

public class IndiceColonne extends Tuile{
    PiecesMoteur piece;
    IndiceColonne(Modele modele, Vecteur position, int sable, PiecesMoteur piece) {
        super(modele, position, sable);
        this.piece = piece;
        piece.setColonne(this);
    }

    @Override
    public String toString(){
        return "IndiceColonne " + piece.getNom();
    }

    @Override
    public String filename(){ 
        switch(piece.getNom()){
            case "Moteur":
                return "indiceColonne_moteur";
            case "Roue":
                return "indiceColonne_roue";
            case "Aile":
                return "indiceColonne_aile";
            case "Hélice":
                return "indiceColonne_helice";
            default:
                return "indiceColonne";
        }
    }

    @Override
    public void action(){
        super.action();
        if(isExplored() && piece.ligne.isExplored()){
            piece.apparaitre();
        }
    }
}
