package Test.Model;

import Model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Other {
    @Test
    public void testVecteur() {
        Vecteur v = new Vecteur(1, 2);
        assertEquals(v.getLigne(), 1);
        assertEquals(v.getColonne(), 2);
        assertEquals(v.toString(), "(1, 2)");
        v.setColonne(0);
        v.setLigne(0);
        assertEquals(v.getLigne(), 0);
        assertEquals(v.getColonne(), 0);
        assertEquals(v.toString(), "(0, 0)");
        Vecteur v2 = new Vecteur();
        assertEquals(v2.getLigne(), 0);
        assertEquals(v2.getColonne(), 0);
        assertEquals(v2.toString(), "(0, 0)");

        Vecteur v3 = new Vecteur(v);
        assertEquals(v3.getLigne(), 0);
        assertEquals(v3.getColonne(), 0);
        assertEquals(v3.toString(), "(0, 0)");
        v3.setLigne(1);
        v3.setColonne(2);

        Vecteur v4 = v3.add(new Vecteur(2, 3));
        assertEquals(v4.getLigne(), 3);
        assertEquals(v4.getColonne(), 5);
        assertEquals(v4.toString(), "(3, 5)");

        assertTrue(v3.equals(new Vecteur(1, 2)));

    }

    

}
