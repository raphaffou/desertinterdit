package Model;

enum TypeTempete {
    DEPLACEMENT_NORD,
    DEPLACEMENT_SUD,
    DEPLACEMENT_EST,
    DEPLACEMENT_OUEST,
    VAGUE_DE_CHALEUR,
    LA_TEMPETE_SE_DECHAINE;
    public String toFilenameFormat(){
        return switch (this) {
            case DEPLACEMENT_NORD -> "n";
            case DEPLACEMENT_SUD -> "s";
            case DEPLACEMENT_EST -> "e";
            case DEPLACEMENT_OUEST -> "o";
            case VAGUE_DE_CHALEUR -> "vaguesChaleur.png";
            case LA_TEMPETE_SE_DECHAINE -> "tempete.png";
        };
    }
    public boolean isDeplacement(){
        return this == DEPLACEMENT_NORD || this == DEPLACEMENT_SUD || this == DEPLACEMENT_EST || this == DEPLACEMENT_OUEST;
    }
}



public class CarteTempete extends Cartes {
    int force; //-1 si la carte est une carte de type "la tempête se déchaîne" ou "vague de chaleur"
    public TypeTempete type;
    public CarteTempete(TypeTempete type, int force, Modele modele) {
        super(modele);
        this.type = type;
        if (type == TypeTempete.LA_TEMPETE_SE_DECHAINE || type == TypeTempete.VAGUE_DE_CHALEUR) {
            this.force = -1;
        } else {
            this.force = force;
        }
    }
    @Override
    public void action(){
        switch (type) {
            //bullshit, à corriger
            case DEPLACEMENT_NORD:
                modele.deplacerOeil(Direction.NORD, force);
                break;
            case DEPLACEMENT_SUD:
                modele.deplacerOeil(Direction.SUD, force);
                break;
            case DEPLACEMENT_EST:
                modele.deplacerOeil(Direction.EST, force);
                break;
            case DEPLACEMENT_OUEST:
                modele.deplacerOeil(Direction.OUEST, force);
                break;
            case VAGUE_DE_CHALEUR:
                modele.vagueDeChaleur();
                break;
            case LA_TEMPETE_SE_DECHAINE:
                modele.laTempeteSeDechaine();
                break;
        }
    }
    @Override
    public String filename(){
        if(type.isDeplacement()){
            return "f"+force+type.toFilenameFormat() + ".png";
        }else{
            return type.toFilenameFormat();
        }
    }
}


