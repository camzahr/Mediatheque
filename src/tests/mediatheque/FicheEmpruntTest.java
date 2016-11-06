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
        this.client = new Client("denne","paul","lille",new CategorieClient("cat",1,2.0,0.5, 0.5, false));
        Localistion local = new Localisation("a","2");
        this.doc = new Audio("code", local, "titre", "autor", "annee", new Genre("scifi"), "classification");
        this.med = new Mediatheque("med");
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
    public void verifier() throws Exception{
        this.fiche.verifier();
        assertEquals(true, this.fiche.getDepasse());
    }


    @Ignore
    public void modifierClient() throws Exception {
    }

    @Test
    public void correspond() throws Exception {
        Localisation local = new Localisation("a","2");
        Document docTest = new Audio("codeTest", local, "titre2", "autor", "annee", new Genre("scifi"), "classification");
        assertEquals(false, this.fiche.correspond(this.fiche.getClient(), docTest));
        Client client2 = new Client("lf","flo","paris",new CategorieClient("2",2,1.0,1.0,1.0,false));
        assertEquals(false, this.fiche.correspond(client2, this.fiche.getDocument()));
        assertEquals(true, this.fiche.correspond(this.fiche.getClient(), this.fiche.getDocument()));

    }

    @Test
    public void restituer() throws Exception {
        if (this.fiche.getDocument().estEmprunte()){
            this.fiche.restituer();
            assertEquals(false, this.fiche.getDocument().estEmprunte());
            assertEquals(0, this.fiche.getClient().getNbEmpruntsEnCours());
        }
    }

    @Ignore
    public void getClient() throws Exception {

    }

    @Ignore
    public void getDocument() throws Exception {

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
