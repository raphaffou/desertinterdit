package Model;
import java.util.ArrayList;
import java.util.Collections;



public class Modele extends Observable {
    public static final int HAUTEUR=40, LARGEUR=60;
    public int LARGEUR_PLATEAU = 5;
    public int MAX_SABLE_LEVEL = 48;
    public int MAX_TEMPETE_LEVEL = 7;


    public Case[][] plateau;
    public ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    public ArrayList<PiecesMoteur> pieces = new ArrayList<PiecesMoteur>();
    public pileTempete pileTempete = new pileTempete(this);
    public Oeil oeil;
    public int niveauDeTempete = 2;
    private int cranTempete = 0;
    public int nbJoueurs = 0;
    public int qteSable = 0;
    public boolean piecesPosables = false;
    private int joueurCourant;

    protected Tuile tuileDepart;
 
    public Modele() {
        initCases();
    }

    /**
     * Initialise le plateau de jeu
     */
    public void initCases() {

        pieces.add(new PiecesMoteur("Hélice", this));
        pieces.add(new PiecesMoteur("Moteur", this));
        pieces.add(new PiecesMoteur("Aile", this));
        pieces.add(new PiecesMoteur("Roue", this));

        //Mettre toutes les cases reglementaires dans le arrayList temproaire ci dessous, le shuffle, puis après on etale dans l'ordre sur le plateau
        ArrayList<Case> cases = new ArrayList<Case>();
        cases.add(new Oasis(this, new Vecteur(), 0));
        cases.add(new Oasis(this, new Vecteur(), 0));
        cases.add(new Oasis(this, new Vecteur(), 0, true));
        cases.add(new Tunnel(this, new Vecteur(), 0));
        cases.add(new Tunnel(this, new Vecteur(), 0));
        cases.add(new Tunnel(this, new Vecteur(), 0));
        cases.add(new PisteDecollage(this, new Vecteur(), 0));
        for(PiecesMoteur p : pieces){
            cases.add(new IndiceLigne(this, new Vecteur(), 0, p));
            cases.add(new IndiceColonne(this, new Vecteur(), 0, p));
        }
        for (int i = cases.size(); i < LARGEUR_PLATEAU*LARGEUR_PLATEAU-1; i++) {
            cases.add(new Tuile(this, new Vecteur(), 0));
        }
        Collections.shuffle(cases); //manque une case à ce stade, normal on rajoute l'oeil ensuite
        int centre = (LARGEUR_PLATEAU*LARGEUR_PLATEAU)/2;
        oeil = new Oeil(this, new Vecteur());
        cases.add(centre, oeil);

        plateau = new Case[LARGEUR_PLATEAU][LARGEUR_PLATEAU];
        for(int ligne=0; ligne<LARGEUR_PLATEAU; ligne++) {
            for(int colonne=0; colonne<LARGEUR_PLATEAU; colonne++) {
                plateau[ligne][colonne] = cases.get(ligne*LARGEUR_PLATEAU + colonne);
                //!CHOIX SYSTEME DE COORDONNEES DANGEREUX
                plateau[ligne][colonne].setPosition(new Vecteur(ligne, colonne));
            }
        }
        tuileDepart = getTuile(new Vecteur(3, 2));
        joueurCourant = 0;


        //haut droite
        ((Tuile)plateau[0][2]).ensable();
        ((Tuile)plateau[1][3]).ensable();
        //bas à droite
        ((Tuile)plateau[3][3]).ensable();
        ((Tuile)plateau[2][4]).ensable();
        //en bas à gauche
        ((Tuile)plateau[2][0]).ensable();
        ((Tuile)plateau[3][1]).ensable();
        ((Tuile)plateau[4][2]).ensable();
        //haut gauche
        ((Tuile)plateau[1][1]).ensable();

    }


    public void addJoueur(String nom, String role){
        switch(role){
            case "Explorateur":
                joueurs.add(new Explorateur(nbJoueurs, nom, tuileDepart,  this));
                break;
            case "Alpiniste":
                joueurs.add(new Alpiniste(nbJoueurs, nom, tuileDepart,  this));
                break;
            case "Porteuse d'eau":
                joueurs.add(new Porteuse(nbJoueurs, nom, tuileDepart,  this));
                break;
            case "Archéologue":
                joueurs.add(new Archeologue(nbJoueurs, nom, tuileDepart,  this));
                break;
            case "Météorologue":
                joueurs.add(new Meteorologue(nbJoueurs, nom, tuileDepart,  this));
                break;
            default:
                joueurs.add(new Joueur(nbJoueurs, nom, tuileDepart,  this));
                break;
        }
        nbJoueurs++;
    }

