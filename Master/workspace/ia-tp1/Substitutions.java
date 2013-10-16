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
		genererRec(s, 0, 0);
		System.out.println(listSubs);
	}
	
	public void genererRec(Substitution s, int e1, int e2)
	{
		//faire une copie de s
		if(s.getListCT().size() == E1.size())
			listSubs.add(s);
		else
		{
			Substitution s2 = new Substitution(s);
			Substitution s3 = new Substitution(s);
			CoupleTerms ct = new CoupleTerms(E1.get(e1), E2.get(e2));
			s2.getListCT().add(ct);
			
			genererRec(s2, e1+1, e2);
			genererRec(s3, e1, e2+1);
		}
	}
	
	
	public String toString()
	{
		String s = "";
		for(Substitution sb : listSubs)
		{
			s += sb.toString() + "\n";
		}
		return s;
	}
}
