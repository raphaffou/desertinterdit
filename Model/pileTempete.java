package Model;

public class pileTempete extends pileCartes {
    public pileTempete(Modele modele) {
        super(modele);
        remplissagePile();
    }

    public void remplissagePile(){
        for(int i = 0; i < 3; i++) {
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_NORD, 1, modele));
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_SUD, 1, modele));
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_EST, 1, modele));
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_OUEST, 1, modele));
            ajouterCarte(new CarteTempete(TypeTempete.LA_TEMPETE_SE_DECHAINE, -1, modele));
        }
        for(int i = 0; i < 2; i++) {
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_NORD, 2, modele));
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_SUD, 2, modele));
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_EST, 2 , modele));
            ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_OUEST, 2 , modele));
        }
        ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_NORD, 3 , modele));
        ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_SUD, 3, modele));
        ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_EST, 3 , modele));
        ajouterCarte(new CarteTempete(TypeTempete.DEPLACEMENT_OUEST, 3 , modele));
        for(int i = 0; i < 4; i++) {
            ajouterCarte(new CarteTempete(TypeTempete.VAGUE_DE_CHALEUR, -1, modele));
        }
        shuffle();
    }
    @Override
    public Cartes retirerCarte() {
        if(pile.empty())
            remplissagePile();
        return pile.pop();
    }
}
