package edu.turtlekit2.warbot.waritems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.warbot.SingletonAffichage;
import edu.turtlekit2.warbot.WarDynamicAgentsAbstract;

/**
 * Classe definissant les comportements possibles pour l'agent WarRocket.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarRocket extends WarDynamicAgentsAbstract {
	
	public static final int 				COST = 200;
	public static final int					DAMAGE = 500;
	
	private int								_autonomy = 0;
	private int								_angle = 0;

	public WarRocket(int angle, String team) {
		super("action");
		_radius = 10;
		_angle = angle;
		_team = team;
		_autonomy = 20;
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Methode permettant d'initialiser l'agent.
     */
	@SuppressWarnings("deprecation")
	public void setup(){
		playRole("WarRocket");
    	
        setHeading(_angle);
        setColor(Color.black);

        joinGroup(getTeam());
        requestRole(getTeam(), "WarRocket", null);
    }

	@Override
	public String action() {
		
		if(_autonomy <= 0){
			SingletonAffichage.getInstance(mySelf()).releaseRefresh();
			die();
		}
		
		List<WarDynamicAgentsAbstract> target = getTarget();
		
		if(target.size() > 0){
			for(int i = 0 ; i < target.size() ; i++){
				target.get(i).damage(DAMAGE);
			}
			SingletonAffichage.getInstance(mySelf()).releaseRefresh();
			die();
		}else{
			if(!isBlocked()){
				fd(3);
			}else{
				SingletonAffichage.getInstance(mySelf()).releaseRefresh();
				die();
			}
			_autonomy--;
		}
		
		return "action";
	}

	@Override
	public String getType() {
		return "WarRocket";
	}
	
	/**
	 * Methode renvoyant la liste de tous les agents dynamics.
	 * 
	 * @return {@code List<WarDynamicAgentsAbstract>} - la liste de tous les agents dynamics
	 */
	public List<WarDynamicAgentsAbstract> getTarget(){
		List<WarDynamicAgentsAbstract> retour = new ArrayList<WarDynamicAgentsAbstract>();

		for(int i = 0 ; i < _listRole.size() ; ++i){
			List<Turtle> tmp = getTurtlesListWithRole(_listRole.get(i));
			
			for(int k = 0 ; k < tmp.size() ; k++){
				if(tmp.get(k) instanceof WarDynamicAgentsAbstract){
					WarDynamicAgentsAbstract t = (WarDynamicAgentsAbstract) tmp.get(k);
					if(getDistance(xcor(), t.xcor(), ycor(), t.ycor()) < _radius && !t.getTeam().equals(_team)){
						retour.add(t);
					}
				}
			}
		}
		
		return retour;
	}

	/**
	 * Fonction retournant la distance entre lui meme et un autre agent.
	 * 
	 * @param x1 Receveur
	 * @param x2 Envoyeur
	 * @param y1 Receveur
	 * @param y2 Envoyeur
	 * 
	 * @return {@code int} la distance entre lui meme et un autre agent
	 */	
	private int getDistance(int x1, int x2, int y1, int y2){
		int distance = 0;

		int part1 = (x2 - x1) * (x2 - x1);
		int part2 = (y2 - y1) * (y2 - y1);
		distance = (int) Math.sqrt(part1 + part2);

		return distance;
	}

	@Override
	public boolean fullBag() {
		return false;
	}
}
