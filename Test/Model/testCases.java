package Test.Model;

import Model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class testCases {

    Modele modele = new Modele();
    Vecteur position = new Vecteur(1, 3);

    // test de la class Model.Case
    @Test
    public void testCase(){
        Case c = modele.getCase(position);
        assertTrue(c.getPosition().equals(position));
        c.setPosition(position.add(new Vecteur(1, 2)));
        assertTrue(c.getPosition().equals(new Vecteur(2, 5)));
        c.setPosition(position);

        Case c2 = c.getVoisin(Direction.NORD);
        assertTrue(c2.getPosition().equals(new Vecteur(0, 3)));

        Case c3 = c.getVoisin(Direction.SUD);
        assertTrue(c3.getPosition().equals(new Vecteur(2, 3)));
        

        assertTrue(c2.getVoisin(Direction.NORD).equals(c2));

        c2.echange(Direction.NORD);
        assertTrue(c2.getPosition().equals(new Vecteur(0, 3)));
        Case c5 = c2.getVoisin(Direction.SUD);

        c2.echange(Direction.SUD);
        assertTrue(c2.getPosition().equals(new Vecteur(1, 3)));
        assertTrue(c5.equals(c2.getVoisin(Direction.NORD)));


    }


    @Test
    public void testTuile(){
        Tuile t = new Tuile(modele, position);
        assertTrue(t.getPosition().equals(position));
        assertFalse(t.isExplored());
        assertEquals(t.getJoueurs().size(), 0);
        assertEquals(t.getPieces().size(), 0);
        assertEquals(t.getSable(), 0);
        assertEquals(t.toString(), "Cité");
        t.ensable();
        assertEquals(t.getSable(), 1);
        t.ensable();
        assertEquals(t.getSable(), 2);
        t.desensable();
        assertEquals(t.getSable(), 1);
        t.desensable();
        assertEquals(t.getSable(), 0);
        assertThrows(IllegalStateException.class, () -> t.desensable());
        t.ensable();
        assertThrows(IllegalStateException.class, () -> t.retourne());
        t.desensable();
        assertDoesNotThrow(() -> t.retourne());
        assertThrows(IllegalStateException.class, () -> t.retourne());
        assertTrue(t.isExplored());


        Tuile t2 = new Tuile(modele, position, 10);
        assertEquals(t2.getSable(), 10);
        assertThrows(IllegalArgumentException.class, () -> new Tuile(modele, position, -1));

    }  


}


