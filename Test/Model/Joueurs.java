package Test.Model;


import org.junit.jupiter.api.Test;

import Model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class Joueurs {

    @Test
    public void test_Construct() {
        Modele m = new Modele();
        m.addJoueur("Joueur0", "");
        Joueur j = m.joueurs.get(0);
        assertEquals(j.getWaterLvl(), j.getMaxWaterLvl());
        j.soif();
        assertEquals(j.getWaterLvl(), j.getMaxWaterLvl() - 1);
        j.boit();
        assertEquals(j.getWaterLvl(), j.getMaxWaterLvl());
        assertEquals(j.getNbActions(), j.getNbActionsMax());
    }

    @Test
    public void test_deplacer() {
        Modele m = new Modele();
        m.addJoueur("Joueur0", "");
        Joueur j = m.joueurs.get(0);

        for(Direction d : Direction.values()){
            boolean deplacementQuiFonctione = j.deplacable(d);
            Vecteur v = j.getEmplacement().getPosition();
            j.deplacer(d);
            if(deplacementQuiFonctione) {
                assertTrue(j.getEmplacement().getPosition().equals(v.add(d.toVecteur())));
            }else {
                assertTrue(j.getEmplacement().getPosition().equals(v));
            }
        }
    }

    @Test
    void test_getTuilesVoisines(){
        Modele m = new Modele();
        m.addJoueur("Joueur0", "");
        Joueur j = m.joueurs.get(0);
        ArrayList<Tuile> voisines = j.getTuilesVoisines();
        for(Tuile t : voisines){
            boolean existeDirTqTCaseObtenue = false;
            for(Direction d : Direction.values()){
                Case c = j.getEmplacement().getVoisin(d);
                if(c instanceof Tuile && c.equals(t)){
                    existeDirTqTCaseObtenue = true;
                    break;
                }
            }
            assertTrue(existeDirTqTCaseObtenue);
        }
    }

    @Test
    void test_retourneCaseCourante() {
        Modele m = new Modele();
        m.addJoueur("Joueur0", "");
        Joueur j = m.joueurs.get(0);
        int i = 0;
        while(!j.getEmplacement().retournable()){
            Case c = m.getCase(new Vecteur(i/m.LARGEUR_PLATEAU, i%m.LARGEUR_PLATEAU));
            if(c instanceof Tuile){
                j.teleporter((Tuile)c);
            }
            i++;
        }
        j.retourneCaseCourante();
        assertEquals(j.getNbActions(), j.getNbActionsMax() - 1);
        assertTrue(j.getEmplacement().isExplored());
    }

    @Test
    void test_prendrePiece(){
        //!todo
    }

    
}
