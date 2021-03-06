package tests.mediatheque;

import static org.junit.Assert.*;
import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Audio;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AudioTest {


	Audio audio;
    Genre genre;
    Localisation loc;

    @Before
    public void setUp() throws Exception {
        genre = new Genre("Rock");
        loc = new Localisation("salle1", "rayon1");
        audio = new Audio("poeizf", loc, "Yellow Submarine", "The Beatles", "2000", genre, "cd");
        audio.metEmpruntable();
    }

    @After
    public void tearDown() throws Exception {
        audio = null;
    }

    @Test (expected = OperationImpossible.class)
    public void create() throws Exception {
        Audio audioCurrent = new Audio("poiii", loc, "Yellow Submarine", "The Beatles", null, genre, "cd");
    }

    @Test
    public void getStatFirst() throws Exception {
        int stat = audio.getStat();
        audio.emprunter();
    	assertEquals(stat+1, audio.getStat());
    }


    @Test
    public void getClassification() throws Exception {
        assertEquals("cd", audio.getClassification());
    }

    @Test
    public void emprunter() throws Exception {
        audio.emprunter();
    }

    @Test
    public void dureeEmprunt() throws Exception {
        assertEquals(4*7, audio.dureeEmprunt());
    }

	@Test
    public void tarifEmprunt() throws Exception {
        assertEquals(1.0, audio.tarifEmprunt(),1.0);
    }

    @Ignore("Out of the scope")
    public void testToString() throws Exception {

    }

}
