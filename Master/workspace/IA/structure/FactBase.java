package structure;

import java.util.*;

/**
 * @author graphikTeam
 *
 */
public class FactBase
{
	private ArrayList<Atom> atoms; // l'ensemble des atomes
	private ArrayList<Term> terms; // l'ensemble des termes apparaissant dans les atomes
	// la liste des termes est triee par ordre lexicographique
	
	/**
	 * Constructeur : cree une base de faits vide
	 */
	public FactBase()
	{
		atoms = new ArrayList<Atom>();
		terms = new ArrayList<Term>();
	}
	
	/**
	 * Constructeur : initialise avec une chaine de caracteres correspondant a des atomes
	 * @param factList : les faits, passes sous la forme "atom1;...;atomk"
	 */
	public FactBase(String factList)
	{
		atoms = new ArrayList<Atom>();
		terms = new ArrayList<Term>();
		createFactBase(factList);	
	}
	
	/**
	 * Remplit la base de faits avec le chaine de caracteres passee en parametres
	 * @param factList: les faits, passes sous la forme "atom1;...;atomk"
	 */
	private void createFactBase(String factList)
	//Prerequis : le format de la base de faits est suppose correct
   	{
   		StringTokenizer st = new StringTokenizer(factList,";");
   		while(st.hasMoreTokens())
   		{
   			String s = st.nextToken(); // s represente un atome
   			Atom a = new Atom(s);
   			atoms.add(a);// on ajoute un atome a la liste des atomes
   			for (int i = 0; i < a.getArity(); i ++)
   			{
   				Term t = searchOrAddTerm(a.getArgI(i));
   				a.setArgI(i, t);
   			}
   		}
   	}
	

	/**
	 * Ajoute un terme a la liste des termes s'il n'existe pas deja.
	 * @param t le terme a potentiellement ajouter
	 * @return un terme, soit t s'il a ete insere, soit le terme qui existait deja dans la liste des termes
	 */
	private Term searchOrAddTerm(Term t)
	//SI : dans le cas ou le terme t n'existe pas deja dans la liste des termes, on l'ajoute a la bonne place (par ordre lexicographique)
	
	{
		int[] report; //la case d'indice 0 contiendra -1 si t apparait deja ; la case d'indice 1 contiendra la place de t, ou celle ou inserer t
		
		report = searchPosTerm(t,0,terms.size()-1);//on recherche la position ou (est/devrait etre) t
		if(report[0]!= -1) terms.add(report[1],t);//Si t n'apparaissait pas auparavant, on l'ajoute a la liste des termes
		return terms.get(report[1]);//On retourne le terme, soit t s'il a ete insere, soit le terme qui existait deja dans la liste des termes
	} 
	
	/**
	 * Cherche la position ou (se trouve/inserer) le terme 't'
	 * @param t le  terme a inserer 
	 * @return un tableau de deux entiers : la premiere case contient a -1 si le terme apparait deja. la seconde case contient la place ou doit etre insere le terme ou celle ou il apparait.
	 */	
	private int[] searchPosTerm(Term t, int debut, int fin)
	//SE : recherche le nom, de facon recursive, entre les indices debut et fin de la liste des termes. debut et fin 
	//doivent obligatoirement etre des indices de la liste des termes.
	//tableauReponses : la premiere case contient a -1 si le terme apparait deja
		//				la seconde case contient la place ou doit etre insere le terme ou celle ou il apparait 
	{
	  if (debut>fin)
	  {
		  int[] report = new int[2];
		  report[0] = debut;
		  report[1] = debut;
		  return report; // et on sort
	  }
	  int milieu = (debut+fin)/2;
	  int compare = terms.get(milieu).compareTo(t);
	  if (compare==0) //Si le terme existe deja
	  {
		  int[] report = new int[2];
		  report[0] = -1;
		  report[1] = milieu;
		  return report; // et on sort 
	  }
	  if (compare > 0) return searchPosTerm(t,debut,milieu-1);
	  return searchPosTerm(t,milieu+1,fin);
	}

	/**
	 * Ajoute des atomes a la base de faits s'ils n'apparaissent pas deja
	 * @param atomList la liste d'atomes a ajouter (seuls ceux n'apparaissant pas dans la base seront ajoutes)
	 */
	public void addAtoms(ArrayList<Atom> atomList)
	{
		for(int i=0;i<atomList.size();i++)
			addAtom(atomList.get(i));
	}
	
	/**
	 * Ajoute un atome a la base de faits (meme si il y est deja) 
	 * @param a l'atome a ajouter 
	 */
	public void addAtom(Atom a)
	{
		atoms.add(a);
		for(int j=0;j<a.getArity();j++)
		{
			for (int i = 0; i < a.getArity(); i ++)
			{
				Term t = searchOrAddTerm(a.getArgI(i));
		   				a.setArgI(i, t);
			}
		}
	}

	/**
	 * retourne la liste des atomes de la base de faits
	 * @return la liste des atomes de la base de faits
	 */
	public ArrayList<Atom> getAtoms() 
	{
		return atoms;
	}	
	
	/**
	 * retourne la liste des termes de la base de faits
	 * @return la liste des termes de la base de faits
	 */
	public ArrayList<Term> getTerms()
	{
		return terms;
	}

	
	/**
	 * teste si la base est vide
	 * @return vrai si la base est vide
	 */
	public boolean isEmpty() 
	{
		return atoms.isEmpty();
	}
	
	/**
	 * retourne le nombre de faits dans la base
	 * @return le nombre de faits dans la base
	 */
	public int size()
	{
		return atoms.size();
	}

	
	
	/**
	 * Teste l'appartenance d'un atome a la base de faits
	 * @param a l'atome dont on teste l'existence
	 * @return vrai si l'atome existe deja, faux sinon
	 */
	public boolean belongsAtom(Atom a)
	{
		for(int i=0;i<atoms.size();i++)
			if(atoms.get(i).equalsA(a))
					return true;
		return false;
	}
	
	
	/**
	 * Retourne une description de la base de faits
	 * @return description de la base de faits
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String s ="";
		if (atoms.size()==0) s="La base est vide !";
		else {
			s = "La base contient "+atoms.size()+ " atomes : ";
			for(int i=0;i<atoms.size()-1;i++)
				s+=	atoms.get(i)+" ; ";	
			s+= atoms.get(atoms.size()-1)+"\n";
			if (terms.size()>0) {
				s+="\t Liste des termes : ";
				for(int i=0;i<terms.size()-1;i++)
					s+=	terms.get(i)+" ; ";	
				s+= terms.get(terms.size()-1)+"\n";
			}
		}
		return s;
	}
}
	