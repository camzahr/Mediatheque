package tests.mediatheque;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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

import util.Datutil;

public class MediathequeTest {


	    private String nom;
	    private Mediatheque med;
	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	    private CategorieClient cat1;
	    private CategorieClient cat2;
	    private CategorieClient cat3;
	    
	    private Audio audio;
	    private Client client1;


	    @Before
	    public void setUp() throws Exception {
	        this.nom = "nom";
	        this.med = new Mediatheque(this.nom);
	        
	        File file = new File(nom + ".data");
	        if (file.exists()) {
	            file.delete();
	        }

	        med.ajouterGenre("Genre 1");
	        med.ajouterGenre("Genre 2");
	        med.ajouterGenre("Genre 3");
	        med.ajouterGenre("Genre 4");
	        med.ajouterGenre("Genre 35");
	        med.ajouterGenre("Genre 51");

	        med.ajouterLocalisation("Salle 1", "Rayon 1");
	        med.ajouterLocalisation("Salle 1", "Rayon 2");
	        med.ajouterLocalisation("Salle 2", "Rayon 1");
	        med.ajouterLocalisation("Salle 2", "Rayon 2");
	        med.ajouterLocalisation("Salle 20", "Rayon 20");
	        
	        
	        med.ajouterCatClient("Voisin", 3, 123.45, 6.7, 8.9, true);
	        med.ajouterCatClient("Membre", 4, 124.45, 7.7, 9.9, false);
	        med.ajouterCatClient("Famille", 5, 125.45, 8.7, 10.9, true);
	        
	        cat1 = new CategorieClient("Voisin", 3, 123.45, 6.7, 8.9, true);
	        cat2 = new CategorieClient("Membre", 4, 124.45, 7.7, 9.9, false);
	        cat3 = new CategorieClient("Famille", 5, 125.45, 8.7, 10.9, true);

	        audio = new Audio("abc", med.getLocalisationAt(0), "Life", "A", "1900", med.getGenreAt(0), "Classification");
	        
	        med.ajouterDocument(new Audio("abc", med.getLocalisationAt(0), "Life", "A", "1900", med.getGenreAt(0), "Classification"));
	        med.ajouterDocument(new Livre("def", med.getLocalisationAt(1), "Lord", "B", "1700", med.getGenreAt(1), 345));
	        med.ajouterDocument(new Video("rtf", med.getLocalisationAt(2), "Ring", "C", "2006", med.getGenreAt(2), 567, "COPYRIGHT"));
	        med.ajouterDocument(new Audio("tuc", med.getLocalisationAt(2), "Life", "A", "1900", med.getGenreAt(2), "Classification"));
	        
	        med.inscrire("Camilleri", "Jeremy", "adresse", "Voisin", 123);
	        med.inscrire("Flo", "Anne", "adresse 1", "Membre");
	        med.inscrire("Jude", "Hey", "adresse 3", new CategorieClient("peace"), 123);
	        
	        client1 = new Client("Camilleri", "Jeremy", "adresse", cat1, 123);
	        med.metEmpruntable(med.getDocumentAt(0).getCode());
	        med.metEmpruntable(med.getDocumentAt(1).getCode());
	        med.metEmpruntable(med.getDocumentAt(2).getCode());
	        med.metEmpruntable(med.getDocumentAt(3).getCode());

	        med.emprunter("Camilleri", "Jeremy", "abc");
	        med.emprunter("Camilleri", "Jeremy", "def");

	        //med.saveToFile();
	        System.setOut(new PrintStream(outContent));

	    }

	    @After
	    public void tearDown() throws Exception {

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
	        assertEquals("Genre 1", med.chercherGenre("Genre 1").getNom());
	        assertEquals(null, med.chercherGenre("Genre 678"));
	    }

	    @Test
	    public void supprimerGenre() throws Exception {
	    	assertEquals("Genre 35", med.chercherGenre("Genre 35").getNom());
	    	med.supprimerGenre("Genre 35");
	        assertEquals(null, med.chercherGenre("Genre 35"));
	    }

