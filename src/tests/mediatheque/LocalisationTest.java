package tests.mediatheque;

import static org.junit.Assert.*;
import mediatheque.Localisation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalisationTest {

	private static final long serialVersionUID = 3L;

    /**
     * Salle ou ranger le document.
     */
    private String salle;

    /**
     * Rayon ou ranger le document.
     */
    private String rayon;

    private Localisation local;

    @Before
    public void setUp() throws Exception {
        salle = "Salle 1";
        rayon = "1";
        local = new Localisation(salle, rayon);

    }

    @After
    public void tearDown() throws Exception {
    	
    }

    @Test
    public void getSalle() throws Exception {
        assertEquals(salle, local.getSalle());
    }


    @Test
    public void getRayon() throws Exception {
        assertEquals(rayon, local.getRayon());
    }

}
