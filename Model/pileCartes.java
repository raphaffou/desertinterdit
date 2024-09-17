package Model;
import java.util.Stack;
import java.util.Collections;



public abstract class pileCartes {
    Modele modele;
    //type permettant un shuffle
    Stack<Cartes> pile;
    public pileCartes(Modele modele) {
        this.modele = modele;
        pile = new Stack<Cartes>();
    }
    public void ajouterCarte(Cartes carte) {
        pile.push(carte);
    }
    public Cartes retirerCarte() {
        return pile.pop();
    }
    public Cartes voirCarte() {
        return pile.peek();
    }
    public void shuffle() {
        Collections.shuffle(pile);
    }
}

