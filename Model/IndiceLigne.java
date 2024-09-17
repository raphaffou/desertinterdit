package Model;

public class IndiceLigne extends Tuile {
    PiecesMoteur piece;
    IndiceLigne(Modele modele, Vecteur position, int sable, PiecesMoteur piece) {
        super(modele, position, sable);
        this.piece = piece;
        piece.setLigne(this);
    }

    @Override
    public String toString(){
        return "IndiceLigne " + piece.getNom();
    }
    
    @Override
    public String filename(){ 
        switch(piece.getNom()){
            case "Moteur":
                return "indiceLigne_moteur";
            case "Roue":
                return "indiceLigne_roue";
            case "Aile":
                return "indiceLigne_aile";
            case "Hélice":
                return "indiceLigne_helice";
            default:
                return "indiceLigne";
        }
    }

    @Override
    public void action(){
        super.action();
        if(isExplored() && piece.colonne.isExplored()){
            piece.apparaitre();
        }
    }
}
