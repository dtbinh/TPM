package state;

public abstract class EtatCycleSeminaire {
	private void ajouter(Adherent ad){}
	private Adherent popListeAttente(){return null;}
	private void ajoutInscrits(Adherent ad){}
	private void retirerInscrit(Adherent ad){}
	
	public void inscription(Adherent ad){}
	public void desistement(Adherent ad){}
	public void archiver(){}
	public void abandon(Adherent ad){}
	public void ouvertureReservation(){}
	public void clotureReservation(){}
	public void annuler(){}
	public void ajoutDocument(Document d){}
}
