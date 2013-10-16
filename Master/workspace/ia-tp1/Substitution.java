import java.util.ArrayList;


public class Substitution {
	
	private ArrayList<CoupleTerms> listCT;

	public Substitution(ArrayList<CoupleTerms> listCT) {
		this.listCT = listCT;
	}
	
	public Substitution(Substitution s)
	{
		for(CoupleTerms c : s.listCT)
			listCT.add(c);
	}

	public Substitution() {
		listCT = new ArrayList<CoupleTerms>();
	}

	public ArrayList<CoupleTerms> getListCT() {
		return listCT;
	}

	public void setListCT(ArrayList<CoupleTerms> listCT) {
		this.listCT = listCT;
	}
	
	public String toString()
	{
		String s = "";
		for(CoupleTerms ct : listCT)
		{
			s += ct.toString()+ " --- ";
		}
		return s;
	}
	
}
