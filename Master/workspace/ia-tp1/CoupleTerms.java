import structure.Term;


public class CoupleTerms {
	private Term constant;
	private Term variable;
		
	public CoupleTerms(Term term1, Term term2) {
		this.constant = term1;
		this.variable = term2;
	}
	
	public CoupleTerms(CoupleTerms c)
	{
		this.constant = c.getConstant();
		this.variable = c.getVariable();
	}
	
	public Term getConstant() {
		return constant;
	}
	public void setConstant(Term term1) {
		this.constant = term1;
	}
	public Term getVariable() {
		return variable;
	}
	public void setVariable(Term term2) {
		this.variable = term2;
	}
	
	public String toString()
	{
		return "("+constant+","+variable+")";
	}
}
