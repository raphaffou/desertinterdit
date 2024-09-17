package Model;

import java.util.ArrayList;

public class Joueur{ //temporairement non abstract
    String nom;
    int id;
    Tuile emplacement;
    protected int nbActionsReste;
    protected int nbActionsMax;
    int waterLvl;
    int maxWaterLvl;
    Modele modele;
    ArrayList<PiecesMoteur> piecesportees;

    public Joueur(int id, String nom, Tuile emplacement, int nbActionsMax, int maxWaterLvl, Modele modele) {
        this.id = id;
        this.nom = nom;
        this.emplacement = emplacement;
        this.emplacement.addJoueur(this);
        this.nbActionsMax = nbActionsMax;
        this.nbActionsReste = nbActionsMax;
        this.maxWaterLvl = maxWaterLvl;
        this.waterLvl = maxWaterLvl;
        this.modele = modele;
        this.piecesportees = new ArrayList<>();
    }
    public Joueur(int id, String nom, Tuile emplacement, Modele modele){
        this(id, nom, emplacement, 4, 4, modele);
    }

    public int getNbActions() { return nbActionsReste; }
    public int getId(){ return id;}
    public ArrayList<PiecesMoteur> getPieces(){ return piecesportees; }
    public int getWaterLvl() { return waterLvl; }
    public int getMaxWaterLvl() { return maxWaterLvl; }
    public int getNbActionsMax() { return nbActionsMax; }
    public int getNbActionsReste() {return nbActionsReste;}
    public Tuile getEmplacement() { return emplacement; }
    public String getNom() { return nom; }
    public void setEmplacement(Tuile emplacement) {
        this.emplacement = emplacement;
    }

    /**
     * Revoie true si on trouve une tuile dans une direction donnée (accessible au joueur)
     */
    boolean estVoisin(Direction direction){
        //à overrider si explorateur
        switch(direction){
            case NORD_EST : case NORD_OUEST : case SUD_EST : case SUD_OUEST:
                return false;
            default:
                return this.emplacement.isTuile(direction);
        }
    }

    public void deplacer(Direction direction){
        if(!isAccessible(emplacement))
            // throw new IllegalArgumentException("Tuile inaccessible");
            return;
        if (estVoisin(direction)){
            Tuile voisine = (Tuile)this.emplacement.getVoisin(direction);
            if(isAccessible(voisine)){
                this.emplacement.removeJoueur(this);
                this.emplacement = voisine;
                this.emplacement.addJoueur(this);
                this.action();
                if(peutPoserPiece()){
                    poserPiece();
                }
            }
        }
    }

    public void deplacer(Vecteur v){
        if(emplacement.getPosition().equals(v)){
            return;
        }
        for(Direction d : Direction.values()){
            System.out.println(v.sub(this.emplacement.getPosition()).equals(d.toVecteur()));
            if(v.sub(this.emplacement.getPosition()).equals(d.toVecteur())){
                deplacer(d);
                return;
            }
        }
        throw new IllegalArgumentException("fun: joueur.deplacer(Vecteur v) : coordonnée non valide");
    }

    public ArrayList<Tuile> getTuilesVoisines(){
        ArrayList<Tuile> voisins = new ArrayList<>();
        for (Direction direction : Direction.values()){
            if (estVoisin(direction)){
                voisins.add((Tuile)this.emplacement.getVoisin(direction));
            }
        }
        return voisins;
    }

    public void soif(){
        if(waterLvl>0 && !(emplacement instanceof Tunnel && emplacement.isExplored())){
            waterLvl--;
        }
    }

    public void boit(){
        if(waterLvl<maxWaterLvl-1 ){ waterLvl+=2; }
        else if(waterLvl<maxWaterLvl){ waterLvl++; }
    }

    /**
     * Retourne la tuile courante si elle est retournable
     */
    public void retourneCaseCourante(){
        if(this.emplacement.retournable()){
            this.emplacement.retourne();
            this.emplacement.action();
            action();
        }
    }

    /**
     * Décrémente le nombre d'actions restantes
     */
    void action(){
        if(this.nbActionsReste>0){
            this.nbActionsReste--;
        }
        else throw new RuntimeException("Fun Joueur.action : Plus d'actions restantes");
    }

