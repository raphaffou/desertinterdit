package Model;

import java.util.ArrayList;



public class Tuile extends Case{
    protected boolean explored = false;
    protected int sable = 0;
    protected ArrayList<Joueur> joueurs = new ArrayList<>();
    protected ArrayList<PiecesMoteur> pieces = new ArrayList<>();

    public Tuile(Modele modele, Vecteur position, int sable, ArrayList<Joueur> j, ArrayList<PiecesMoteur> pieces) {
        this(modele, position, sable);
        this.joueurs =  new ArrayList<>(joueurs);
        this.pieces = new ArrayList<>(pieces);
    }

    public Tuile(Modele modele, Vecteur position, int sable) {
        super(modele, position);
        if(sable < 0){throw new IllegalArgumentException("Le sable ne peut pas être négatif");}
        this.sable = sable;
    }

    public Tuile(Modele modele, Vecteur position) {
        this(modele, position, 0);
    }

    // getters


    public boolean isExplored() {
        return explored;
    }

    public String toString(){
        return "Cité";
    }

    @Override
    public String filename(){ return "Cite";}

    public int getSable() {
        return sable;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public ArrayList<PiecesMoteur> getPieces() {
        return pieces;
    }



    /** revoie true si la cable est retournable, ie si elle ne contient pas de sable et qu'elle n'est pas déjà exporée */
    public boolean retournable(){ return !this.desensablable() && !this.explored; }
   
    /** retourne la tuile, et active son action() */
    public void retourne(){ 
        if(retournable()) {explored = true; this.action();} 
        else throw new IllegalStateException("La tuile est ensablee ou déjà découverte (pas retournable) (fct : Tuile.retourne())");
    }


    /** active l'action de la tuile (par defaut rien) */
    public void action() { modele.notifyObservers();}


    /** reoturne vrai si il reste du sable sur la tuile */
    public boolean desensablable(){ return sable != 0; }

    /** desensable la tuile */
    public void desensable() { 
        if(this.desensablable()){
            sable--;
            modele.leSableDiminue();
        } else
        { throw new IllegalStateException ("La tuile n'est pas ensablee");} 
    }

    /** ensable la tuile */
    public void ensable() {
        if(!modele.qteSableMaxAtteinte()){
            sable++;
            modele.leSableAugmente();
        }
    }


    /** ajoute le joueur j a la liste des joueurs sur la tuile */
    public void addJoueur(Joueur j){ 
        if(!joueurs.contains(j))
            { joueurs.add(j); }
        else
            { throw new IllegalArgumentException("Le joueur est deja sur la tuile");}
    }

    /** retire le joueur j de la liste des joueurs sur la tuile */
    public void removeJoueur(Joueur j){ 
        if(joueurs.contains(j))
            joueurs.remove(j); 
        else
            { throw new IllegalArgumentException("Le joueur n'est pas sur la tuile");}
    }
    public void addPiece(PiecesMoteur p){
        if(!pieces.contains(p))
            pieces.add(p);
        else
            throw new IllegalArgumentException("La piece est deja sur la tuile");
    }
    public void removePiece(PiecesMoteur p){
        if(pieces.contains(p))
            pieces.remove(p);
        else
            throw new IllegalArgumentException("La piece n'est pas sur la tuile");
    }

    public boolean isTuile(Direction dir){
        try{ 
            modele.getTuile(this.getPosition().add(dir.toVecteur()));
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }

    public boolean besoinEauIci(){
        Joueur jCourant = modele.getJoueurCourant();
        for(Joueur j : joueurs){
            if(j == jCourant) continue;
            if(j.getWaterLvl() < j.getMaxWaterLvl()){
                return true;
            }
        }
        return false;
    }

    public boolean besoinEauIci(Joueur j){
        for(Joueur j2 : joueurs){
            if(j2 == j) continue;
            if(j2.getWaterLvl() < j2.getMaxWaterLvl()){
                return true;
            }
        }
        return false;
    }


    public boolean estEnsablee(){ return sable != 0; }


}

