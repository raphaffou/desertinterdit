package Model;

public abstract class Case {
    public Modele modele;
    private Vecteur position;

    public Case(Modele modele, Vecteur position) {
        this.modele = modele;
        this.position = position;
    }

    // getters
    public Vecteur getPosition() {
        return position;
    }


    // setters
    public void setPosition(Vecteur position) {
        this.position = position;
    }

    // methods
    /**
     * Echange la position de la case avec celle de la case voisine dans la direction dir si c'est possible, sinon ne fait rien
     */
    public void echange(Direction dir){
        modele.notifyObservers();
        Case voisin = this.getVoisin(dir);
        if(voisin != null){
            Vecteur pos = this.getPosition().copy();
            //echange cases
            modele.plateau[voisin.getPosition().copy().getLigne()][voisin.getPosition().copy().getColonne()] = this;
            modele.plateau[pos.getLigne()][pos.getColonne()] = voisin;
            //echange positions
            this.setPosition(voisin.getPosition().copy());
            voisin.setPosition(pos);
        }
    }

    /**
     * Renvoie la case voisine dans la direction dir si elle existe, sinon renvoie null
     */
    public Case getVoisin(Direction dir){
        Vecteur pos = this.getPosition();
        Vecteur newPos = pos.add(dir.toVecteur());
        try{
            return modele.getCase(newPos);
        }catch(IllegalArgumentException e){
            //devrait pas poser de probleme, on deborde jamais plus d'une case
            return modele.getCase(pos);
        }
    }

    public boolean equals(Case c){
        return this.getPosition().equals(c.getPosition());
    }


    public abstract String filename();




}
