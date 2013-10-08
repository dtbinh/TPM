package dico;

public abstract class AbstractDictionary implements IDictionary{
	protected Object[] keys;
	protected Object[] values;
	protected int compteur;
	
	public AbstractDictionary() {
		//super();
		this.compteur = 0;
		this.keys = new Object[1];
		this.values = new Object[1];
	}
	
	public AbstractDictionary(Object[] key, Object[] value, int compteur) {
		//super();
		this.keys = key;
		this.values = value;
		this.compteur = compteur;
	}
	
	public AbstractDictionary(int taille) {
		//super();
		this.compteur = 0;
		this.keys = new Object[taille];
		this.values = new Object[taille];
	}
	
	public int size()
	{
		return compteur;
	}
	
	public Object getOneValue(int index)
	{
		return values[index];
	}
	
	protected abstract int indexOf(Object key);
	protected abstract int newIndexOf(Object key);
	
	
	@Override
	public Object get(Object key) {
		if(indexOf(key) != -1)
			return getOneValue(indexOf(key));
		else
			return null;
	}
	@Override
	public Object put(Object key, Object value) {
		//utilise newIndexOf pour preparer l'insertion
		//cette methode return l'index sur lequel on peut inserer la valeur
		if(!containsKey(key))
		{
			int index = newIndexOf(key);
			keys[index] = key;
			values[index] = value;
			compteur++;
		}
		return this;
	}
	@Override
	public boolean isEmpty() {
		return compteur == 0;
	}
	@Override
	public boolean containsKey(Object key) {
		return indexOf(key) != -1;
	}
}
