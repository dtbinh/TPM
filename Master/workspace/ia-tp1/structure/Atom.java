package structure;

import java.util.ArrayList;

public class Atom
{
	private String predicate; // le predicat (ou le symbole propositionnel) de l'atome
	private ArrayList<Term> args; //la liste de termes de cet atome (vide si il s'agit d'un atome reduit a un symbole propositionnel)
		
	/**
	 * Constructeur de la classe Atome
	 * Cree un atome, avec ou sans termes
	 * @param s l'atome, soit reduit a un nom de predicat, soit de la forme
	 * predicat(liste de termes), ou les termes sont separes par des points virgules
	 */
	public Atom (String s)
	{	
   		args = new ArrayList<Term>();
   		if (s.charAt(s.length()-1)!=')') // c'est donc un atome sans termes
   		{
   			predicate = s;
   		}
   		else
   		{
   		int cpt = 0;
   	   	String nomAtome = "";//pour construire le label de l'atome
   	   	String nomTerme = "";//pour construire le terme courant
		boolean constanteTerme;//le terme courant est-il une constante ou non ?
   		
   		while(s.charAt(cpt) != ')')
   		{
   			while(s.charAt(cpt) != '(')//On recupere le label de l'atome
   			{
   				nomAtome += s.charAt(cpt);
   				cpt++;
   			}
   			predicate = nomAtome;
   			cpt++;//Pour sauter le '('
   			while(s.charAt(cpt) != ')')//On va desormais s'interesser aux termes de l'atome
   			{
				while(s.charAt(cpt) != ',' && s.charAt(cpt) != ')')//On recupere le label du terme
				{
					nomTerme += s.charAt(cpt);
					cpt++;					
				}
				if(nomTerme.charAt(0) == '\'')//On teste si le terme est une constante
				{
					constanteTerme = true;
					nomTerme = nomTerme.substring(1,nomTerme.length()-1);
                    //Si c'est une constante alors on supprime les "'"
				}
				else constanteTerme = false;
				Term t = new Term(nomTerme,constanteTerme);//On cree un nouveau terme
				args.add(t); //On ajoute le terme cree s'il n'existait pas deja
				nomTerme = "";
				if(s.charAt(cpt) == ',') cpt++; //Pour sauter le ','
   			}
 
   		}
   		}
	}
	
	/**
	 * Ajoute le terme 't' a la liste de termes de l'atome, sans autre verification
	 * @param t le terme a ajouter
	 */
	public void addTerm(Term t)
	{
		args.add(t);
	}
	
	/**
	 * retourne l'arite du predicat
	 */
	public int getArity()
	{
		return args.size();
	}
		
	/**
	 * retourne le ieme argument de l'atome
	 * @param i la position de l'argument
	 */
	public Term getArgI(int i) {
		return args.get(i);
	}
	
	
	/**
	 * modifie le ieme argument de l'atome
	 * @param i la position de l'argument
	 * @param t le nouveau terme i
	 */
	public void setArgI(int i, Term t) {
		args.set(i,t);
	}

	
	/**
	 * @return le (nom du) predicat de l'atome
	 */
	public String getPredicate()
	{
		return predicate;
	}
		
		
	/**
	 * @return vrai si l'atome est un symbole propositionnel (arite 0)
	 */
	public boolean isProp()
	{
		return args.size()==0;
	}
		
	
	/**
	 * Teste l'egalite des predicats de deux atomes avec le label et l'arite
	 * de l'atome
	 * @param r l'atome e tester par rapport e l'atome courant
	 * @return vrai si les deux atomes ont meme predicat, faux sinon
	 */
	public boolean equalsP(Atom r)
	{
		return (predicate.equals(r.predicate) && args.size()==r.args.size());
	}

	/**
	 * Teste l'egalite de deux atomes (meme label et meme liste de termes)
	 * @param r l'atome a tester par rapport a l'atome courant
	 * @return vrai si les deux atomes sont egaux, faux sinon
	 */
	public boolean equalsA(Atom r)
	{
		if (!equalsP(r)) return false;
		for (int i=0; i < args.size(); i++) 
			if (!args.get(i).equalsT(r.args.get(i))) return false;
		return true; 
	}
	
	/**
	 * Retourne la chaine de caracteres de cet atome
	 * @return la chaine decrivant l'atome (suivant l'ecriture logique habituelle)
	 */
	public String toString()
	{
		String s = predicate;
		if (args.size()>0)
		{
			s+="(";
			for(int i=0;i<args.size();i++)
			{
				s+=args.get(i);
				if(i<args.size()-1) s+=",";
			}
			s+=")";
		}
		return s;
	}
	
	// juste pour une demo de la classe
	 public static void main(String[] args)
	{
		Atom a = new Atom("mange('Loup'),x)");
		System.out.println("Atome 1 : "+a); // appel a.toString()
		Atom b = new Atom("mange(x,y)");
		System.out.println("Atome 2 : "+b); // appel b.toString()
		System.out.println("Meme predicat 1 et 2 ? "+ a.equalsP(b));
		System.out.println("Meme atome 1 et 2 ? "+ a.equalsA(b));
		b = new Atom("mange(y)");
		System.out.println("Atome 2 modifie : "+b); // appel b.toString()
		System.out.println("Meme predicat 1 et 2 ? "+ a.equalsP(b));
		System.out.println("Meme atome 1 et 2 ? "+ a.equalsA(b));
		Atom c = new Atom("animal"); // on donne juste le nom du predicat
		System.out.println("Atome 3 : "+c); // appel b.toString()
		System.out.println("L'arite de "+c+" est "+c.getArity());
		System.out.println("Est-ce un symbole propositionnel ? "+c.isProp());
		c.addTerm(new Term("x",false)); //puis on ajoute une variable
		c.addTerm(new Term("toto",true)); //et une constante
		System.out.println("Atome 3 modifie : "+c); // appel b.toString()		
		System.out.println("L'arite de "+c+" est maintenant "+c.getArity());
		System.out.println("Est-ce un symbole propositionnel ? "+c.isProp());
	
	}
}