    Vecteur getOeilPos(){
        return oeil.getPosition();
    }
    public Joueur getJoueurCourant(){
        return joueurs.get(joueurCourant);
    }

    /**
     * Renvoie le joueur suivant dans l'ordre de jeu
     * @return le joueur suivant dans l'ordre de jeu
     */
    public Joueur getNextJoueur(){
        return joueurs.get((joueurCourant+1)%nbJoueurs);
    }

    // getters niveau de tempete et sable
    public int getNiveauDeTempete() {
        return niveauDeTempete;
    }

    public int getQteSable() {
        return qteSable;
    }

    /**
     * Passe au joueur suivant dans l'ordre de jeu
     */
    public void nextJoueur(){
        joueurs.get(joueurCourant).nbActionsReste = joueurs.get(joueurCourant).nbActionsMax;
        joueurCourant = (joueurCourant+1)%nbJoueurs;
    }
    /**
     * Renvoie la tuile à la position v.
     * Si la tuile est hors du plateau, leve une erreur.
     * Si la tuile est l'oeil, leve une erreur.
     * @return la tuile à la position v
     */
    public Tuile getTuile(Vecteur v){
        int ligne = v.getLigne();
        int colonne = v.getColonne();
        if(this.getOeilPos().equals(v)){
            throw new IllegalArgumentException("Tuile demandée est l'oeil, donc c'est pas une tuile ! (dans Modele.getTuile)");
        }else if(ligne < 0 || ligne >= LARGEUR_PLATEAU || colonne < 0 || colonne >= LARGEUR_PLATEAU){
            throw new IllegalArgumentException("Tuile demandée est hors du plateau !!! (dans Modele.getTuile)");
        }
        return (Tuile) plateau[ligne][colonne]; 
    }
    
    /**
     * @param v la position de la case demandée
     * @return la case à la position v.
     * Si la case est hors du plateau, leve une erreur.
     */
    public Case getCase(Vecteur v){
        int ligne = v.getLigne();
        int colonne = v.getColonne();
        if(ligne < 0 || ligne >= LARGEUR_PLATEAU || colonne < 0 || colonne >= LARGEUR_PLATEAU){
            throw new IllegalArgumentException("Case demandée est hors du plateau ! (Modele.getCase)");
        }
        return plateau[ligne][colonne]; 
    }

    /**
     * On abaisse le niveau d'eau de tous les joueurs.
     */
    public void vagueDeChaleur(){
        for(Joueur j : joueurs){
            if(!(j.emplacement instanceof Tunnel && j.emplacement.isExplored())){
                j.soif(); 
            }
        }
    }

    /**
     * @return Vrai si tous les joueurs ont assez d'eau, Faux sinon.
     */
    boolean checkNiveauSoif(){
        for(Joueur j : joueurs){
            if(j.mortDeSoif()){
                return false;
            }
        }
        return true;
    }

    /**
     * @return Vrai si la quantité de sable est supérieure à MAX_SABLE_LEVEL (par defaut 48), Faux sinon.
     */
    boolean qteSableMaxAtteinte(){
        return qteSable > MAX_SABLE_LEVEL;
    }
    public void leSableAugmente(){
        qteSable++;
    }

    public void leSableDiminue(){
        qteSable--;
    }

    /**
     * @return Vrai si la tempete a un niveau OK pour continuer, Faux sinon.
     */
    boolean checkNiveauTempete(){
        return niveauDeTempete < MAX_TEMPETE_LEVEL;
    }

