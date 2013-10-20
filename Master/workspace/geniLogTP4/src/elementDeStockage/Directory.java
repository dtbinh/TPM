package elementDeStockage;

import java.util.ArrayList;

public class Directory extends ElementStockage {
	private static int basicSize = 4;
	protected ArrayList<ElementStockage> listElement;

	@Override
	public int getBasicSize() {
		return basicSize;
	}

	@Override
	public int size() {
		int res = 0;
		if(listElement.isEmpty())
			return basicSize;
		for(ElementStockage e : listElement)
		{
			if(e instanceof Directory)
			{
				res += ((Directory) e).listElement.size();
			}
			else
				res += e.size();
				
		}
		return res;
	}
	
	public void ls()
	{
		for(ElementStockage e : listElement)
			System.out.println(e.name + "\n");
	}
	
	public int nbElement()
	{
		return listElement.size();
	}
	
	public ArrayList<String> find(String name)
	{
		ArrayList<String> find = new ArrayList<String>();
		if(!listElement.isEmpty())
		{
			for(ElementStockage e : listElement)
			{
				if(! (e instanceof Directory) )
				{
					if(e.name.equals(name))
						find.add(e.absoluteAddress());
				}
			}
		}		
		return find;
	}
	
	
	public ArrayList<String> findR(String name)
	{

		ArrayList<String> find = new ArrayList<String>();
		if(!listElement.isEmpty())
		{
			find = find(name);
			for(ElementStockage e : listElement)
			{
				if(e instanceof Directory)
				{
					ArrayList<String> tmpFind = ((Directory) e).findR(name);
					for(String s : tmpFind)
						find.add(s);
				}
			}
		}
		
		return find;
	}
	
	public void add(ElementStockage e)
	{
		listElement.add(e);
	}
	
	public void remove(ElementStockage e)
	{
		listElement.remove(e);
	}
	
	public ArrayList<ElementStockage> getElements()
	{
		return listElement;
	}

}
