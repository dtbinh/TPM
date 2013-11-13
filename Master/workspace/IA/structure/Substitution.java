package structure;
import java.util.ArrayList;



public class Substitution {
	
	private ArrayList<CoupleTerms> couplesTermes;

	public Substitution(ArrayList<CoupleTerms> listCT) {
		listCT = new ArrayList<CoupleTerms>();
		this.couplesTermes = listCT;
	}
	
	public Substitution(Substitution s)
	{
		couplesTermes = new ArrayList<CoupleTerms>();
		for(CoupleTerms c : s.couplesTermes)
			couplesTermes.add(c);
	}

	public Substitution() {
		couplesTermes = new ArrayList<CoupleTerms>();
	}

	public ArrayList<CoupleTerms> getListCT() {
		return couplesTermes;
	}

	public void setListCT(ArrayList<CoupleTerms> listCT) {
		this.couplesTermes = listCT;
	}
	
	public int size()
	{
		return couplesTermes.size();
	}
	
	public Term getConstante(Term t)
	{
		for(CoupleTerms cp : couplesTermes)
		{
			if(cp.getVariable().equalsT(t))
			{
				return cp.getConstant();
			}
		}
		return null;
	}
	
	public String toString()
	{
		String s = "";
		for(CoupleTerms ct : couplesTermes)
		{
			s += ct.toString();
		}
		return "["+s+"]";
	}
	
}
