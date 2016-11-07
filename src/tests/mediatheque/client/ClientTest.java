package tests.mediatheque.client;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import mediatheque.FicheEmprunt;
import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.Mediatheque;
import mediatheque.OperationImpossible;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.Audio;
import mediatheque.document.Document;
import mediatheque.document.Empruntable;
import mediatheque.document.Livre;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import util.Datutil;

public class ClientTest {
	
	 private static final long serialVersionUID = 2L;
     /**
      * Nom du client, format libre.
      */
     private String nom;

     /**
      * Prenom du client, format libre.
      */
     private String prenom;

     /**
      * Adresse du client, format libre.
      */
     private String adresse;

     /**
      * Nombre de documents empruntes par le client
      */
     private int nbEmpruntsEnCours = 0;

     /**
      * Nombre de documents non restitues dans les delais par le client
      */
     private int nbEmpruntsDepasses = 0;

     /**
      * Statistique sur le nombre d'emprunts effectues par le client
      */
     private int nbEmpruntsEffectues = 0;

     /**
      * Type de client
      */
     private CategorieClient catClient = null;

     /**
      * Nombre total d'emprunts de tous les clients
      */
     private static int nbEmpruntsTotal = 0;
     /**
      * Attributs pour les abonnes ces attributs peuvent etre utilises par tous les clients
      */
     private Date dateRenouvellement;
     private Date dateInscription;

     /**
      * Code de reduction
      */
     private int codeReduction = 0;
     /**
      * Date de l'inscription : la verification des droits
      * a la reduction est annuelle.
      */
     private Vector<FicheEmprunt> lesEmprunts;
     
     private Client client1;
     private Client client2;
     
     private FicheEmprunt emprunt;
     private CategorieClient cClient;
     
     private Mediatheque m;
     private Livre l;
     private Localisation loc;
     private Genre genre;
     

