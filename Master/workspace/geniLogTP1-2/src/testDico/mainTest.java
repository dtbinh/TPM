package testDico;

import dico.OrderedDictionary;

public class mainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OrderedDictionary dico = new OrderedDictionary();
		dico.put(new String("eSport"), new String("sport electronique, pratique de jeux vidéo a haut niveau"));
		dico.put(new String("sport"), new String("effort physique"));
		//System.out.println(dico);

	}

}
