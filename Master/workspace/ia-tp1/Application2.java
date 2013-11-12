import java.io.IOException;

import structure.Rule;


public class Application2 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		KnowledgeBase kb = new KnowledgeBase("premierOrdre.txt");
		kb.forwardChaning();
		System.out.println(kb);
		System.out.println("-------------------------------");
		System.out.println("-------------------------------");
		kb.forwardChaningOrdre1();
		System.out.println(kb);
	}
}
