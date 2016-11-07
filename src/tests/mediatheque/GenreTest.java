package tests.mediatheque;

import static org.junit.Assert.*;
import mediatheque.Genre;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GenreTest {

	private static final long serialVersionUID = 3L;

    /**
     * Nom du genre
     */
    private String nom;

    /**
     * Nombre de fois ou un document de ce genre a ete emprunte
     */
    private int nbEmprunts;

    private Genre genre;



    @Before
    public void setUp() throws Exception {
        nom = "Roman";
        genre = new Genre(nom);
    }

    @After
    public void tearDown() throws Exception {

    }
    
    @Test
    public void getNbEmprunts() throws Exception {
    	assertEquals(0, genre.getNbEmprunts());
    }

    @Test
    public void emprunter() throws Exception {
        assertEquals(0, genre.getNbEmprunts());
        genre.emprunter();
        assertEquals(1, genre.getNbEmprunts());
    }

    @Test
    public void getNom() throws Exception {
        assertEquals(nom, genre.getNom());
    }

    @Test
    public void modifier() throws Exception {
        genre.modifier("BD");
        assertEquals("BD",genre.getNom());
    }

    

    @Test
    public void afficherStatistiques() throws Exception {
    	genre.afficherStatistiques();
    }

}
