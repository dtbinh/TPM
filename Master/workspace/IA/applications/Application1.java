package applications;
import structure.FactBase;
import structure.Rule;
import structure.RuleBase;


public class Application1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//base des faits
		FactBase bf = new FactBase("Benoit;Cloe");
		System.out.println("Etat initial de la base de faits :\n"+bf);
		
		//base des regles
		RuleBase br = new RuleBase();
		Rule R1 = new Rule("Benoit;Djamel;Emma;Felix");
		Rule R2 = new Rule("Gaelle;Djamel;Amandine");
		Rule R3 = new Rule("Cloe;Felix;Amandine");
		Rule R4 = new Rule("Benoit;Xena");
		Rule R5 = new Rule("Xena;Amandine;Habiba");
		Rule R6 = new Rule("Cloe;Djamel");
		Rule R7 = new Rule("Xena;Cloe;Amandine");
		Rule R8 = new Rule("Xena;Benoit;Djamel");
		
		br.addRule(R1);
		br.addRule(R2);
		br.addRule(R3);
		br.addRule(R4);
		br.addRule(R5);
		br.addRule(R6);
		br.addRule(R7);
		br.addRule(R8);
		
		System.out.println("Base de regles :\n"+br);
		

	}

}
