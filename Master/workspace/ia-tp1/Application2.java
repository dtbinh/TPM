import java.io.IOException;

import structure.Rule;


public class Application2 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		KnowledgeBase kb = new KnowledgeBase("fileBase.txt");
		kb.addFact("Arnaud");
		kb.addRule(new Rule("Arnaud;Benoit"));
		System.out.println(kb);
		kb.forwardChaning();
	}

}
