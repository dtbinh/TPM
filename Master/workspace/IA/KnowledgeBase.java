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
		//toutes les constantes apparessant dans les regles
		ArrayList<Term> constantes = new ArrayList<Term>(fb.getTerms());
		
		//tous les variables apparessant dans les regles
		ArrayList<Term> variables = new ArrayList<Term>();
		
		for(int i = 0; i < rb.size(); i++)
		{
			ArrayList<Term> terms = new ArrayList<Term>(rb.getRule(i).getTerms());
			for(int j = 0; j < terms.size(); j++)
			{
				Term t = terms.get(j);
				if(t.isConstant() && !constantes.contains(t))
					constantes.add(t);
				else if(!t.isConstant() && !variables.contains(t))
				{
					variables.add(t);
				}
			}
		}
		
		System.out.println("constante \n" + constantes);
		System.out.println("variable \n" + variables);
		
		Substitutions s = new Substitutions(variables, constantes);
		
		RuleBase newRuleBase = new RuleBase();
		for(int i = 0; i < rb.size(); i++)
		{
			for(Substitution sub : s.getSubstitutions())
			{
				Rule newRule = new Rule(rb.getRule(i));
				instanciation(newRule, sub);
				newRuleBase.addRule(newRule);
			}
		}
		rb = newRuleBase;
	}
	
	public void instanciation(Rule r, Substitution s)
	{
		//remplace chaque variable de la regle r par la constante correspondant
		for(int i = 0; i < r.getTerms().size(); i++)
		{
			Term t = r.getTerms().get(i);
			if(t.isVariable())
			{
				r.set(i, s.getConstante(t));
			}
		}
		
		//a faire aussi pour l'hypothese et la conclusion
		//voir comment est construit une regle
		for(int k = 0; k < r.getHypothesis().size(); k++)
		{
			Atom a = r.getAtomHyp(k);
			for(int j = 0; j < a.getArgs().size(); j++)
			{
				Term t = a.getArgs().get(j);
				if(t.isVariable())
				{
					r.getAtomHyp(k).getArgs().set(j, s.getConstante(t));
				}
			}
		}
		
		for(int i = 0; i < r.getConclusion().getArgs().size(); i++)
		{
			Term t = r.getConclusion().getArgs().get(i);
			if(t.isVariable())
			{
				r.getConclusion().getArgs().set(i, s.getConstante(t));
			}
		}
	}
	
	public void forwardChaningOrdre1()
	{
		//on instancie les regles d'ordre 1
		instanciation();
		//on suite on applique le forward chaning sur la nouvelle base de connaissance qu'on obtient
		forwardChaning();
	}

}
