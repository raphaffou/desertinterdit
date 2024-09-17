package Model;

import java.util.Collections;
import java.util.Stack;

public class PileRole {

    // Modele modele;
    public PileRole() {
        // this.modele = modele;
        pile = new Stack<String>();
        remplissagePile();
    }

    Stack<String> pile;

    public void ajouterCarte(String carte) {
        pile.push(carte);
    }
    public String retirerCarte() {
        if(pile.empty())
            return "Autre";
        return pile.pop();
    }
    public String voirCarte() {
        return pile.peek();
    }
    public void shuffle() {
        Collections.shuffle(pile);
    }

    public void remplissagePile(){
        ajouterCarte("Explorateur");
        ajouterCarte("Alpiniste");
        ajouterCarte("Archéologue");
        ajouterCarte("Archéologue");
        // ajouterCarte("Météorologue");
        ajouterCarte("Porteuse d'eau");
        shuffle();
    }
    
}
