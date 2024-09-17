package Model;

public class Vecteur {
    int ligne, colonne;
    public Vecteur(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }
    public Vecteur(){
        this.ligne = 0;
        this.colonne = 0;
    }
    public Vecteur(Vecteur v){
        this.ligne = v.ligne;
        this.colonne = v.colonne;
    }
    public void setLigne(int ligne){
        this.ligne = ligne;
    }
    public void setColonne(int colonne){
        this.colonne = colonne;
    }
    public int getLigne(){
        return ligne;
    }
    public int getColonne(){
        return colonne;
    }

    public Vecteur add(Vecteur v){
        return new Vecteur(this.ligne + v.ligne, this.colonne + v.colonne);
    }
    public Vecteur sub(Vecteur v){return new Vecteur(this.ligne - v.ligne, this.colonne - v.colonne);}
    public boolean equals(Vecteur v){
        return this.ligne == v.ligne && this.colonne == v.colonne;
    }

    public String toString(){
        return "(" + ligne + ", " + colonne + ")";
    }

    public Vecteur copy(){
        return new Vecteur(this.ligne, this.colonne);
    }

}
