package tests.mediatheque;

import static org.junit.Assert.*;
import mediatheque.FicheEmprunt;
import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.Mediatheque;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.Audio;
import mediatheque.document.Document;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FicheEmpruntTest {

	private static int nbEmpruntsTotal = 0;
    private Client client;
    private Audio doc;
    private Mediatheque med;
    private FicheEmprunt fiche;


    @Before
    public void setUp() throws Exception {
        this.client = new Client("Camilleri","Jeremy","Paris",new CategorieClient("voisin",1,2.0,4.0, 5.0, false));
        Localisation local = new Localisation("Salle 1","A1");
        this.doc = new Audio("abc", local, "Hey", "Beyonce", "1980", new Genre("rnb"), "All Public");
        
        doc.metEmpruntable();
        this.med = new Mediatheque("myMed");
        this.fiche = new FicheEmprunt(med,client,doc);
    }

    @After
    public void tearDown() throws Exception {
 
    }
    
    @Test
    public void verifierInit() throws Exception {
        this.fiche.verifier();
        assertEquals(false, this.fiche.getDepasse());
    }

    @Test
    public void modifierClient() throws Exception {
    	fiche.modifierClient(new Client("Flo","Anne","Boulbi",new CategorieClient("voisin",1,2.0,4.0, 5.0, false)));
    	assertEquals("Flo", fiche.getClient().getNom());
    }

    @Test
    public void correspond() throws Exception {
        Localisation local = new Localisation("Salle 2","B3");
        Document docTest = new Audio("123", local, "Yo", "Rihanna", "2000", new Genre("bad"), "classification");
        assertEquals(false, fiche.correspond(client, docTest));
        assertEquals(true, fiche.correspond(client, doc));
    }

    @Test
    public void restituer() throws Exception {
        fiche.getDocument().emprunter();
        fiche.restituer();
        assertEquals(false, fiche.getDocument().estEmprunte());
        assertEquals(0, fiche.getClient().getNbEmpruntsEnCours());
        
    }

    @Ignore
    public void getClient() throws Exception {
    	assertEquals(client,fiche.getClient());
    }

    @Ignore
    public void getDocument() throws Exception {
    	assertEquals(doc,fiche.getDocument());
    }

    @Ignore
    public void getDateEmprunt() throws Exception {
    	
    }

    @Ignore
    public void getDateLimite() throws Exception {

    }

    @Ignore
    public void getDepasse() throws Exception {

    }

    @Ignore
    public void getDureeEmprunt() throws Exception {

    }

    @Ignore
    public void getTarifEmprunt() throws Exception {

    }

    @Test(timeout = 1000)
    public void changementCategorie() throws Exception {
        

    }

    @Ignore
    public void afficherStatistiques() throws Exception {

    }

}