    /**
     * On augmente le niveau de tempete selon les regles du jeu
     */
    void laTempeteSeDechaine(){
        cranTempete++;
        if(niveauDeTempete == 2 && cranTempete == 1) {
            niveauDeTempete++;
            cranTempete = 0;
        }
        if(niveauDeTempete == 4 && cranTempete == 4) {
            niveauDeTempete++;
            cranTempete = 0;
        }
        if(niveauDeTempete == 5 && cranTempete == 3) {
            niveauDeTempete++;
            cranTempete = 0;
        }
        if(niveauDeTempete == 6 && cranTempete == 2){
            niveauDeTempete++;
            cranTempete = 0;
        }
        switch (nbJoueurs) {
            case 2 -> {
                if (niveauDeTempete == 3 && cranTempete == 3) {
                    niveauDeTempete++;
                    cranTempete = 0;
                }
            }
            case 3, 4 -> {
                if (niveauDeTempete == 3 && cranTempete == 4) {
                    niveauDeTempete++;
                    cranTempete = 0;
                }
            }
            case 5 -> {
                if (niveauDeTempete == 3 && cranTempete == 5) {
                    niveauDeTempete++;
                    cranTempete = 0;
                }
            }
        }
    }

    /**
     * On déplace l'oeil dans la direction dir, avec la force force.
     * Si l'oeil est bloqué, on ne fait rien.
     */
    public void deplacerOeil(Direction dir, int force){
        for(int i = 0; i < force; i++){
            oeil.echange(dir);
        }
    }

    public void pisteDecouverte(){
        this.piecesPosables = true;
    }

    //utilitaires pour la vue
    /**
    * @return Vrai si la case à la position v est retournée, Faux sinon.
    */
    boolean estRetournee(Vecteur v){ //equivalent dans joueur avec effet de bord existant
        Case macase = plateau[v.getLigne()][v.getColonne()];
        return macase instanceof Tuile && ((Tuile) macase).isExplored();
    }
    /**
    * @return Vrai si la case à la position v est atteignable par le joueur j en se deplaçant, Faux sinon.
     */
    public boolean peutSeDeplacer(Joueur j, Vecteur v){ //equivalent dans joueur avec effet de bord existant
        Case macase = plateau[v.getLigne()][v.getColonne()];
        for(Direction d : Direction.values()){
            if(j.deplacable(d) && macase.equals(j.emplacement.getVoisin(d))){
                return true;
            }
        }
        return false;
    }
    /**
    * @return Vrai si la case à la position v peut être desensablée par le Joueur j, Faux sinon.
     */
    public boolean peutDesensabler(Joueur j, Vecteur v){
        Case macase = plateau[v.getLigne()][v.getColonne()];
        for(Direction d : Direction.values()){
            if(macase instanceof Tuile && ((Tuile) macase).desensablable() && macase.equals(j.emplacement.getVoisin(d))){
                return true;
            }
        }
        return false;
    }

    /*
    * Pioche une carte dans la pioche et la retourne. La pioche ne peut pas etre vide parce que le jeu serait fini avant ça. La carte a un effet sur le modele, et donc on notifie les observateurs.
     */
    public CarteTempete piocher() {
        CarteTempete t = (CarteTempete) this.pileTempete.retirerCarte();
        t.action();
        return t;
    }

    /**
     * @return Vrai si le jeu est fini, Faux sinon.
     */
    public boolean estPerdu(){
        return !checkNiveauSoif() || !checkNiveauTempete() || qteSableMaxAtteinte();
    }
    public boolean estGagne(){
        for(Joueur j : joueurs){
            if(!(j.emplacement instanceof PisteDecollage)) {
                return false;
            }
        }
        for(PiecesMoteur p : pieces){
            if(!p.estInstallee()){
                return false;
            }
        }
        return true;
    }

    void passeProchainJoueurSiTourFini(){
        if(joueurs.get(joueurCourant).tourTermine()){
            joueurCourant = (joueurCourant + 1) % joueurs.size();
            joueurs.get(joueurCourant).resetActions();
        }
    }

    public void faitApparaitrePiecesSiPossible(){
        for(PiecesMoteur p : pieces) {
            System.out.println("Piece : " + p.getNom() + " ");
            if(p.peutApparaitre()){
                p.apparaitre();
                System.out.println("OMG !!!! " + p.getNom() + " EST APPARU !");
                System.out.println(p.apparition.getPosition().toString());
            }
        }
    }
    public ArrayList<Joueur> getJoueurs(){
        return joueurs;
    }


    
}




