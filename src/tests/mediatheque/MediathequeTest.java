package tests.mediatheque;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.Mediatheque;
import mediatheque.OperationImpossible;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.Audio;
import mediatheque.document.Livre;
import mediatheque.document.Video;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MediathequeTest {

	private String nom;
    private Mediatheque med;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();



    @Before
    public void setUp() throws Exception {
        this.nom = "nom";
        this.med = new Mediatheque(this.nom);

        for (int i=1; i<4; i++){
            med.ajouterGenre("genre "+i);
            med.ajouterLocalisation("salle "+i, "rayon "+i);
            med.ajouterDocument(new Audio("code "+i, new Localisation("salle "+i, "rayon "+i), "title "+i, "auteur "+i, "year "+i, new Genre("genre "+i), "class "+i));
            med.ajouterDocument(new Livre("code "+i*10, new Localisation("salle "+i, "rayon "+i), "title "+i, "auteur "+i, "year "+i, new Genre("genre "+i), i*100));            
            med.ajouterDocument(new Video("code "+i*100, new Localisation("salle "+i, "rayon "+i), "title "+i, "auteur "+i, "year "+i, new Genre("genre "+i), i*10, "mention "+i));
        }


        med.ajouterCatClient("reduc", 2, 12.00, 4.00, 2.00, true);

        med.ajouterGenre("genre 4");
        med.ajouterLocalisation("salle 4", "rayon 4");

        med.ajouterCatClient("normal", 6, 20.00, 6.00, 4.00, false);
        med.ajouterCatClient("premium", 10, 40.00, 20.00, 6.00, false);
        med.inscrire("le floch", "floriane", "address 1", "reduc", 25);
        med.inscrire("dennetiere", "paul", "address 2", "normal");
        //med.inscrire("dupont", "marie", "address 3", "premium");
        med.metEmpruntable("code 200");
        med.metEmpruntable("code 20");
        med.metEmpruntable("code 300");
        med.metEmpruntable("code 1");

        med.metEmpruntable("code 10");

        //med.emprunter("le floch", "floriane", "code 200");
        med.emprunter("le floch", "floriane", "code 20");
        //med.emprunter("dennetiere", "paul", "code 300");
        med.emprunter("dennetiere", "paul", "code 1");

        System.setOut(new PrintStream(outContent));

    }

    @After
    public void tearDown() throws Exception {
        this.med = null;

    }

    @Test
    public void empty() throws Exception {
        med.empty();
        assertEquals(0, med.getLocalisationsSize());
        assertEquals(0, med.getFicheEmpruntsSize());
        assertEquals(0, med.getDocumentsSize());
        assertEquals(0, med.getClientsSize());
        assertEquals(0, med.getCategoriesSize());
        assertEquals(0, med.getGenresSize());
    }

    @Test
    public void chercherGenre() throws Exception {
        for (int i=1; i<4; i++) {
            assertEquals(new Genre("genre "+i), med.chercherGenre("genre "+i));
        }
        assertEquals(null, med.chercherGenre("genre"));
    }

    @Test
    public void supprimerGenre() throws Exception {
        med.supprimerGenre("genre 4");
        assertEquals(null, med.chercherGenre("genre 4"));
    }

    @Test (expected = OperationImpossible.class)
    public void supprimerGenreAvecDoc() throws Exception {
        med.supprimerGenre("genre 1");
    }

    @Test (expected = OperationImpossible.class)
    public void supprimerGenreNonExiste() throws Exception {
        med.supprimerGenre("genre 45");
    }

    @Test
    public void ajouterGenre() throws Exception {
        med.ajouterGenre("genre 5");
        assertEquals(new Genre("genre 5"), med.chercherGenre("genre 5"));
    }

    @Test(expected = OperationImpossible.class)
    public void ajouterGenreException() throws Exception {
        med.ajouterGenre("genre 1");
    }

    @Test
    public void modifierGenre() throws Exception {
        med.modifierGenre("genre 2", "genre 5");
        assertEquals(new Genre("genre 5"), med.chercherGenre("genre 5"));
        assertEquals(null, med.chercherGenre("genre 2"));
    }

    @Test
    public void listerGenres() throws Exception {
        med.listerGenres();
    }

    @Test
    public void getGenreAt() throws Exception {
        for (int i=1; i<4; i++) {
            assertEquals(new Genre("genre "+i), med.getGenreAt(i-1));
        }
        assertEquals(null, med.getGenreAt(4));
        assertEquals(null, med.getGenreAt(-4));
    }

    @Test
    public void getGenresSize() throws Exception {
        int size = med.getGenresSize();
        med.ajouterGenre("genre 31");
        assertEquals(size+1, med.getGenresSize());
    }

    @Test(expected = OperationImpossible.class)
    public void supprimerLocalisationAvecDoc() throws Exception {
        med.supprimerLocalisation("salle 1", "rayon 1");
    }

    @Test(expected = OperationImpossible.class)
    public void supprimerLocalisationNonExiste() throws Exception {
        med.supprimerLocalisation("salle 3", "rayon 1");
    }

    @Test
    public void supprimerLocalisation() throws Exception {
        med.supprimerLocalisation("salle 4", "rayon 4");

    }

    @Test
    public void chercherLocalisation() throws Exception {
        for (int i=1; i<4; i++) {
            assertEquals(new Localisation("salle "+i, "rayon "+i), med.chercherLocalisation("salle "+i, "rayon "+i));
        }
        assertEquals(null, med.chercherLocalisation("genre 1", "rayon 2"));
    }

    @Test
    public void ajouterLocalisation() throws Exception {
        med.ajouterLocalisation("salle 56", "rayon 23");
        assertEquals(new Localisation("salle 56", "rayon 23"), med.chercherLocalisation("salle 56", "rayon 23"));
    }

    @Test(expected = OperationImpossible.class)
    public void ajouterLocalisationExiste() throws Exception {
        med.ajouterLocalisation("salle 1", "rayon 1");
    }

    @Test
    public void modifierLocalisation() throws Exception {
        Localisation locOld = new Localisation("salle 1", "rayon 1");
        Localisation locNew = new Localisation("salle 5", "rayon 3");
        med.modifierLocalisation(locOld, "salle 5", "rayon 3");
        assertEquals(locNew, med.chercherLocalisation("salle 5", "rayon 3"));
        assertEquals(null, med.chercherLocalisation("salle 1", "rayon 1"));
    }

    @Test(expected = OperationImpossible.class)
    public void modifierLocalisationNotExists() throws Exception {
        Localisation locOld = new Localisation("salle 1", "rayon 4");
        med.modifierLocalisation(locOld, "salle 1", "rayon 3");
    }

    @Test(expected = OperationImpossible.class)
    public void modifierLocalisationNull() throws Exception {
        Localisation locOld = new Localisation(null, "rayon 4");
        med.modifierLocalisation(locOld, "salle 1", "rayon 3");
    }

    @Test
    public void listerLocalisations() throws Exception {
        med.listerLocalisations();
    }

    @Test
    public void getLocalisationAt() throws Exception {
        for (int i=1; i<4; i++) {
            assertEquals(new Localisation("salle "+i, "rayon "+i), med.getLocalisationAt(i-1));
        }
        assertEquals(null, med.getLocalisationAt(4));
        assertEquals(null, med.getLocalisationAt(-4));
    }

    @Test
    public void getLocalisationsSize() throws Exception {
        int size = med.getLocalisationsSize();
        med.ajouterLocalisation("ze", "za");
        assertEquals(size+1, med.getLocalisationsSize());
    }

    @Test
    public void chercherCatClient() throws Exception {

    }

    @Test
    public void supprimerCatClient() throws Exception {

    }

    @Test
    public void ajouterCatClient() throws Exception {

    }

    @Test
    public void modifierCatClient() throws Exception {

    }

    @Test
    public void listerCatsClient() throws Exception {

    }

    @Test
    public void getCategorieAt() throws Exception {

    }

    @Test
    public void getCategoriesSize() throws Exception {

    }

    @Test
    public void chercherDocument() throws Exception {

    }

    @Test
    public void ajouterDocument() throws Exception {

    }

    @Test
    public void retirerDocument() throws Exception {

    }

    @Test
    public void metEmpruntable() throws Exception {
        this.med.metEmpruntable("code 100");
        assertEquals(this.med.chercherDocument("code 100").estEmpruntable(), true);


    }

    @Test
    public void metConsultable() throws Exception {
        this.med.metConsultable("code 10");
        assertEquals(this.med.chercherDocument("code 10").estEmpruntable(), false);
    }

    @Test
    public void listerDocuments() throws Exception {
        this.med.listerDocuments();
        String test = "";
        for (int i =0; i< this.med.getDocumentsSize(); i++){
            test = test + this.med.getDocumentAt(i).toString() + "\n";
        }
        assertEquals(this.outContent.toString(), test);

        this.outContent.reset();
        this.med.empty();
        this.med.listerDocuments();
        assertEquals("(neant)\n", this.outContent.toString());
    }

    @Test
    public void getDocumentAt() throws Exception {
        String test = this.med.getDocumentAt(0).getCode();
        assertEquals(test, "code 200");
    }

    @Ignore
    public void getDocumentsSize() throws Exception {

    }
    @Test
    public void emprunter() throws Exception {
        this.med.emprunter("dennetiere","paul","code 10");
        assertEquals(this.med.chercherDocument("code 10").estEmprunte(), true);
    }

    @Test(expected = OperationImpossible.class)
    public void emprunterPasDoc() throws Exception {
        this.med.emprunter("dennetiere","paul","nocode");
    }
    @Test(expected = OperationImpossible.class)
    public void emprunterDejaEmprunte() throws Exception {
        this.med.emprunter("dennetiere","paul","code 200");
    }
    @Test(expected = OperationImpossible.class)
    public void emprunterPasEmpruntable() throws Exception {
        this.med.emprunter("dennetiere","paul","code 400");
    }

    @Test(expected = OperationImpossible.class)
    public void emprunterPasClient() throws Exception {
        this.med.emprunter("denere","paul","code 10");
    }

    @Test(expected = OperationImpossible.class)
    public void emprunterNePeutEmprunter() throws Exception {
        this.med.emprunter("le floch","floriane","code 10");
    }

    @Test
    public void restituer() throws Exception {
        this.med.emprunter("dennetiere","paul","code 10");
        this.med.restituer("dennetiere","paul","code 10");
        assertEquals(false, this.med.chercherDocument("code 100").estEmprunte());
    }

    @Test(expected = OperationImpossible.class)
    public void restituerPasClient() throws Exception{
        this.med.restituer("dene","pol","code 300");
    }

    @Test(expected = OperationImpossible.class)
    public void restituerPasDoc() throws Exception{
        this.med.restituer("dennetiere","paul","code");
    }

    @Test(expected = OperationImpossible.class)
    public void restituerPasEmprunte() throws Exception{
        this.med.restituer("dennetiere","paul","code 400");
    }

    @Ignore("teste dans les tests de ficheEmprunt")
    public void verifier() throws Exception {

    }

    @Test
    public void listerFicheEmprunts() throws Exception {
        this.med.listerFicheEmprunts();
        String test = "";
        for (int i =0; i< this.med.getFicheEmpruntsSize(); i++){
            test = test + Integer.toString(i) + ": "+ this.med.getFicheEmpruntAt(i).toString() + "\n";
        }
        assertEquals(test, this.outContent.toString());

        this.outContent.reset();
        this.med.empty();
        this.med.listerFicheEmprunts();
        assertEquals("(neant)\n", this.outContent.toString());
    }

    @Test
    public void getFicheEmpruntAt() throws Exception {
        assertEquals("code 200", this.med.getFicheEmpruntAt(0).getDocument().getCode().toString());
    }

    @Ignore
    public void getFicheEmpruntsSize() throws Exception {

    }

    @Test
    public void inscrire() throws Exception {
        this.med.inscrire("denne","paul","lille","reduc");
        assertEquals("denne", this.med.chercherClient("denne","paul").getNom());
    }

    @Test(expected = OperationImpossible.class)
    public void inscrireDejaClient() throws Exception{
        this.med.inscrire("dennetiere","paul","lille","normal");
    }


    @Test(expected = OperationImpossible.class)
    public void resilierEmprunEnCours() throws Exception {
        this.med.resilier("dennetiere","paul");
    }

    @Test
    public void resilier() throws Exception{
        this.med.inscrire("denne","paul","lille","normal");
        Client cli = this.med.chercherClient("denne","paul");
        this.med.resilier("denne","paul");
        assertNull(this.med.chercherClient("denne","paul"));
        String test = "(stat) Nombre d'emprunts effectues par \"" + cli.getNom()
                + "\" : " + cli.getNbEmpruntsEffectues() + "\n";
        assertEquals(test, this.outContent.toString());
    }

    @Test(expected = OperationImpossible.class)
    public void resilierPasClient() throws Exception{
        this.med.resilier("denne","paul");
    }

    @Test
    public void modifierClient() throws Exception {
        Client cli = this.med.chercherClient("dennetiere","paul");
        this.med.modifierClient(cli,"dennetiere","david","paris","reduc",1);
        assertEquals("david", this.med.chercherClient("dennetiere","david").getPrenom());
        assertEquals("paris", this.med.chercherClient("dennetiere","david").getAdresse());
        assertEquals("reduc", this.med.chercherClient("dennetiere","david").getCategorie().getNom());
        assertEquals(true, this.med.chercherClient("dennetiere","david").getCategorie().getCodeReducUtilise());
    }
    @Test(expected = OperationImpossible.class)
    public void modifierClientPasDeHash() throws Exception {
        CategorieClient cat = new CategorieClient("normal", 6, 20.00, 6.00, 4.00, false);
        Client cli = new Client("dede","p","lille",cat);
        this.med.modifierClient(cli, "depo","polo","lille","reduc",0);
    }

    @Test(expected = OperationImpossible.class)
    public void modifierClientPasDeCat() throws Exception {
        CategorieClient cat = new CategorieClient("ed", 6, 20.00, 6.00, 4.00, false);
        Client cli = this.med.chercherClient("dennetiere","paul");
        this.med.modifierClient(cli, "depo","polo","lille","cat",0);
    }

    @Test
    public void changerCategorie() throws Exception {

    }

    @Test
    public void changerCodeReduction() throws Exception {

    }

    @Test
    public void chercherClient() throws Exception {

    }

    @Test
    public void listerClients() throws Exception {

    }

    @Test
    public void existeClient() throws Exception {

    }

    @Test
    public void getClientAt() throws Exception {

    }

    @Test
    public void getClientsSize() throws Exception {

    }

    @Test
    public void findClient() throws Exception {

    }

    @Test
    public void afficherStatistiques() throws Exception {

    }

    @Test
    public void getNom() throws Exception {

    }

    @Test
    public void initFromFile() throws Exception {

    }

    @Test
    public void saveToFile() throws Exception {

    }

}
