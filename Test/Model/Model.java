package Test.Model;


import org.junit.jupiter.api.Test;

import Model.*;

import static org.junit.jupiter.api.Assertions.*;


public class Model {
    @Test
    public void test_getCase() {
        Modele m = new Modele();
        for(int i = 0; i < m.LARGEUR_PLATEAU; i++){
            for(int j = 0; j < m.LARGEUR_PLATEAU; j++){
                Vecteur v = new Vecteur(i, j);
                Case t = m.getCase(v);
                assertEquals(t, m.plateau[i][j]);
            }
        }
    }

    @Test
    public void test_wellFormed_Pieces(){
        Modele m = new Modele();
        for(PiecesMoteur p : m.pieces){
            assertTrue(p.checkWellConstructed());
        }
    }

    @Test
    public void test_Oeil(){
        Modele m = new Modele();
        int oeilCount = 0;
        int tuileCount = 0;
        for(int i = 0; i<m.LARGEUR_PLATEAU ; i++){
            for(int j = 0; j<m.LARGEUR_PLATEAU ; j++){
                Vecteur v = new Vecteur(i, j);
                Case c = m.getCase(v);
                if(c instanceof Oeil){
                    oeilCount++;
                }else if(c instanceof Tuile){
                    tuileCount++;
                }
            }
        }
        assertEquals(1, oeilCount);
        assertEquals(24, tuileCount);
    }

    @Test
    void test_sable() {
        Modele m = new Modele();
        int sableCount = 0;
        for(int i = 0; i<m.LARGEUR_PLATEAU ; i++){
            for(int j = 0; j<m.LARGEUR_PLATEAU ; j++){
                Vecteur v = new Vecteur(i, j);
                Case c = m.getCase(v);
                if(c instanceof Tuile){
                    ((Tuile)c).ensable();
                    System.out.println(((Tuile)c).getSable() + " " + c.getPosition().toString());
                    sableCount+=((Tuile)c).getSable();
                }
            }
        }
        assertEquals(m.qteSable, sableCount);
    }

    @Test
    void test_deplacement_oeil(){
        Modele m = new Modele();
        Vecteur p = m.oeil.getPosition();
        m.deplacerOeil(Direction.NORD, 2);
        assertTrue(p.add(new Vecteur(-2, 0)).equals(m.oeil.getPosition()));
        m.deplacerOeil(Direction.SUD, 2);
        assertTrue(p.add(new Vecteur(0, 0)).equals(m.oeil.getPosition()));
        m.deplacerOeil(Direction.EST, 2);
        assertTrue(p.add(new Vecteur(0, 2)).equals(m.oeil.getPosition()));
        m.deplacerOeil(Direction.OUEST, 2);
        assertTrue(p.add(new Vecteur(0, 0)).equals(m.oeil.getPosition()));
    }

    @Test
    void test_joueur_mort_de_soif_apres_vague_chaleur() {
        Modele m = new Modele();
        m.addJoueur("Joueur0", "");
        
        // Joueur j = m.joueurs.get(0);
        for (int i = 0; i < 5; i++) { //! attention mon coco c'est pas un magic number le 5 ici 
            m.vagueDeChaleur();
        }
        
        assertTrue(m.estPerdu());
    }


}
