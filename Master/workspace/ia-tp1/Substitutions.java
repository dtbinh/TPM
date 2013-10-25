import java.util.ArrayList;

import structure.Term;


public class Substitutions {

	ArrayList<Term> E1; //variables
	ArrayList<Term> E2; //constantes
	ArrayList<Substitution> listSubs;
	
	
	public Substitutions(ArrayList<Term> e1, ArrayList<Term> e2)
	{
		E1 = e1;
		E2 = e2;
		this.listSubs = new ArrayList<Substitution>();;
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
		if(s.size() == E1.size())
		{
			listSubs.add(s);
		}
		else
		{			
			for(int i = 0; i < E2.size(); i++)
			{
				Substitution tmpS = new Substitution(s);
				tmpS.getListCT().add(new CoupleTerms(E1.get(tmpS.size()), E2.get(i)));
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
