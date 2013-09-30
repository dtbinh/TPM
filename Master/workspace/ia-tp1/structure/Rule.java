package structure;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Rule
{
	private ArrayList<Atom> hypothesis;//l'hypothese : une liste d'atomes
	private Atom conclusion;//la conclusion : un atome
	private ArrayList<Term> terms;//l'ensemble des termes presents dans la regle classes par ordre lexicographique

	/**
	 * Constructeur
	 * @param strRule la regle, sous forme  sous forme textuelle ; cette forme est 
	 * "atome1;atome2;...atomek", ou les (k-1) premiers atomes forment l'hypothese,
	 * et le dernier forme la conclusion
	 */
	public Rule(String strRule)
	{
		hypothesis = new ArrayList<Atom>();
		terms = new ArrayList<Term>();
		
		StringTokenizer st = new StringTokenizer(strRule,";");
   		while(st.hasMoreTokens())
   		{
   			String s = st.nextToken(); // s represente un atome
   			Atom a = new Atom(s);
   			hypothesis.add(a);//ajout de a a la liste des atomes de l'hypothese (pour l'instant, on ajoute aussi celui de la conclusion)
   			for (int i = 0; i < a.getArity(); i ++)
   				a.setArgI(i,searchOrAddTerm(a.getArgI(i)));
   		}
   		// on a mis tous les atomes crees en hypothese
   		// il reste a tranferer le dernier en conclusion
		conclusion = hypothesis.get(hypothesis.size()-1);
		hypothesis.remove(hypothesis.size()-1);
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
	 * accesseur a l'hypothese de la regle
	 * @return l'hypothese de la regle
	 */
	public ArrayList<Atom> getHypothesis() {
		return hypothesis;
	}
	
	/**
	 * retourne la ieme atome de l'hypothese
	 * @param i le rang de l'atome a retourner (debut a 0)
	 * @return le ieme atome de l'hypothese
	 */
	public Atom getAtomHyp(int i) 
	{
		return hypothesis.get(i);
	}

	
	/**
	 * accesseur a la conclusion de la regle
	 * @return l'atome conclusion de la regle
	 */
	public Atom getConclusion()
	{
		return conclusion;
	}
	
	/**
	 * retourne une description de la regle
	 * @return la chaine decrivant la regle (suivant l'ecriture habituelle)
	 */
	public String toString()
	{
		String s="";
		for(int i=0;i<hypothesis.size();i++)
		{
			s+=hypothesis.get(i);	
			if(i<hypothesis.size()-1) s+=" ; ";
		}
		s+=" --> ";  
		s+=conclusion;
		return s;
	}

	
}
