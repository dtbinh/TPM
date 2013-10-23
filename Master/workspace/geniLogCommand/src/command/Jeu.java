package command;

import java.util.ArrayList;

public class Jeu {
	private ArrayList<Action> historique;
	private ArrayList<Bidon> listeBidons;
	private int objectif;
	
	public Jeu(int _objectif, ArrayList<Bidon> _listeBidons)
	{
		historique = new ArrayList<Action>();
		listeBidons = _listeBidons;
		objectif = _objectif;
	}
	
	public void addAction(Action action)
	{
		historique.add(action);
	}
	
	public void removeLastAction()
	{
		historique.remove(historique.size() - 1);
	}
	
	public boolean win()
	{
		for(Bidon b : listeBidons)
			if(b.getVolumeCourant() == objectif)
				return true;
		return false;
	}
	
	
	public String toString()
	{
		//todo
		String jeu = "Etat du jeu: ----- \n";
		return jeu;
	}
	
	
	
	
}
