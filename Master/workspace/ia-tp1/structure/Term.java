package structure;

public class Term
{
	private String label;//le nom du terme (par exemple : x, 'toto')
	private boolean constant;//vrai si le terme courant est une constante, faux sinon (c'est une variable)
		
	/**
	 * Constructeur de la classe Terme
	 * @param n le label du terme
	 * @param c boolean qui indique si le terme est une constante ou pas (et alors c'est une variable)
	 */
	public Term(String n, boolean c)
	{
		label = n;
		constant = c;	}
		
	/**
	 * Indique si le terme est une constante
	 * @return vrai si le terme est une constante, faux sinon
	 */
	public boolean isConstant()
	{
		return constant;
	}
	
	/**
	 * Indique si le terme est une variable
	 * @return vrai si le terme est une variable, faux sinon
	 */
	public boolean isVariable()
	{
		return !constant;
	}
	

	/**
	 * Accesseur en lecture
	 * @return le label du terme
	 */
	public String getLabel()
	{
		return label;
	}

	
	/**
	 * Teste l'egalite du terme 't' et du terme courant (constante, label)
	 * @param t le terme a tester
	 * @return vrai si 't' et le terme courant sont egaux, faux sinon
	 */
	public boolean equalsT(Term t)
	{
		return(constant==t.constant && label.equals(t.label));
	}

	/**
	 * Compare le label du terme 't' et du terme courant
	 * @param t le terme a tester
	 * @return entier negatif, nul ou positif selon que le label du terme courant est avant, identique,apres le terme t dans l'ordre lexicographique. Attention on ne s'occupe pas de savoir si les termes sont des variables ou des constantes
	 */
	public int compareTo(Term t)
	{
		return(label.compareTo(t.label));
	}
	
	/**
	 * Retourne la chaine de caracteres de ce terme
	 * @return la chaine decrivant le terme 
	 */
	public String toString()
	{
		if (constant) return "'"+label+"'";
		else return label;
	}
	
}
