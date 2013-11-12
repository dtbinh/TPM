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
	
	private FactBase fb;
	private RuleBase rb;
	
	public KnowledgeBase()
	{
		fb = new FactBase();
		rb = new RuleBase();
	}
	
	public KnowledgeBase(String path) throws IOException
	{
		BufferedReader readerFile = new BufferedReader(new FileReader(path));
		System.out.println("lecture du fichier" + path);
		
		String line = readerFile.readLine();
		
		//System.out.println(line);
		fb = new FactBase(line);
		line = readerFile.readLine();
		//System.out.println(line);
		rb = new RuleBase();
		while(line != null)
		{
			//System.out.println(line);
			Rule r = new Rule(line);
			rb.addRule(r);
			line = readerFile.readLine();
		}
		
		readerFile.close();
		System.out.println("Fin du fichier");
	}

	public FactBase getBf() {
		return fb;
	}

	public void setBf(FactBase bf) {
		this.fb = bf;
	}

	public RuleBase getBr() {
		return rb;
	}

	public void setBr(RuleBase br) {
		this.rb = br;
	}
	
	public String toString()
	{
		String base = "";
		
		base += fb + "\n" + rb;
		
		return base;
	}
	
	public void addRule(Rule r)
	{
		rb.addRule(r);
	}
	
	public void addFact(String f)
	{
		fb.addAtom(new Atom(f));
	}
	
	public void forwardChaning()
	{
		ArrayList<Atom> ATraiter = new ArrayList<Atom>(fb.getAtoms());
		int brSize = rb.size();
		int[] compteur = new int[brSize];
		for(int i = 0; i < brSize; i++)
		{
			Rule r = rb.getRule(i);
			compteur[i] = r.getHypothesis().size();
		}
		int x = 0;
		while(!ATraiter.isEmpty())
		{
			Atom a = ATraiter.get(x);
			ATraiter.remove(x);
			for(int i = 0; i < brSize; i++)
			{
				Rule r1 = rb.getRule(i);
				for(Atom b : r1.getHypothesis())
				{
					if(a.equalsA(b))
						compteur[i]--;
				}
				if(compteur[i] == 0)
				{
					Atom c = r1.getConclusion();
					boolean isInBf = false;
					for(Atom d : fb.getAtoms())
					{
						if(c.equalsA(d))
							isInBf = true;
					}
					if(!isInBf)
					{
						ATraiter.add(c);
						fb.addAtom(c);
					}
				}
			}
			
		}
	}
	
	public void instanciation()
	{
		ArrayList<Term> constantes = new ArrayList<Term>(fb.getTerms());
		ArrayList<Term> e1 = new ArrayList<Term>();
		
		for(int i = 0; i < rb.size(); i++)
		{
			ArrayList<Term> terms = new ArrayList<Term>(rb.getRule(i).getTerms());
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
		
		RuleBase newRuleBase = new RuleBase();
		for(int i = 0; i < rb.size(); i++)
		{
			Rule r = rb.getRule(i);
			Substitutions s = new Substitutions(constantes, r.getTerms());
			s.generateAllSubstitutions();
			//System.out.println("substitutions r"+i+" "+r+" : \n"+s);
			for(Substitution sub : s.getSubstitutions())
			{
				Rule newRule = new Rule(r);
				instanciation(newRule, sub);
				newRuleBase.addRule(newRule);
				//System.out.println("substitutions r"+i+" "+newRule);
			}
		}
		rb = newRuleBase;
	}
	
	public void instanciation(Rule r, Substitution s)
	{
		System.out.println("[test] premiere affichage de la substitution: "+s);
		//term to constant
		for(int i = 0; i < r.getTerms().size(); i++)
		{
			Term t = r.getTerms().get(i);
			if(t.isVariable())
			{
				if(s.getTerm(t) == null)
					System.out.println("[error] regle: "+r+" substitution: "+s);
				r.getTerms().set(i, s.getTerm(t));
			}
		}
		//hypothese to constant
		for(Atom a : r.getHypothesis())
        {
            for(int i = 0; i < a.getArity() ; i++)
            {                                
                if(a.getArgI(i).isVariable() )
                {
                        a.setArgI(i, s.getTerm(a.getArgI(i)));
                }
            }
        }
		//conclusion to constant
        for(int i = 0 ; i < r.getConclusion().getArity(); i ++)
        {
            if( r.getConclusion().getArgI(i).isVariable())
            {
                r.getConclusion().setArgI(i, s.getTerm(r.getConclusion().getArgI(i)));
            }
        }
	}
	
	public void forwardChaningOrdre1()
	{
		instanciation();
		//forwardChaning();
	}

}
