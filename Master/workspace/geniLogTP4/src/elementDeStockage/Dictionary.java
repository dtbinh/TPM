package elementDeStockage;

import java.util.ArrayList;

public class Dictionary extends ElementStockage {
	private static int basicSize = 4;
	protected ArrayList<ElementStockage> listElement;

	@Override
	public int getBasicSize() {
		return basicSize;
	}

	@Override
	public int size() {
		for(ElementStockage e : listElement)
		{
			
		}
		return 0;
	}

}
