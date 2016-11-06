package tests.mediatheque;

import static org.junit.Assert.*;
import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Livre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LivreTest {

	private Livre l;
	private Localisation loc;
	private Genre g;
	
	@Before
	public void setUp() throws Exception {
		loc = new Localisation("Salle 1", "A1");
		g = new Genre("BD");
		l = new Livre("abc", loc, "Tintin", "Serge", "1997", g, 40);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected = OperationImpossible.class)
	public void testConstructorFirst() throws Exception {
		Livre l1;
		l1 = new Livre("abc", loc, "Tintin", "Serge", "1997", g, -2);
	}

    @Test
    public void getStat() throws Exception {
        assertEquals(0,Livre.getStat());
    }

    @Test (expected = OperationImpossible.class)
    public void emprunter() throws Exception {
            l.emprunter();
    }

    @Test
    public void dureeEmprunt() throws Exception {
        assertEquals(6*7, l.dureeEmprunt());
    }

    @Test
    public void tarifEmprunt() throws Exception {
        assertEquals(0.5, l.tarifEmprunt(),0);
    }


    @Test(expected = mediatheque.OperationImpossible.class)
    public void invariantLivre() throws Exception {
        Localisation localisation = new Localisation("Allée 2","2B");
        Livre l1;
		l1 = new Livre("abc", loc, "Tintin", "Serge", "1997", g, -2);
	}

}
