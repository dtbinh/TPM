package edu.turtlekit2.warbot.agents;

import java.awt.Color;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.warbot.SingletonAffichage;
import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.WarDynamicAgentsAbstract;
import edu.turtlekit2.warbot.controller.PanelControl;
import edu.turtlekit2.warbot.waritems.WarRocket;

/**
 * Classe definissant les comportements possibles pour l'agent WarRocketLauncher.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarRocketLauncher extends WarDynamicAgentsAbstract {
	
	public static final int							TIC_TO_RELOAD = 50;
	public static final double 						SPEED=0.8;
    public final static int							RADIUS = 30;
    public final static int							MAX_ENERGY = 8000;
	
	private WarBrain								_brain = null;
	public final static int					    	COST = 3000;
	public final static int							COST_SHOOT = 200;
	private int										_turretHead = 0;
	private int 									_reloadTime = 0;
	
	private boolean									_rocketOK = false;
	private boolean									_startReload = false;
	 
	private int										_startX = 0;
    private int										_startY = 0;

	public WarRocketLauncher(WarBrain wb) {
		super("action");
		 _radius = RADIUS;
         _energy = MAX_ENERGY;
         _brain = wb;
	}

	public WarRocketLauncher(String team, int startX, int startY, WarBrain wb, Color c) {
		super("action");
		_teamColor = c;
		_radius = RADIUS;
	    _energy = MAX_ENERGY;
	    MAX_HEALTH = MAX_ENERGY;
	    _team = team;
	    _startX = startX;
	    _startY = startY;
	    _brain = wb;
	}
	
	/**
     * Methode permettant d'initialiser l'agent.
     */
	@SuppressWarnings("deprecation")
	public void setup(){
		playRole("WarRocketLauncher");
    	setHeading(Math.random()*360);
        setColor(new Color(0,128,0));
        setXY(_startX, _startY);
        _brain.setAgent(this);
        joinGroup(getTeam());
        requestRole(getTeam(), "WarRocketLauncher", null);

    }

	@Override
	public String getType() {
		return "WarRocketLauncher";
	}
	
	/**
	 * Methode permettant d'orienter l'agent dans une direction aleatoire.
	 */
    public void setRandomHeading(){
        randomHeading();
    }
    
    /**
     * Methode permettant a l'agent WarRocketLauncher de tirer.
     */
    private void buildAndShoot(){
    	if(_energy > WarRocket.COST){
	    	_energy -= COST_SHOOT;
	    	Turtle tmp;
	    	//System.out.println("1");
			tmp = new WarRocket(_turretHead, getTeam(),this);
			//System.out.println("2");
			SingletonAffichage.getInstance(mySelf()).initRefresh(createTurtle(tmp));
			//System.out.println("3");
    	}
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
        }else if (retour.equals("fire") && _rocketOK){
        	buildAndShoot();
        	_rocketOK = false;
        }else if (retour.equals("reload")){
        	if(!_startReload){
        		_startReload = true;
        		_reloadTime = TIC_TO_RELOAD;
        	}
        }else if(retour.equals("take")){
        	takeFood();
        }else if(retour.equals("give")){
    		giveFood();
    	}else if(retour.equals("eat")){
    		if(_energy < MAX_HEALTH)
    			eatFood();
    	}
        
        if(_startReload && _reloadTime > 0){
        	_reloadTime-- ;
        }else if (_startReload){
        	_startReload = false;
        	_rocketOK = true;
        }

        return "action";
    }
    
    /**
	 * Methode permettant de savoir si un missile est charge.
	 * 
	 * @return {@code boolean} - true si un missile est charge sinon false
	 */
    public boolean isReloaded(){
    	return _rocketOK;
    }
    
    /**
	 * Methode permettant de savoir si un missile est en cours de chargement.
	 * 
	 * @return {@code boolean} - true si un missile est en cour de chargement sinon false
	 */
    public boolean isReloading(){
    	return _startReload;
    }
    
    /**
     * Methode permettant a l'agent WarRocketLauncher de 
     * diriger sa tourelle en fonction d'un angle donnee
     * en degre.
     * 
     * @param angle l'angle donnee en degre
     */
    public void setAngleTurret(int angle){
    	_turretHead = angle;
    }
}
