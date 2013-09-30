package dico;

public interface IDictionary {
	Object get(Object key);
	Object put(Object key, Object value);
	boolean isEmpty();
	boolean containsKey(Object key);
}
