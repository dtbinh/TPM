package elementDeStockage;

public abstract class ElementStockage {
	protected String name;
	protected Dictionary parent;
	
	public ElementStockage()
	{
		name = "";
		parent = null;
	}
	
	public abstract int getBasicSize();
	public abstract int size();
	
	public String absoluteAddress() {
		if(parent == null)
			return "";
		else
		{
			return parent.absoluteAddress()+"/"+name;
		}
	}
	
}
