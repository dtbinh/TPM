import java.util.ArrayList;

import structure.Term;


public class Substitutions {

	ArrayList<Term> variables;
	ArrayList<Term> constantes;
	ArrayList<Substitution> substitutions;
	
	
	public Substitutions(ArrayList<Term> e1, ArrayList<Term> e2)
	{
		variables = e1;
		constantes = e2;
		this.substitutions = new ArrayList<Substitution>();
		generateAllSubstitutions();
	}



	public void generateAllSubstitutions()
	{
		Substitution s = new Substitution();
		genererRec(s);
		System.out.println("liste substitution " + substitutions);
	}
	
	public void genererRec(Substitution s)
	{
		//faire une copie de s
		if(s.size() == variables.size())
		{
			substitutions.add(s);
		}
		else
		{			
			for(int i = 0; i < constantes.size(); i++)
			{
				Substitution tmpS = new Substitution(s);
				tmpS.getListCT().add(new CoupleTerms(variables.get(tmpS.size()), constantes.get(i)));
				genererRec(tmpS);
			}
		}
	}
	
	public int size()
	{
		return substitutions.size();
	}
	
	public Substitution getSubstitution(int index)
	{
		return substitutions.get(index);
	}
	
	public ArrayList<Substitution> getSubstitutions()
	{
		return substitutions;
	}
	
	
	public String toString()
	{
		String s = "";
		for(Substitution sb : substitutions)
		{
			s += (sb.toString() + "\n");
		}
		return s;
	}
}
