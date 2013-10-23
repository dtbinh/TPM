import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import command.Bidon;
import command.Jeu;


public class TestGame {
	ArrayList<Bidon> bidons;
	
	
	Jeu jeu;
	
	@Before
	public void setUp() throws Exception {
		bidons = new ArrayList<Bidon>();
		bidons.add(new Bidon(5));
		bidons.add(new Bidon(2));
		
		jeu = new Jeu(4, bidons);
		
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRemplir()
	{
		
	}

}
