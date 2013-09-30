package dico;

public abstract class AbstractDictionary implements IDictionary{
	private Object[] key;
	private Object[] value;
	private int compteur;
	
	
	
	public Object[] getKey() {
		return key;
	}
	public void setKey(Object[] key) {
		this.key = key;
	}
	public Object[] getValue() {
		return value;
	}
	public void setValue(Object[] value) {
		this.value = value;
	}
	public Object getOneValue(int index)
	{
		return value[index];
	}
	
	protected abstract int indexOf(Object key);
	protected abstract int newIndexOf(Object key);
	
	public int size() { return compteur; }
	
	@Override
	public Object get(Object key) {
		if(indexOf(key) != -1)
			return getOneValue(indexOf(key));
		else
			return null;
	}
	@Override
	public Object put(Object key, Object value) {
		
		return this;
	}
	@Override
	public boolean isEmpty() {
		if(size() != 0)
			return true;
		return false;
	}
	@Override
	public boolean containsKey(Object key) {
		if(indexOf(key) != -1)
			return true;
		return false;
	}
}
