package tests.mediatheque;

import static org.junit.Assert.*;

import java.util.Date;

import mediatheque.FicheEmprunt;
import mediatheque.Genre;
import mediatheque.LettreRappel;
import mediatheque.Localisation;
import mediatheque.Mediatheque;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.Audio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LettreRappelTest {
	
	private static final long serialVersionUID = 2L;
    /**
     * Corps minimal de la lettre (<em>Tout emprunt est desormais
     * impossible</em>)
     */
    private final static String DEFAUT =
                    "Tout emprunt est desormais impossible\n";
    private final static String dashLine = "\n----------------------------------------------------\n";
    private String nomMedia, entete, corps, fin;  
    /**
     * Date d'envoi de la lettre
     */
    private Date dateRappel;
    private FicheEmprunt enRetard;
    
    private LettreRappel lettre;
    
    private Client client;
    private Mediatheque med;
    private Audio audio;
    private Localisation local;
    private CategorieClient cat;
    
    
	@Before
	public void setUp() throws Exception {
		cat = new CategorieClient("voisin", 10, 2.0, 3.0, 4.0, false);
		client = new Client("Camilleri", "Jeremy", "37 quai de grenelle", cat);
		med = new Mediatheque("myMed");
		local = new Localisation("Salle1", "A");
		audio = new Audio("code", local, "Tintin", "Hergé", "1980", new Genre("BD"), "classification");
        
		audio.metEmpruntable();
		
		enRetard = new FicheEmprunt(med, client, audio);
		lettre = new LettreRappel("Lettre1", enRetard);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
    public void relancer() throws Exception {
		lettre.relancer();
    }

}