	    @Test (expected = OperationImpossible.class)
	    public void supprimerGenreAvecDoc() throws Exception {
	        med.supprimerGenre("Genre 1");
	    }

	    @Test (expected = OperationImpossible.class)
	    public void supprimerGenreNonExiste() throws Exception {
	        med.supprimerGenre("Genre 495");
	    }

	    @Test
	    public void ajouterGenre() throws Exception {
	        med.ajouterGenre("Genre 5");
	        assertEquals(new Genre("Genre 5"), med.chercherGenre("Genre 5"));
	    }

	    @Test(expected = OperationImpossible.class)
	    public void ajouterGenreException() throws Exception {
	        med.ajouterGenre("Genre 1");
	    }

	    @Test
	    public void modifierGenre() throws Exception {
	        med.modifierGenre("Genre 2", "Genre 15");
	        assertEquals(new Genre("Genre 15"), med.chercherGenre("Genre 15"));
	        assertEquals(null, med.chercherGenre("Genre 2"));
	    }

	    @Test
	    public void listerGenres() throws Exception {
	        med.listerGenres();
	        String test = "";

	        for (int i =0; i< this.med.getGenresSize(); i++){
	            test = test  + this.med.getGenreAt(i).toString()+ "\n";
	        }
	        assertEquals(test, this.outContent.toString());

	        
	    }

	    @Test
	    public void getGenreAt() throws Exception {
	        assertEquals("Genre 1", med.getGenreAt(0).getNom());
	    }

	    @Test
	    public void getGenresSize() throws Exception {
	        int size = med.getGenresSize();
	        med.ajouterGenre("genre 31");
	        assertEquals(size+1, med.getGenresSize());
	    }

