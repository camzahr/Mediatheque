package tests.mediatheque;

import static org.junit.Assert.*;
import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.document.Video;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VideoTest {
	private Video vid;
	private Localisation loc;
	private Genre genre;
	

	@Before
	public void setUp() throws Exception {
		loc = new Localisation("1", "A");
		genre = new Genre("Action");
		vid = new Video("abc", loc, "Star Wars", "Georges", "1980", genre, 120, "Copyright");
	}

	 @Test
	    public void getStat() throws Exception {
	        assertEquals(0,vid.getStat());
	    }

	    @Test(expected = mediatheque.OperationImpossible.class)
	    public void emprunter() throws Exception {
	        vid.emprunter();
	    }

	    @Test
	    public void emprunter2() throws Exception{
	        vid.metEmpruntable();
	        assertTrue(vid.emprunter());
	        assertEquals(1, vid.getStat());
	    }

	    @Test
	    public void dureeEmprunt() throws Exception {
	        assertEquals(2*7, vid.dureeEmprunt());
	    }

	    @Test
	    public void tarifEmprunt() throws Exception {
	        assertEquals(1.5, vid.tarifEmprunt(), 0.0001);

	    }

	    @Test
	    public void getMentionLegale() throws Exception {
	        assertEquals("Copyright", vid.getMentionLegale());
	    }

	    @Test
	    public void getDureeFilm() throws Exception {
	        assertEquals(120, vid.getDureeFilm());
	    }


	    @Test(expected = mediatheque.OperationImpossible.class)
	    public void invariantVideo() throws Exception {
	        Localisation local = new Localisation("A","2");
	        Genre genre = new Genre("genre");
	        Video vid = new Video("code",local,"title","author","1994",genre, 0, "copyright");
	    }

}
