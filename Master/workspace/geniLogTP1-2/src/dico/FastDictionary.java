package dico;

public class FastDictionary extends AbstractDictionary {
	
	public FastDictionary() {
		super();
	}

	public FastDictionary(int taille) {
		super(taille);
	}

	public FastDictionary(Object[] key, Object[] value, int compteur) {
		super(key, value, compteur);
	}
	
	public boolean mustGrow()
	{
		return compteur/keys.length < 3/4;
	}
	
	public void grow()
	{
		int taille = (int) Math.ceil(keys.length * 3);
		Object[] tmpKeys = keys;
		Object[] tmpValues = values;
		
		keys = new Object[taille];
		values = new Object[taille];
		compteur = 0;
		
		for(int i = 0; i < tmpKeys.length; i++)
		{
			if(tmpKeys[i] != null)
			{
				put(tmpKeys[i], tmpValues[i]);
			}
		}
	}
	
	int hashKey(Object key)
	{
		int index = 0;
		if(key != null)
			index = key.hashCode();
		return (int) Math.abs(index)%keys.length;
	}

	@Override
	protected int indexOf(Object key) {
		int index = -1;
		int b = hashKey(key);
		for(int i = b; i < keys.length; i++)
		{
			if(key == keys[i])
			{
				index = i;
				break;
			}
		}
		return index;
	}

	@Override
	protected int newIndexOf(Object key) {
		// TODO Auto-generated method stub
		if(mustGrow())
			grow();
		
		int i = hashKey(key);
		while(i < keys.length && keys[i] != null)
			i++;
		
		if(i == keys.length)
		{
			grow();
			i = newIndexOf(key);
		}
		
		return i;
	}

}