	    @Test(expected = OperationImpossible.class)
	    public void supprimerLocalisationAvecDoc() throws Exception {
	        med.supprimerLocalisation("Salle 1", "Rayon 1");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void supprimerLocalisationNonExiste() throws Exception {
	        med.supprimerLocalisation("salle 300", "rayon 167");
	    }

	    @Test
	    public void supprimerLocalisation() throws Exception {
	        med.supprimerLocalisation("Salle 20", "Rayon 20");

	    }

	    @Test
	    public void chercherLocalisation() throws Exception {
	        assertEquals("Salle 1", med.chercherLocalisation("Salle 1", "Rayon 1").getSalle());
	    }

	    @Test
	    public void ajouterLocalisation() throws Exception {
	        med.ajouterLocalisation("Salle 89", "Rayon 23");
	        assertEquals("Salle 89", med.chercherLocalisation("Salle 89", "Rayon 23").getSalle());
	    }

	    @Test(expected = OperationImpossible.class)
	    public void ajouterLocalisationExiste() throws Exception {
	        med.ajouterLocalisation("Salle 1", "Rayon 1");
	    }

	    @Test
	    public void modifierLocalisation() throws Exception {
	        Localisation locNew = new Localisation("Salle 5", "Rayon 3");
	        med.modifierLocalisation(new Localisation("Salle 1", "Rayon 1"), "Salle 5", "Rayon 3");
	        assertEquals(locNew, med.chercherLocalisation("Salle 5", "Rayon 3"));
	        assertEquals(null, med.chercherLocalisation("Salle 1", "Rayon 1"));
	    }

	    @Test(expected = OperationImpossible.class)
	    public void modifierLocalisationNotExists() throws Exception {
	        med.modifierLocalisation(new Localisation("Salle 189", "Rayon 46789"), "Salle 145", "Rayon 1222");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void modifierLocalisationNull() throws Exception {
	        med.modifierLocalisation(new Localisation(null, "Rayon 478"), "salle 1", "rayon 3");
	    }

	    @Test
	    public void listerLocalisations() throws Exception {
	        med.listerLocalisations();
	    }

	    @Test
	    public void getLocalisationAt() throws Exception {
	        assertEquals("Salle 1", med.getLocalisationAt(0).getSalle());
	    }

	    @Test
	    public void getLocalisationsSize() throws Exception {
	        assertEquals(5, med.getLocalisationsSize());
	    }

	    @Test
	    public void chercherCatClient() throws Exception {
	        assertEquals(cat1, med.chercherCatClient("Voisin"));
	        assertEquals(cat2, med.chercherCatClient("Membre"));
	        assertEquals(null, med.chercherCatClient("egpoirh"));
	    }

	    @Test
	    public void supprimerCatClient() throws Exception {
	        med.supprimerCatClient("Famille");
	        assertEquals(null, med.chercherCatClient("Famille"));
	    }

	    @Test(expected = OperationImpossible.class)
	    public void supprimerCatClientAvecClients() throws Exception {
	        med.supprimerCatClient("Voisin");
	    }
	    @Test(expected = OperationImpossible.class)
	    public void supprimerCatClientNonExist() throws Exception {
	        med.supprimerCatClient("zaegrbpkml");
	    }

	    @Test
	    public void ajouterCatClient() throws Exception {
	        CategorieClient cat = new CategorieClient("Bonus", 6, 20.00, 6.00, 4.00, false);
	        
	    }

	    @Test
	    public void modifierCatClient() throws Exception {

	        med.modifierCatClient(this.med.getCategorieAt(0), "hourra", 7, 21.00, 7.00, 5.00, false);

	        assertEquals("hourra", this.med.getCategorieAt(0).getNom());
	    }

	    @Test(expected = OperationImpossible.class)
	    public void modifierCatClientNonExiste() throws Exception {
	        med.modifierCatClient(new CategorieClient("Hello"), "new", 7, 21.00, 7.00, 5.00, false);
	    }

	    @Test
	    public void listerCatsClient() throws Exception {
	        this.med.listerCatsClient();    
	    }

	    @Test
	    public void getCategorieAt() throws Exception {
	        assertEquals(cat1, med.getCategorieAt(0));
	    }

	    @Test
	    public void getCategoriesSize() throws Exception {

	        assertEquals(3, med.getCategoriesSize());
	    }

	    @Test
	    public void chercherDocument() throws Exception {
	        assertEquals(audio, med.chercherDocument("abc"));
	       
	        assertEquals(null, med.chercherDocument("ezrbhoijpk"));
	    }

	    @Test
	    public void ajouterDocument() throws Exception {
	        Audio audio = new Audio("azerty", new Localisation("Salle 1", "Rayon 1"), "Hey", "Sting", "1975", new Genre("Genre 1"), "class");
	        med.ajouterDocument(new Audio("azerty", new Localisation("Salle 1", "Rayon 1"), "Hey", "Sting", "1975", new Genre("Genre 1"), "class") );
	        assertEquals(audio, med.chercherDocument("azerty"));
	    }

	    @Test(expected = OperationImpossible.class)
	    public void ajouterDocumentExist() throws Exception {
	        med.ajouterDocument(new Audio("abc", new Localisation("Salle 10", "Rayon 10"), "Hey", "Sting", "1975", new Genre("good vibe"), "class") );
	    }

	    @Test(expected = OperationImpossible.class)
	    public void retirerDocumentNotExists() throws Exception {
	        med.retirerDocument("vezhoi");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void retirerDocumentEmprunte() throws Exception {
	        med.retirerDocument("abc");
	    }

	    @Test
	    public void retirerDocument() throws Exception {
	    	
	        med.retirerDocument("rtf");
	        assertEquals(null, med.chercherDocument("rtf"));
	    }

	    @Test
	    public void metConsultable() throws Exception {
	        this.med.metConsultable("tuc");
	        assertEquals(this.med.chercherDocument("tuc").estEmpruntable(), false);
	    }
	    
	    @Test
	    public void metEmpruntable() throws Exception {
	    	this.med.metConsultable("tuc");
	    	this.med.metEmpruntable("tuc");
	        assertEquals(this.med.chercherDocument("tuc").estEmpruntable(), true);


	    }

	    @Test
	    public void listerDocuments() throws Exception {
	        this.med.listerDocuments();
	        String test = "";
	        for (int i =0; i< this.med.getDocumentsSize(); i++){
	            test = test + this.med.getDocumentAt(i).toString() + "\n";
	        }
	        assertEquals(this.outContent.toString(), test);

	    }

	    @Test
	    public void getDocumentAt() throws Exception {
	        assertEquals(med.getDocumentAt(0).getCode(), "tuc");
	    }

	    @Ignore
	    public void getDocumentsSize() throws Exception {
	    	assertEquals(4,med.getDocumentsSize());

	    }
	    @Test
	    public void emprunter() throws Exception {
	    	med.restituer("Camilleri", "Jeremy", "abc");;
	        med.emprunter("Camilleri","Jeremy","tuc");
	        assertEquals(this.med.chercherDocument("tuc").estEmprunte(), true);
	    }

	    @Test(expected = OperationImpossible.class)
	    public void emprunterNoDoc() throws Exception {
	        this.med.emprunter("Camilleri","Jeremy","ezgiuhorij");
	    }
	    
	    @Test(expected = OperationImpossible.class)
	    public void emprunterAlreadyEmprunte() throws Exception {
	        this.med.emprunter("Camilleri","Jeremy","abc");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void emprunterNoClient() throws Exception {
	        this.med.emprunter("BI","Eezoi","abc");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void emprunterNePeutEmprunter() throws Exception {
	    	med.getClientAt(2).marquer();
	        this.med.emprunter("Flo","Anne","abc");
	    }

	    @Test
	    public void restituer() throws Exception {
	        med.restituer("Camilleri","Jeremy","abc");
	        assertEquals(false, this.med.chercherDocument("abc").estEmprunte());
	    }

	    @Test(expected = OperationImpossible.class)
	    public void restituerPasClient() throws Exception{
	        this.med.restituer("Frjigp","zegz","abc");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void restituerPasDoc() throws Exception{
	        this.med.restituer("Camilleri","Jeremy","aegzre");
	    }

	    @Test(expected = OperationImpossible.class)
	    public void restituerPasEmprunte() throws Exception{
	        this.med.restituer("Jude","hey","abc");
	    }

	    @Test
	    public void listerFicheEmprunts() throws Exception {
	        this.med.listerFicheEmprunts();
	        String test = "";
	        for (int i =0; i< this.med.getFicheEmpruntsSize(); i++){
	            test = test + Integer.toString(i) + ": "+ this.med.getFicheEmpruntAt(i).toString() + "\n";
	        }
	        assertEquals(test, this.outContent.toString());
	    }

	    @Test
	    public void getFicheEmpruntAt() throws Exception {
	        assertEquals("abc", this.med.getFicheEmpruntAt(0).getDocument().getCode().toString());
	    }

	    @Test
	    public void getFicheEmpruntsSize() throws Exception {
	    	assertEquals(2, med.getFicheEmpruntsSize()); 
	    }

	    @Test
	    public void inscrire() throws Exception {
	        med.inscrire("Sabatier","Robin","Lyon","Voisin");
	        assertEquals("Sabatier", med.chercherClient("Sabatier","Robin").getNom());
	    }

	    @Test(expected = OperationImpossible.class)
	    public void inscrireDejaClient() throws Exception{
	    	med.inscrire("Camilleri", "Jeremy", "adresse", "Voisin", 123);
	    }


	    @Test(expected = OperationImpossible.class)
	    public void resilierEmprunEnCours() throws Exception {
	        this.med.resilier("Camilleri","Jeremy");
	    }

	    @Test
	    public void resilier() throws Exception{
	        this.med.resilier("Jude","Hey");
	        assertNull(this.med.chercherClient("Jude","Hey"));
	       
	    }

	    @Test(expected = OperationImpossible.class)
	    public void resilierPasClient() throws Exception{
	        this.med.resilier("ZPO","ezg");
	    }

	    @Test
	    public void modifierClient() throws Exception {
	        med.modifierClient(med.chercherClient("Flo","Anne"),"Anne","Florence","paris","Membre",1);
	        assertEquals("Florence", this.med.chercherClient("Anne","Florence").getPrenom());
	        assertNull(med.chercherClient("Flo","Anne"));
	    }
	   
	    @Test(expected = OperationImpossible.class)
	    public void modifierClientPasDeCat() throws Exception {
	        Client client = this.med.chercherClient("Flo","Anne");
	        this.med.modifierClient(client, "Hey","Bro","Paris","ezgeg",0);
	    }

	    @Test(expected = OperationImpossible.class)
	    public void changerCategoriePasClient() throws Exception {
	        this.med.changerCategorie("aezg","ezze","Membre",0);
	    }

	    @Test(expected = OperationImpossible.class)
	    public void changerCategoriePasCat() throws Exception {
	        this.med.changerCategorie("Camilleri ","Jeremy","vzdoibz",0);
	    }
	    
	    @Test
	    public void changerCategorie() throws Exception {
	        this.med.changerCategorie("Camilleri","Jeremy","Membre",0);
	        assertEquals("Membre",this.med.chercherClient("Camilleri","Jeremy").getCategorie().getNom());
	    }



	    @Test(expected = OperationImpossible.class)
	    public void changerCodeReductionPasClient() throws Exception {
	        this.med.changerCodeReduction("zegez","zegze",890);
	    }

	    @Test(expected = OperationImpossible.class)
	    public void changerCodeReductionPasDeReduc() throws Exception {
	        this.med.changerCodeReduction("Flo","Anne",2800);
	    }

	    @Test
	    public void changerCodeReduction() throws Exception{
	        this.med.changerCodeReduction("Camilleri","Jeremy", 20);
	        assertEquals(20,this.med.chercherClient("Camilleri","Jeremy").getReduc());
	    }

	    @Test
	    public void chercherClient() throws Exception {
	        assertEquals(client1.getPrenom(), this.med.chercherClient("Camilleri","Jeremy").getPrenom());
	    }

	    @Test
	    public void listerClients() throws Exception {
	        String test = "Mediatheque " + this.med.getNom() + "  listage des clients au "
	                + Datutil.dateToString(Datutil.dateDuJour()) + "\n";
	        for (int i =0; i<this.med.getClientsSize(); i++){
	            test = test + this.med.getClientAt(i).toString() + "\n";
	        }
	        this.med.listerClients();
	        assertEquals(test, this.outContent.toString());
	       

	    }

	    @Test
	    public void existeClient() throws Exception {
	        assertTrue(med.existeClient(cat1));
	        assertTrue(med.existeClient(cat2));

	    }

	    @Test
	    public void getClientAt() throws Exception {
	    	assertEquals("Jeremy",med.getClientAt(0).getPrenom());
	    }

	    @Test
	    public void getClientsSize() throws Exception {
	    	assertEquals(3,med.getClientsSize());
	    }

	    @Test
	    public void findClient() throws Exception {
	    	assertEquals(client1, med.findClient("Camilleri", "Jeremy"));	    }

	    @Test
	    public void afficherStatistiques() throws Exception {
	    	med.afficherStatistiques();
	    }



	    @Test
	    public void initFromFile() throws Exception {
	        Mediatheque test = new Mediatheque(med.getNom());
	        med.saveToFile();
	        test.initFromFile();
	        File file = new File(test.getNom() + ".data");
	        file.delete();
	        assertEquals(med.getCategorieAt(0),test.getCategorieAt(0));

	    }

	    @Test
	    public void saveToFile() throws Exception {
	    	Mediatheque test = new Mediatheque(med.getNom());
	        med.saveToFile();
	        test.initFromFile();
	        File file = new File(test.getNom() + ".data");
	        file.delete();
	        assertEquals(med.getCategorieAt(0),test.getCategorieAt(0));
	    }
	
}
