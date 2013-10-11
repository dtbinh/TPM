import java.util.ArrayList;

import structure.Term;


public class Substitutions {

	ArrayList<Term> E1; //constantes
	ArrayList<Term> E2; //variables
	ArrayList<Substitution> listSubstitution;
	

	public Substitutions() {
		listSubstitution = new ArrayList<Substitution>();
	}

//	public Substitutions(ArrayList<Substitution> listSubstitution) {
//		this.listSubstitution = listSubstitution;
//	}
	
	public void generateAllSubstitutions()
	{
		genererRec();
	}
	
	public void genererRec()
	{
		
	}
}
