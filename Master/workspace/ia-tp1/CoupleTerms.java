import structure.Term;


public class CoupleTerms {
	private Term term1;
	private Term term2;
		
	public CoupleTerms(Term term1, Term term2) {
		this.term1 = term1;
		this.term2 = term2;
	}
	
	public Term getTerm1() {
		return term1;
	}
	public void setTerm1(Term term1) {
		this.term1 = term1;
	}
	public Term getTerm2() {
		return term2;
	}
	public void setTerm2(Term term2) {
		this.term2 = term2;
	}
}
