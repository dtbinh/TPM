import java.util.ArrayList;

import structure.Term;


public class Substitutions {

	ArrayList<Term> variables; //variables
	ArrayList<Term> constantes; //constantes
	ArrayList<Substitution> listSubs;
	
	
	public Substitutions(ArrayList<Term> e1, ArrayList<Term> e2)
	{
		variables = e1;
		constantes = e2;
		this.listSubs = new ArrayList<Substitution>();
		generateAllSubstitutions();
	}



	public void generateAllSubstitutions()
	{
		Substitution s = new Substitution();
		genererRec(s);
		System.out.println("liste substitution " + listSubs);
	}
	
	public void genererRec(Substitution s)
	{
		//faire une copie de s
		if(s.size() == variables.size())
		{
			listSubs.add(s);
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
		return listSubs.size();
	}
	
	public Substitution getSubstitution(int index)
	{
		return listSubs.get(index);
	}
	
	public ArrayList<Substitution> getSubstitutions()
	{
		return listSubs;
	}
	
	
	public String toString()
	{
		String s = "";
		for(Substitution sb : listSubs)
		{
			s += (sb.toString() + "\n");
		}
		return s;
	}
}