    /**
     * Prend une pièce de la tuile courante et la met dans son "inventaire" (piecesportees)
     */
    public void prendrePiece(){
        if(peutPrendrePiece()){
            PiecesMoteur p = this.emplacement.getPieces().get(0);
            this.piecesportees.add(p);
            this.emplacement.removePiece(p);
            p.setPorteur(this);
            action();
        }
    }

    public void teleporter(Tuile t){
        if(isAccessible(t)){
            this.emplacement.removeJoueur(this);
            this.emplacement = t;
            this.emplacement.addJoueur(this);
            this.action();
        }
    }

    /**
     * Revoie true si le joueur peut prendre une pièce
     */
    public boolean peutPrendrePiece(){
        return this.emplacement.getPieces().size()>0 && !emplacement.estEnsablee();
    }

    /**
     * Pas specialement necessaire mais ça peut servir. Enlève le sable de la tuile courante ou de la tuile dans la direction donnée
     */
    public void desensable(Direction dir){
        if(desensablable(dir)){
            if(dir == null){ // case courante
                this.emplacement.desensable();
            }
            else { ((Tuile)this.emplacement.getVoisin(dir)).desensable(); }
            this.action();
        }
        else{ throw new RuntimeException("Fun Joueur.desensable : Tuile non désensable");}
    }

    public void desensable(){ desensable((Direction)null); }

    public void desensable(Vecteur v){
        if(v.equals(this.emplacement.getPosition())){
            desensable((Direction)null);
            return;
        }
        for(Direction d : Direction.values()){
            if(v.sub(this.emplacement.getPosition()).equals(d.toVecteur())){
                desensable(d);
                return;
            }
        }
        throw new IllegalArgumentException("fun: joueur.desensable(Vecteur v) : coordonnée non valide");
    }

    boolean desensablable(Direction dir){
        if(dir == null){ // case courante
            return this.emplacement.desensablable();
        }
        
        if(estVoisin(dir)){
            return ((Tuile)this.emplacement.getVoisin(dir)).desensablable();
        }
        return false;
    }
    /**
     * Revoie true si le joueur peut se déplacer dans la direction donnée
     */
    public boolean deplacable(Direction dir){
        if(estVoisin(dir)){
            Tuile voisine = (Tuile)this.emplacement.getVoisin(dir);
            if(isAccessible(voisine)){
                return true;
            }
        }
        return false;
    }
    /**
     * Revoie true si le joueur est mort de soif
     */
    boolean mortDeSoif(){ return waterLvl==0; }

    /**
     * Revoie true si le joueur peut se déplacer sur cette piece si elle etait à sa portée
     */
    boolean isAccessible(Tuile t){
        //! à overrider si alpiniste
        if(t == null)
            return false;
        return t.getSable()<2 && this.emplacement.getSable() < 2;
    }
    /**
     * Revoie true si le joueur peut poser une pièce
     */
    public boolean peutPoserPiece(){
        return this.piecesportees.size()>0 &&
                !emplacement.estEnsablee() &&
                modele.piecesPosables &&
                emplacement instanceof PisteDecollage;
    }

    /**
     * Pose la pièce sur la tuile courante. N'utilise pas d'actions
     */
    void poserPiece(){
        if(peutPoserPiece()){
            for(PiecesMoteur p : piecesportees){
                this.piecesportees.remove(p);
                this.emplacement.addPiece(p);
                p.deposer();
                //p.setPorteur(null); //useless, already done in deposer mais osef
            }
        }
    }

    public boolean peutExplorer(){
        return this.emplacement.retournable();
    }
    public void explorer(){
        if(peutExplorer()){
            this.emplacement.retourne();
            if(peutPoserPiece()){
                poserPiece();
            }
            action();
        }
    }

    public boolean tourTermine(){
        return this.nbActionsReste==0;
    }

    public void resetActions(){
        this.nbActionsReste = this.nbActionsMax;
    }

    public boolean peutSeDeplacer(Case c){ //equivalent dans joueur avec effet de bord existant
        Vecteur v = c.getPosition();
        Case macase = modele.plateau[v.getLigne()][v.getColonne()];
        for(Direction d : Direction.values()){
            if(deplacable(d) && macase.equals(emplacement.getVoisin(d))){
                return true;
            }
        }
        return false;
    }

