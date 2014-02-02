package edu.turtlekit2.warbot.agents;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.WarDynamicAgentsAbstract;
import edu.turtlekit2.warbot.controller.PanelControl;

/**
 * Classe definissant les comportements possibles pour l'agent WarBase.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarBase extends WarDynamicAgentsAbstract {

    private WarBrain								_brain = null;
    public final static int							COST = 12000;
    public final static int							RADIUS = 80;
    public final static int							MAX_ENERGY = 12000;
    private String									_futurAgent = "";
    @SuppressWarnings("rawtypes")
	private HashMap<String, Class>					_listeTeam = null;
    
    private int										_startX = 0;
    private int										_startY = 0;

    public WarBase(String team){
        super("action");
        _radius = RADIUS;
        _energy = MAX_ENERGY;
        _team = team;
    }
    
    @SuppressWarnings("rawtypes")
	public WarBase(String team, double d, double e, WarBrain wb, HashMap<String, Class> listBrain, Color c){    	
    	super("action");
    	_teamColor = c;
    	_team = team;
    	_radius = RADIUS;
    	_energy = MAX_ENERGY;
    	MAX_HEALTH = MAX_ENERGY;
    	_startX = (int) d;
    	_startY = (int) e;
    	_brain = wb;
    	_listeTeam = listBrain;
    }
    
    /**
     * Methode permettant d'initialiser l'agent.
     */
    @SuppressWarnings("deprecation")
	public void setup(){
    	playRole("WarBase");
        
        setXY(_startX, _startY);
        //setPatchColor(Color.blue);
        //moveTo(sunX+(int) (Math.random()*10),sunY+(int) (Math.random()*10));
        setHeading(Math.random()*360);
        setColor(Color.red);
        _brain.setAgent(this);
        //SingletonMessage.getInstance().addUser(mySelf(), getTeam(), getType());
        //createGroup(false, String.valueOf(getTeam()), null, null);
        joinGroup(getTeam());
        requestRole(getTeam(), "WarBase", null);
    }

    public String getType() {
        return ("WarBase");
    }

    public String action() {
    	
    	if(_energy <= 0){
			return "dieAnimation";
		}
    	
		String retour = "";
    	
    	retour = _brain.action();
    	
    	if(retour.equals("create")){
    		try {
				create();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
    	}else if(retour.equals("give")){
    		giveFood();
    	}else if(retour.equals("eat")){
    		eatFood();
    	}
    	
    	if(PanelControl.isInfoLogViewerChecked())
    	System.out.println(getType()+" "+getNumber()+" - "+xcor()+" / "+ycor()+" : "+getTeam()+" "+getEnergy()+"  "+getHeading());
    	
    	return "action";
    }
    
    /**
     * Methode permettant a l'agent WarBase de creer l'unite
     * demandee par l'utilisateur.
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @SuppressWarnings({ "unchecked", "static-access" })
	public void create() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	
    	if(!_futurAgent.equals("")){
    		
    		WarBrain monBrain;
    		Turtle turtle;
    		
    		switch(_futurAgent){
	    		case "Explorer":
	    			monBrain = (WarBrain) _listeTeam.get("warexplorer").getConstructor().newInstance();
	    			turtle = (WarDynamicAgentsAbstract) new WarExplorer(getTeam(), _startX, _startY, monBrain, _teamColor);
	    			createTurtle(turtle);
	    			_energy -= ((WarExplorer)turtle).COST;
	    			break;
	    		case "RocketLauncher":
	    			monBrain = (WarBrain) _listeTeam.get("warrocketlauncher").getConstructor().newInstance();
	    			turtle = (WarDynamicAgentsAbstract) new WarRocketLauncher(getTeam(), _startX, _startY, monBrain, _teamColor);
	    			createTurtle(turtle);
	    			_energy -= ((WarRocketLauncher)turtle).COST;
	    			break;
    		}
    	}
    }
    
    /**
     * Fonction permettant d'indiquer � la base le futur agent qui devra etre cree.
     * 
     * @param agentName le nom de l'agent devant etre cree.
     */
    public void setNextAgent(String agentName){
    	_futurAgent = agentName;
    }
}
