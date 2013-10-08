import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dico.OrderedDictionary;


public class TestOrderedDictionary {
	
	OrderedDictionary orderedDico;
	OrderedDictionary orderedDicoNotEmpty;

	@Before
	public void setUp() throws Exception {
		orderedDico = new OrderedDictionary();
		String[] keys = {"aigle", "imagina", "decol"};
		String[] values = {"web", "jeux video", "bdd"};
		orderedDicoNotEmpty = new OrderedDictionary(keys, values, 3);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testAddOneElementToEmptyDico()
	{
		orderedDico.put("eSport", "sport electonique");
		assertEquals(1, orderedDico.size());
		assertTrue(orderedDico.containsKey("eSport"));
		assertEquals("sport electonique", orderedDico.get("eSport"));
	}
	
	@Test
	public void testAddOneElementToNotEmptyDico()
	{
		orderedDicoNotEmpty.put("moca", "théorie");
		assertEquals(4, orderedDicoNotEmpty.size());
		assertTrue(orderedDicoNotEmpty.containsKey("aigle"));
		assertTrue(orderedDicoNotEmpty.containsKey("decol"));
		assertEquals("web", orderedDicoNotEmpty.get("aigle"));
		assertEquals("bdd", orderedDicoNotEmpty.get("decol"));
	}
}