    public void donneEau(Joueur j){
        if (this.waterLvl > 0 && (this.getTuilesVoisines().contains(j.emplacement) || j.emplacement == this.emplacement)){
            this.waterLvl--;
            j.waterLvl++;
            action();
        }else{
            throw new RuntimeException("Fun Joueur.donneEau : Joueur non voisin ou pas d'eau à donner");
        }
    }

    public boolean peutDesensabler(Case c){
        Vecteur v = c.getPosition();
        Case macase = modele.plateau[v.getLigne()][v.getColonne()];
        for(Direction d : Direction.values()){
            if(macase instanceof Tuile && ((Tuile) macase).desensablable() && macase.equals(emplacement.getVoisin(d))){
                return true;
            }
        }
        return false;
    }

    public boolean peutDonnerEau(Case c){
        Vecteur v = c.getPosition();
        Case macase = modele.plateau[v.getLigne()][v.getColonne()];
        boolean existeporteuse = false;
        Joueur porteuse = null;
        for(Joueur j : modele.joueurs){
            if(j instanceof Porteuse){
                existeporteuse = true;
                porteuse = j;
            }
        }
        if(emplacement == macase)
            return emplacement.besoinEauIci();
        for(Direction d : Direction.values()){
            if(macase instanceof Tuile && (macase.equals(emplacement.getVoisin(d))
                    || (existeporteuse && porteuse.peutDonnerEau(macase)))){
                return ((Tuile) macase).besoinEauIci();
            }
        }
        return false;
    }

    public boolean peutDonnerEauVoisin(Case c){
        Vecteur v = c.getPosition();
        Case macase = modele.plateau[v.getLigne()][v.getColonne()];
        if(emplacement == macase)
            return emplacement.besoinEauIci();
        for(Tuile t : getTuilesVoisines()){
            if((macase instanceof Tuile) && (macase.equals(t))){
                System.out.println("peutDonnerEauVoisin en pos " + macase.getPosition().toString() + " : " + ((Tuile) macase).besoinEauIci());
                return ((Tuile) macase).besoinEauIci();
            }
        }
        return false;
    }

    public ArrayList<Case> getVoisinsOuOnPeutFaireDesTrucs(){ //à overrider si on peut faire des trucs sur d'autres cases
        ArrayList<Case> voisins = new ArrayList<>();

        //verifiaction que la porteuse existe
        boolean existeporteuse = false;
        Joueur porteuse = null;
        for(Joueur j : modele.joueurs){
            if(j instanceof Porteuse){
                existeporteuse = true;
                porteuse = j;
            }
        }

        //voisinage immediat du joueur courant
        if(this.getNbActionsReste()>0){
            System.out.println("Porteuse ? : " + (this instanceof Porteuse) + " Peut piocher ? " + ((this instanceof Porteuse) && ((Porteuse)this).peutPiocherEau()));
            if(this.emplacement.retournable() || this.emplacement.getPieces().size() > 0 || emplacement.desensablable() || emplacement.besoinEauIci() || (existeporteuse && (this instanceof Porteuse) && ((Porteuse)this).peutPiocherEau())){
                voisins.add(this.emplacement);
            }
            for (Direction direction : Direction.values()){
                if (estVoisin(direction)){//verifie que c'est bien une tuile dans direction
                    if(deplacable(direction) || desensablable(direction) || ((Tuile)emplacement.getVoisin(direction)).besoinEauIci()){
                        voisins.add(this.emplacement.getVoisin(direction));
                    }
                }
            }
        }

        //gestion voisinage porteuse
        if(existeporteuse && !(this instanceof Porteuse)){
            if(porteuse.emplacement.besoinEauIci(porteuse)){
                if(!voisins.contains(porteuse.emplacement))
                    voisins.add(porteuse.emplacement);
            }
            for (Direction direction : Direction.values()){
                if (porteuse.estVoisin(direction)){//verifie que c'est bien une tuile dans direction
                    if(((Tuile)porteuse.emplacement.getVoisin(direction)).besoinEauIci()){
                        if(!voisins.contains(porteuse.emplacement.getVoisin(direction))){
                            System.out.println("ajout de " + porteuse.emplacement.getVoisin(direction).getPosition().toString());
                            voisins.add(porteuse.emplacement.getVoisin(direction));
                        }
                    }
                }
            }
        }

        return voisins;
    }
    public String role(){
        return "Role inconnu";
    }
}