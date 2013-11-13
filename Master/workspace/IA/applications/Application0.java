package applications;
import structure.Atom;
import structure.FactBase;
import structure.Rule;
import structure.RuleBase;


public class Application0 {
	public static void main(String[] args)
	{
		// creation de la base de faits de 2 facons differentes
		FactBase bf = new FactBase("P");
		bf.addAtom(new Atom("Q"));
		System.out.println("Etat initial de la base de faits :\n"+bf);
	
		// creation de la base de regles
		RuleBase br = new RuleBase();
		br.addRule(new Rule("P;S"));
		System.out.println("Base de regles :\n"+br);
	}
}
