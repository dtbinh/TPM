package elementDeStockage;

public class Link extends ElementStockage {
	private static int basicSize = 0;
	private ElementStockage reference;
	
	public Link(ElementStockage ref)
	{
		super();
		reference = ref;
	}

	@Override
	public int getBasicSize() {
		return basicSize;
	}

	@Override
	public int size() {
		if(reference == null)
			return basicSize;
		return 3;
	}
	
	public void cat()
	{
		System.out.println(name+" reference "+reference.name);
	}


}