	@Before
	public void setUp() throws Exception {
		nom = "Hive";
		prenom = "Pierre";
		adresse = "Quai de grenelle";
		catClient = new CategorieClient("voisin");
		
		m = new Mediatheque("MyMed");
		loc = new Localisation("Salle 1", "A3");
		genre = new Genre("BD");
		l = new Livre("abc", loc, "OuiOui", "Serge", "1997", genre, 40);
		
		cClient = new CategorieClient("voisin",10,10.0,2.0,3.0, false);
		
		client1 = new Client(nom,prenom,adresse,catClient);
		client1.setCategorie(cClient);
		client2 = new Client("Cameron", "James");
		
		l.metEmpruntable();
		//emprunt = new FicheEmprunt(m, client1, l);
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
    public void getNom() throws Exception {
        assertEquals(nom, client1.getNom());
        assertEquals("Cameron", client2.getNom());
    }

    @Test
    public void getPrenom() throws Exception {
    	assertEquals(prenom, client1.getPrenom());
        assertEquals("James", client2.getPrenom());
    }

    @Test
    public void getAdresse() throws Exception {
        assertEquals(adresse, client1.getAdresse());
    }

    @Test
    public void getNbEmpruntsEnCours() throws Exception {
        assertEquals(0, client1.getNbEmpruntsEnCours());
    }

    @Test
    public void getNbEmpruntsEffectues() throws Exception {
        assertEquals(0, client1.getNbEmpruntsEffectues());
    }

    @Test
    public void getNbEmpruntsEnRetardFirst() throws Exception {
        assertEquals(0, client1.getNbEmpruntsEnRetard());
    }
    
    @Test
    public void getNbEmpruntsEnRetardSecond() throws Exception {
        client1.emprunter();
        client1.marquer();
        assertEquals(1, client1.getNbEmpruntsEnRetard());
    }

    @Test
    public void getCoefTarif() throws Exception {
        assertEquals(3.00, client1.getCoefTarif(), 0);
    }

    @Test
    public void getCoefDuree() throws Exception {
        assertEquals(2.00, client1.getCoefDuree(), 0);
    }

    @Ignore("Pas à coder")
    public void equals() throws Exception {

    }

    @Ignore("Pas à coder")
    public void testHashCode() throws Exception {

    }

    @Test
    public void aDesEmpruntsEnCours() throws Exception {
        assertEquals(false, client1.aDesEmpruntsEnCours());
    }

    @Test
    public void peutEmprunterFirst() throws Exception {
        assertEquals(true, client1.peutEmprunter());
    }
    
    @Test
    public void peutEmprunterSecond() throws Exception {
        client1.emprunter();
        client1.marquer();
        assertEquals(false, client1.peutEmprunter());
    }

    @Test
    public void peutEmprunterThird() throws Exception {
        for (int i=0; i<client1.nbMaxEmprunt(); i++) {
            client1.emprunter();
        }
        assertEquals(false, client1.peutEmprunter());
    }

    @Test
    public void emprunterFirst() throws Exception {
        assertEquals(0, client1.getNbEmpruntsEnCours());
    }
    
    @Test
    public void emprunterSecond() throws Exception {
        client1.emprunter(emprunt);
        assertEquals(1, client1.getNbEmpruntsEnCours());
    }

    @Test
    public void emprunterThird() throws Exception {

        assertEquals(0, client1.getNbEmpruntsEnCours());
        client1.emprunter();
        assertEquals(1, client1.getNbEmpruntsEnCours());

        // emprunte un nombre très elevé, sensé bloquer
        for (int i=1; i<=9; i++) {
            client1.emprunter();
        }
        assertEquals(client1.nbMaxEmprunt(), client1.getNbEmpruntsEnCours());

    }

    @Test(expected = OperationImpossible.class)
    public void marquerExeption() throws Exception {
        client1.marquer();
    }

    @Test
    public void marquer() throws Exception {
    	emprunt = new FicheEmprunt(m, client1, l);
        client1.marquer();
        assertEquals(1,client1.getNbEmpruntsEnRetard());
    }

    @Test
    public void restituer1() throws Exception {
        assertEquals(0, client1.getNbEmpruntsEnCours());
        emprunt = new FicheEmprunt(m, client1, l);
        assertEquals(1, client1.getNbEmpruntsEnCours());
        client1.restituer(emprunt);
        assertEquals(0, client1.getNbEmpruntsEnCours());
    }

    @Test
    public void afficherStatistiques() throws Exception {
        client1.afficherStatistiques();
    }

    @Test
    public void afficherStatCli() throws Exception {
        client1.afficherStatCli();
    }

    @Test
    public void testToString() throws Exception {
        client1.toString();
    }

    @Test //TODO
    public void dateRetour() throws Exception {
        //assertEquals(, client.dateRetour(, 10));
    }

    @Test
    public void sommeDue() throws Exception {
        assertEquals(30.00, client1.sommeDue(10.00), 0);
    }

    @Test
    public void nbMaxEmprunt() throws Exception {
        assertEquals(cClient.getNbEmpruntMax(), client1.nbMaxEmprunt());
    }

    @Test
    public void getDateInscription() throws Exception {
        Date today = Datutil.dateDuJour();
        assertEquals(today, client1.getDateInscription());
    }

    @Test
    public void getCategorie() throws Exception {
        assertEquals(catClient, client1.getCategorie());
    }

    @Test
    public void setCategorie() throws Exception {
        CategorieClient newCat = new CategorieClient("new categorieClient", 10, 12.00, 13.00, 3.00, false);
        client1.setCategorie(newCat);
        assertEquals(newCat, client1.getCategorie());
    }

    @Test
    public void setCategorie1() throws Exception {
        CategorieClient newCat = new CategorieClient("new categorieClient", 10, 12.00, 13.00, 3.00, false);
        CategorieClient newCat2 = new CategorieClient("new categorieClient", 10, 12.00, 13.00, 3.00, true);
        client1.setCategorie(newCat);
        assertEquals(newCat2, client1.getCategorie());
    }

    @Test
    public void setReduc() throws Exception {
        client1.setReduc(3);
        assertEquals(3, client1.getReduc());
    }

    @Test
    public void setNom() throws Exception {
        client1.setNom("new name");
        assertEquals("new name", client1.getNom());
    }

    @Test
    public void setPrenom() throws Exception {
        client1.setPrenom("new name");
        assertEquals("new name", client1.getPrenom());
    }

    @Test
    public void setAddresse() throws Exception {
        client1.setAddresse("new");
        assertEquals("new", client1.getAdresse());
    }

    @Test
    public void getReduc() throws Exception {
    	cClient.modifierCodeReducActif(true);
        Client clientCode = new Client("nom", "prenom", "20 blv grenelle", cClient, 5);
        assertEquals(5, clientCode.getReduc());
    }

    @Test
    public void getReducInit() throws Exception {
        assertEquals(0, client1.getReduc());
    }

    @Ignore("méthode non public")
    public void getnbEmpruntsTotal() throws Exception {

    }

    @Ignore("méthode non public")
    public void getStat() throws Exception {

    }

}
