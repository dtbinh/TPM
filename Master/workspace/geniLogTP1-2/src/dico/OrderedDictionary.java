package dico;

public class OrderedDictionary extends AbstractDictionary {
	
	public OrderedDictionary() {
		super();
	}

	public OrderedDictionary(Object[] key, Object[] value, int compteur) {
		super(key, value, compteur);
	}
	
	public OrderedDictionary(int taille) {
		super(taille);
	}

	@Override
	protected int indexOf(Object key) {
		int index = -1;
		for(int i = 0; i < keys.length; i++)
		{
			if(keys[i] == key)
				index = i;
		}
		return index;
	}

	@Override
	protected int newIndexOf(Object key) {
		// TODO Auto-generated method stub
		if(compteur == keys.length)
		{
			int tmpSize = compteur+1;
			Object[] tmpKeys = new Object[tmpSize];
			Object[] tmpValues = new Object[tmpSize];
			for(int i = 0; i < compteur; i++)
			{
				tmpKeys[i] = keys[i];
				tmpValues[i] = values[i];
				
			}
			keys = tmpKeys;
			values = tmpValues;
		}
		return compteur;
	}

}
