package regleOrdre1;

import java.util.ArrayList;

import structure.Atom;
import structure.Term;

public class Homomorphisme {

	private ArrayList<Atom> _a1;
	private ArrayList<Atom> _a2;
	private ArrayList<Homomorphisme> _homomorphismes;
	
	public Homomorphisme(ArrayList<Atom> a1, ArrayList<Atom> a2)
	{
		_a1 = a1;
		_a2 = a2;
		_homomorphismes = new ArrayList<Homomorphisme>();
	}
	
	public ArrayList<Atom> getAtomsA1()
	{
		return _a1;
	}
	
	public ArrayList<Atom> getAtomsA2()
	{
		return _a2;
	}
	
	public Atom getAtomA1(int index)
	{
		return _a1.get(index);
	}
	
	public Atom getAtomA2(int index)
	{
		return _a2.get(index);
	}
	
	public ArrayList<Homomorphisme> getHomomorphisme()
	{
		return _homomorphismes;
	}
	
	public ArrayList<String> getVariables()
	{
		ArrayList<String> variableList = new ArrayList<String>();
		for(Atom a : _a1)
		{
			ArrayList<Term> terms = a.getArgs();
			for(Term t : terms)
			{
				if(!variableList.contains(t.getLabel()))
						variableList.add(t.getLabel());
			}
		}
		return variableList;
	}
	
	public boolean hasHomomorphisme(ArrayList<Homomorphisme> solution)
	{
		if(solution.size() == _a1.size())
			return true;
		else
			//String x = choisirVariableNonAffectee();
		return false;
	}
	
	private String choisirVariableNonAffectee()
	{
		int tailleSolution = _homomorphismes.size();
		return "";
	}
}
