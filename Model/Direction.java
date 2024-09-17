package Model;

public enum Direction {
    NORD,
    SUD,
    EST,
    OUEST,
    NORD_EST,
    NORD_OUEST,
    SUD_EST,
    SUD_OUEST;

    public Vecteur toVecteur(){
        switch(this){
            case NORD:
                return new Vecteur(-1, 0);
            case SUD:
                return new Vecteur(1, 0);
            case EST:
                return new Vecteur(0, 1);
            case OUEST:
                return new Vecteur(0, -1);
            case NORD_EST:
                return new Vecteur(-1, 1);
            case NORD_OUEST:
                return new Vecteur(-1, -1);
            case SUD_EST:
                return new Vecteur(1, 1);
            case SUD_OUEST:
                return new Vecteur(1, -1);
            default: return new Vecteur(0,0);
        }
    }
}
