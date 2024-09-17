package Model;

public class PiecesMoteur{
    private String nom;
    IndiceLigne ligne;
    IndiceColonne colonne;
    public Tuile apparition = null;
    // Si not null considéré apparu
    Vecteur position = null;
    // Si not null, considéré devoilé
    Modele modele;
    Joueur porteur = null;
    // Si not null, considéré porté
    boolean estInstallee = false;


    public PiecesMoteur(String nom, Modele modele) {
        this.nom = nom;
        this.modele = modele;
    }

    // getters
    public String getNom(){
        return nom;
    }

    // setters
    public void setLigne(IndiceLigne ligne){ this.ligne = ligne; }

    public void setColonne(IndiceColonne colonne){ this.colonne = colonne; }


    
    public boolean checkWellConstructed(){
        if(ligne == null || colonne == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean peutApparaitre(){
        return ligne.isExplored() && colonne.isExplored();
    }

    public void apparaitre(){
        if (peutApparaitre()){
            if(position == null){
                position = new Vecteur(
                    ligne.getPosition().getLigne(),
                    colonne.getPosition().getColonne()
                );
                System.out.println("Fun : PiecesMoteur.apparaitre : La piece est apparue en position " + position);
            }
            if(!estApparu()){
                if(!position.equals(modele.getOeilPos())){
                    modele.getTuile(position).addPiece(this);
                    apparition = modele.getTuile(position);
                }
            }
        }
        else throw new RuntimeException("Fun : PiecesMoteur.apparaitre : La piece ne peut pas apparaitre");
    }
    boolean estApparu(){
        return apparition != null;
    }
    

    public void setPorteur(Joueur porteur) {
        if(!peutApparaitre() || !estApparu()){
            throw new RuntimeException("Fun : setPorteur : La piece n'est pas apparue");
        }
        this.porteur = porteur;

    }
    boolean devoileeMaisPasApparue(){
        return apparition == null && position != null;
    }
    boolean estPortee(){
        return porteur != null;
    }
    boolean estDevoilee(){
        return position != null;
    }
    /*
    * @return true si la piece est installee, false sinon
    * */
    boolean estInstallee(){
        return estInstallee;
    }
    void deposer(){
        if(estPortee()){
            if(!estApparu()){
                throw new RuntimeException("Fun : PiecesMoteur.deposer : La piece n'est pas apparue");
            }else{
                porteur = null;
                estInstallee = true;
            }
        }
        else throw new RuntimeException("Fun : PiecesMoteur.deposer : La piece n'est pas portee");
    }


    public String filename(){
        switch (nom) {
            case "Aile":
                return "aile";
            case "Hélice":
                return "helice";
            case "Moteur":
                return "moteur";
            case "Roue":
                return "roue";
            default:
                throw new RuntimeException("Fun : PiecesMoteur.filename : La piece n'est pas reconnue");
        }
    }
}