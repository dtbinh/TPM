package edu.turtlekit2.warbot.waritems;

import java.awt.Color;

import edu.turtlekit2.warbot.SingletonAffichage;
import edu.turtlekit2.warbot.WarPassiveAgentsAbstract;
import edu.turtlekit2.warbot.controller.PanelControl;

/**
 * Classe definissant les comportements possibles pour l'agent WarFood.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarFood extends WarPassiveAgentsAbstract {
	
	public static final int							MAX_DISTANCE_TAKE = 5;
	public static final int							ENERGY = 200;

	public WarFood() {
		super("idle");
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Methode permettant d'initialiser l'agent.
     */
	public void setup(){
		playRole("WarFood");
		_radius = 10;
		x = (int) (Math.random()*700);
		y = (int) (Math.random()*300);
		setXY(x, y);
		setColor(new Color(102,51,0));
		//_actionAgent = new WarExplorerExec(this);
		//requestRole(getTeam(), "WarExplorer", null);
	}
	
	/**
	 * Methode appelee des le lancement de l'agent.
	 * On bouclera sur cette methode.
	 * 
	 * @return {@code String} - la prochaine action 
	 * (elle meme)
	 */
	public String idle(){
		
		if(PanelControl.isInfoLogViewerChecked())
		System.out.println(getType()+" "+getNumber()+" - "+xcor()+" / "+ycor());
		
		return "idle";
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "WarFood";
	}

	@Override
	public void die(){
		SingletonAffichage.getInstance(mySelf()).releaseRefresh();
		super.die();
	}
}
