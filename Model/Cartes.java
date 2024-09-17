package Model;

public abstract class Cartes {
    Modele modele;
    public Cartes(Modele modele) {
        this.modele = modele;
    }
    public abstract void action();
    public abstract String filename();
}

