import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import structure.Atom;
import structure.FactBase;
import structure.Rule;
import structure.RuleBase;


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
		
		System.out.println(line);
		bf = new FactBase(line);
		line = readerFile.readLine();
		//System.out.println(line);
		br = new RuleBase();
		while(line != null)
		{
			System.out.println(line);
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
		int n = 0;
		while(n < ATraiter.size())
		{
			System.out.println(" " + ATraiter.get(n));
			n++;
		}
	}
	
	

}
