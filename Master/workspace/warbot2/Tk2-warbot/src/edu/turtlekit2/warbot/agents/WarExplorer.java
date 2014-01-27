package edu.turtlekit2.warbot.agents;

import java.awt.Color;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.WarDynamicAgentsAbstract;
import edu.turtlekit2.warbot.WarViewer;
import edu.turtlekit2.warbot.controller.PanelControl;

/**
 * Classe definissant les comportements possibles pour l'agent WarExplorer.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings({ "serial", "unused" })
public class WarExplorer extends WarDynamicAgentsAbstract{

    private WarBrain								_brain = null;
    public final static int							COST = 2000;
    public final static int							RADIUS = 50;
    public final static double						SPEED = 1.2;
    public final static int							MAX_ENERGY = 6000;
    
    private int										_startX = 0;
    private int										_startY = 0;
    
    public WarExplorer(WarBrain wb){
    	super("action");
    	_radius = RADIUS;
    	_energy = MAX_ENERGY;
    	_brain = wb;
    }
    
    public WarExplorer(String team, int startX, int startY, WarBrain wb, Color c){    	
    	super("action");
    	_teamColor = c;
    	_team = team;
    	_radius = RADIUS;
    	_energy = MAX_ENERGY;
    	MAX_HEALTH = MAX_ENERGY;
    	_startX = startX;
    	_startY = startY;
    	_brain = wb;
    }
    
    /**
     * Methode permettant d'initialiser l'agent.
     */
    @SuppressWarnings("deprecation")
	public void setup(){
    	playRole("WarExplorer");
    	setXY(_startX, _startY);
        setHeading(Math.random()*360);
        setColor(new Color(0,0,128));
        _brain.setAgent(this);
        joinGroup(getTeam());
        requestRole(getTeam(), "WarExplorer", null);
    }

    public String getType() {
        return "WarExplorer";
    }

    /**
	 * Methode permettant d'orienter l'agent dans une direction aleatoire.
	 */
    public void setRandomHeading(){
        randomHeading();
    }

    public String action() {
    	
    	if(_energy <= 0){
			return "dieAnimation";
		}
    	
        String retour = "";
        
        if(PanelControl.isInfoLogViewerChecked())
        System.out.println(getType()+" "+getNumber()+" - "+xcor()+" / "+ycor()+" : "+getTeam()+" "+getEnergy()+"  "+getHeading());
        
        retour = _brain.action();
        
        
        
        if(retour.equals("move")){
        	if(!isBlocked()){
            	fd(SPEED);
            }
        }else if(retour.equals("take")){
        	takeFood();
        }else if(retour.equals("give")){
    		giveFood();
    	}else if(retour.equals("eat")){
    		if(_energy < MAX_HEALTH)
    			eatFood();
    	}

        return "action";
    }

  
    /****************************************************************************
     * 																			*
     * 							   Fonctions Static								*
     * 																			*
     ****************************************************************************
     */
}
