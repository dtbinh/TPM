package state;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CycleSeminaires {
	private String _titre;
	private String _resume;
	private int _nbInscits;
	private int _capacite;
	private Document _documentsAssocies;
	private ArrayList<Creneau> _creneaux;
	private ArrayList<Adherent> _listeAttente;
	private ArrayList<Adherent> _inscrits;
	private EtatCycleSeminaire _etat;
	
	
	public CycleSeminaires(String titre, String resume, int capacite)
	{
		
	}
	
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
	public void set_tire(String value){_titre = value;}
	public void set_resume(String value){_resume = value;}
	public void set_creneaux(Creneau c){}
	public void set_capacite(int c){_capacite = c;}
	
}
