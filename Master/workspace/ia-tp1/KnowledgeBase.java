import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import structure.Atom;
import structure.FactBase;
import structure.Rule;
import structure.RuleBase;
import structure.Term;


public class KnowledgeBase {
	
	private FactBase bf;
	private RuleBase br;
	
	public KnowledgeBase()
	{
		bf = new FactBase();
		br = new RuleBase();
	}
	
	public KnowledgeBase(String path) throws IOException
	{
		BufferedReader readerFile = new BufferedReader(new FileReader(path));
		System.out.println("lecture du fichier" + path);
		
		String line = readerFile.readLine();
		
		//System.out.println(line);
		bf = new FactBase(line);
		line = readerFile.readLine();
		//System.out.println(line);
		br = new RuleBase();
		while(line != null)
		{
			//System.out.println(line);
			Rule r = new Rule(line);
			br.addRule(r);
			line = readerFile.readLine();
		}
		
		readerFile.close();
		System.out.println("Fin du fichier");
	}

	public FactBase getBf() {
		return bf;
	}

	public void setBf(FactBase bf) {
		this.bf = bf;
	}

	public RuleBase getBr() {
		return br;
	}

	public void setBr(RuleBase br) {
		this.br = br;
	}
	
	public String toString()
	{
		String base = "";
		
		base += bf + "\n" + br;
		
		return base;
	}
	
	public void addRule(Rule r)
	{
		br.addRule(r);
	}
	
	public void addFact(String f)
	{
		bf.addAtom(new Atom(f));
	}
	
	public void forwardChaning()
	{
		ArrayList<Atom> ATraiter = new ArrayList<Atom>(bf.getAtoms());
		int brSize = br.size();
		int[] compteur = new int[brSize];
		for(int i = 0; i < brSize; i++)
		{
			Rule r = br.getRule(i);
			compteur[i] = r.getHypothesis().size();
		}
		int x = 0;
		while(!ATraiter.isEmpty())
		{
			Atom a = ATraiter.get(x);
			ATraiter.remove(x);
			for(int i = 0; i < brSize; i++)
			{
				Rule r1 = br.getRule(i);
				for(Atom b : r1.getHypothesis())
				{
					if(a.equalsA(b))
						compteur[i]--;
				}
				if(compteur[i] == 0)
				{
					Atom c = r1.getConclusion();
					boolean isInBf = false;
					for(Atom d : bf.getAtoms())
					{
						if(c.equalsA(d))
							isInBf = true;
					}
					if(!isInBf)
					{
						ATraiter.add(c);
						bf.addAtom(c);
					}
				}
			}
			
		}
	}
	
	public void instanciation()
	{
		ArrayList<Term> constantes = new ArrayList<Term>(bf.getTerms());
		ArrayList<Term> e1 = new ArrayList<Term>();
		
		for(int i = 0; i < br.size(); i++)
		{
			ArrayList<Term> terms = new ArrayList<Term>(br.getRule(i).getTerms());
			for(int j = 0; j < terms.size(); j++)
			{
				Term t = terms.get(j);
				if(t.isConstant() && !constantes.contains(t))
					constantes.add(t);
				else if(!t.isConstant() && !e1.contains(t))
				{
					e1.add(t);
				}
			}
		}
		System.out.println("constante \n" + constantes);
		System.out.println("variable \n" + e1);
		
		Substitutions s = new Substitutions(e1, constantes);
		s.generateAllSubstitutions();
	}

}
