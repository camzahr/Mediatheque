package tests.mediatheque.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import mediatheque.OperationImpossible;
import mediatheque.client.CategorieClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class CategorieClientTest {

	private static final long serialVersionUID = 2L;
    /**
     * Nom de la categorie
     */
    private String nomCat;

    /**
     * Nombre d'emprunts maximal tarif normal
     */
    private int nbEmpruntMax;

    /**
     * Cotisation annuelle 
     */
    private double cotisation;
    /**
     * Coefficient applique a la duree du document pour les abonnes
     */
    double coefDuree;
    /**
     * Coefficient appliquable au tarif du document
     */
    private double coefTarif;
    /**
     * is the reduction code used the client associated to that category
     */
    private boolean codeReducActif;
    
    private CategorieClient cClient1;
    private CategorieClient cClient2;
    
    

	@Before
	public void setUp() throws Exception {
		nomCat = "voisin";
		nbEmpruntMax = 10;
		cotisation = 10.0;
		coefDuree = 2.0;
		coefTarif = 3.0;
		codeReducActif = true;
		
		cClient1 = new CategorieClient(nomCat, nbEmpruntMax, cotisation, coefDuree, coefTarif, codeReducActif);
        cClient2 = new CategorieClient("Copain");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

   /* @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[] {"Catégorie Test", 3, 123.45, 6.7, 8.9, true},
                new Object[] {"Catégorie Test bis.", 4, 124.45, 7.7, 10.9, false},
                new Object[] {null, 1, 2, 3, 4, true}
        );
    }*/
	
	@Test
    public void getNbEmpruntMax() throws Exception {
        assertEquals(nbEmpruntMax, cClient1.getNbEmpruntMax());
        assertEquals(0, cClient2.getNbEmpruntMax());
    }

    @Test
    public void getCotisation() throws Exception {
        assertEquals(cotisation, cClient1.getCotisation(), 0);
        assertEquals(0, cClient2.getCotisation(), 0);
    }

    @Test
    public void getCoefDuree() throws Exception {
        assertEquals(coefDuree, cClient1.getCoefDuree(), 0);
        assertEquals(0, cClient2.getCoefDuree(), 0);
    }

    @Test
    public void getCoefTarif() throws Exception {
        assertEquals(coefTarif, cClient1.getCoefTarif(), 0);
        assertEquals(0, cClient2.getCoefTarif(), 0);
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("CategorieClient [nomCat=" + nomCat + ", nbEmpruntMax="
				+ nbEmpruntMax + ", cotisation=" + cotisation
				+ ", coefDuree=" + coefDuree + ", coefTarif=" + coefTarif
				+ ", codeReducActif=" + codeReducActif + "]", cClient1.toString());
        
        assertEquals("CategorieClient [nomCat=" + "Copain" + ", nbEmpruntMax="
				+ 0 + ", cotisation=" + 0
				+ ", coefDuree=" + 0 + ", coefTarif=" + 0
				+ ", codeReducActif=" + false + "]", cClient2.toString());
    }

    @Test
    public void getNom() throws Exception {
        assertEquals(nomCat, cClient1.getNom());
        assertEquals("Copain", cClient2.getNom());
    }

    @Test
    public void getCodeReducUtilise() throws Exception {
        assertEquals(codeReducActif, cClient1.getCodeReducUtilise());
        assertEquals(false, cClient2.getCodeReducUtilise());
    }

    @Test
    public void modifierNom() throws Exception {
        cClient1.modifierNom("testCat");
        assertEquals("testCat", cClient1.getNom());
    }

    @Test
    public void modifierMax() throws Exception {
        cClient1.modifierMax(8);
        assertEquals(8, cClient1.getNbEmpruntMax());
    }

    @Test
    public void modifierCotisation() throws Exception {
        cClient1.modifierCotisation(7.5);
        assertEquals(7.5, cClient1.getCotisation(), 0);
    }

    @Test
    public void modifierCoefDuree() throws Exception {
        cClient1.modifierCoefDuree(4.6);
        assertEquals(4.6, cClient1.getCoefDuree(), 0);
    }

    @Test
    public void modifierCoefTarif() throws Exception {
        cClient1.modifierCoefTarif(1.7);
        assertEquals(1.7, cClient1.getCoefTarif(), 0);
    }

    @Test
    public void modifierCodeReducActif() throws Exception {
        cClient1.modifierCodeReducActif(false);
        assertEquals(false, cClient1.getCodeReducUtilise());
    }

    

    @Test
    public void hashCodeTest() throws Exception {
    	final CategorieClient cClientEquals1 = new CategorieClient(nomCat, nbEmpruntMax, cotisation, coefDuree, coefTarif, codeReducActif);
        final CategorieClient cClientEquals2 = new CategorieClient(nomCat, nbEmpruntMax, cotisation, coefDuree, coefTarif, codeReducActif);
        final CategorieClient cClientEquals3 = new CategorieClient("Copain");
        final CategorieClient cClientEquals4 = new CategorieClient("Copain");
        
        assertTrue("2 même catégories : ", cClientEquals1.hashCode() == cClientEquals2.hashCode());
        assertTrue("2 même catégories (2) : ", cClientEquals3.hashCode() == cClientEquals4.hashCode());
       
    }

    @Test
    public void equalsTest() throws Exception {
        final CategorieClient cClientEquals1 = new CategorieClient(nomCat, nbEmpruntMax, cotisation, coefDuree, coefTarif, codeReducActif);
        final CategorieClient cClientEquals2 = new CategorieClient(nomCat, nbEmpruntMax, cotisation, coefDuree, coefTarif, codeReducActif);
        final CategorieClient cClientEquals3 = new CategorieClient("Copain");
        final CategorieClient cClientEquals4 = new CategorieClient("Copain");
        

        assertTrue("2 même catégories : ", cClientEquals1.equals(cClientEquals2));
        assertTrue("2 même catégories (2) : ", cClientEquals3.equals(cClientEquals4));
        
    }

}
