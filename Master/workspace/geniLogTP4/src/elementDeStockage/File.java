package elementDeStockage;

public class File extends ElementStockage {
	private static int basicSize = 0;
	private String contenu;
	

	public File(String contenu) {
		super();
		this.contenu = contenu;
	}

	@Override
	public int getBasicSize() {
		return basicSize;
	}

	@Override
	public int size() {
		return contenu.length();
	}
	
	public void cat()
	{
		System.out.println("contenu du fichier "+name+" : \n"+contenu);
	}

}
