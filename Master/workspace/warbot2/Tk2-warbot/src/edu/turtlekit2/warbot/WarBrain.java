package edu.turtlekit2.warbot;

import java.util.List;

import edu.turtlekit2.warbot.agents.WarBase;
import edu.turtlekit2.warbot.agents.WarRocketLauncher;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;


/**
 * Classe abstraite servant au developpement des differents brains. Elle contient
 * les differentes methodes utilisables dans les brains.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public abstract class WarBrain {
	
	private WarDynamicAgentsAbstract 			_agent = null;
	
	/**
	 * Methode permettant d'indiquer l'agent correspondant au Brain.
	 * Cette action ne peut s'effectuer qu'une seule fois afin d'eviter toute tricherie des utilisateurs.
	 * 
	 * @param agent l'agent devant etre relie au brain
	 */
	public void setAgent(WarDynamicAgentsAbstract agent){
		if(_agent == null)
			_agent = agent;
	}
	
	/**
	 * Methode a definir. Elle est utilisee par les agents pour savoir les actions a faire.
	 * 
	 * @return {@code String} - l'action que doit effectuer l'agent
	 */
    public abstract String action();
    
    /*********************************************************************
	 * 																	 *
	 * 						   Gestion des messages						 *	
	 * 																	 *
	 *********************************************************************/
    
    /**
     * Methode permettant d'envoyer un message a un agent precis.
     * 
     * @param agent l'id de l'agent de destination
     * @param message le message principal
     * @param content un tableau de string pour des donnees additionnels
     */
    public void sendMessage(int agent, String message, String[] content){
    	_agent.sendMessage(agent, message, content);
    }
    
    /**
     * Methode permettant de broadcaster un message a tous les agent.
     * Mettre <b>null</b> a unite pour envoyer a tous les agent.
     * Mettre le type d'agent voulu a unite pour envoyer a un type precis d'unite.
     * 
     * @param unite le type d'unite devant recevoir le message sinon null pour tous les types
     * @param message le message principal 
     * @param content un tableau de string pour des donnees additionnels
     */
    public void broadcastMessage(String unite, String message, String[] content){
    	_agent.broadcastMessage(unite, message, content);
    }
    
    /**
     * Methode permettant de faire une reponse rapide a un message.
     * 
     * @param wm le message auquel on repond
     * @param message le message principal 
     * @param content un tableau de string pour des donnees additionnels
     */
    public void reply(WarMessage wm, String message, String[] content){
		_agent.reply(wm, message, content);
	}
    
    /**
     * Methode permettant de recuperer les messages d'un agent sous forme de liste.
     * 
     * @return {@code List<WarMessage>} - la liste des messages d'un agent
     */
    public List<WarMessage> getMessage(){
    	return _agent.getMessage();
    }
	
    
    
    /*********************************************************************
	 * 																	 *
	 * 						  Fonctions utilitaires					 	 *	
	 * 																	 *
	 *********************************************************************/
    
    /**
     * Methode permettant d'indiquer le prochain agent auquel on donnera une ressource.
     * 
     * @param agentID l'id de l'agent auquel on desire donner une ressource
     */
	public void setAgentToGive(int agentID){
		_agent._nextAgentToGiveFood = agentID;
	}
	
	/**
	 * Methode permettant de savoir si le sac de ressource est vide.
	 * 
	 * @return {@code boolean} - true si le sac est vide sinon false
	 */
	public boolean emptyBag(){
		return _agent.emptyBag();
	}
	
	/**
	 * Methode permettant de connaitre le nombre d'Žlements dans le sac
	 * 
	 * @return {@code boolean} - true si le sac est vide sinon false
	 */
	public int sizeBag(){
		return _agent.sizeBag();
	}
	
	/**
	 * Methode permettant de savoir si le sac de ressource est plein.
	 * 
	 * @return {@code boolean} - true si le sac est plein sinon false
	 */
	public boolean fullBag(){
		return _agent.fullBag();
	}
	
	/**
	 * Methode permettant de recuperer l'orientation de l'agent.
	 * 
	 * @return {@code int} - la valeur de l'angle de l'agent
	 */
	public int getHeading(){
		return (int) _agent.getHeading();
	}
	
	/**
	 * Methode permettant de savoir le nom de l'equipe de l'agent.
	 * 
	 * @return {@code String} - le nom de l'equipe de l'agent
	 */
	public String getTeam(){
		return _agent.getTeam();
	}
	
	/**
	 * Methode permettant de recuperer l'ID de l'agent.
	 * 
	 * @return {@code int} - l'id de l'agent
	 */
	public int getID(){
		return _agent.mySelf();
	}
	
	/**
	 * Methode permettant de recuperer l'energie de l'agent.
	 * 
	 * @return {@code int} - l'energie de l'agent
	 */
	public int getEnergy(){
		return _agent.getEnergy();
	}

	/**
	 * Methode permettant de recuperer les percept d'un agent. La fonction retourne une liste avec les differents percepts.
	 * 
	 * @return {@code List<Percept>} - la liste des percepts visible par l'agent
	 */
	public List<Percept> getPercepts() {
		return _agent.getPercept();
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 						  Fonctions deplacements				 	 *
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant d'orienter l'agent dans une direction aleatoire.
	 */
	public void setRandomHeading(){
		_agent.randomHeading();
	}
	
	/**
	 * Methode permettant d'orienter l'agent dans une certaine direction.
	 * 
	 * @param angle l'angle dans lequel l'agent doit s'orienter
	 */
	public void setHeading(double angle){
		_agent.setHeading(angle);
	}
	
	/**
	 * Methode permettant de savoir si une unite est bloque dans un mur.
	 * 
	 * @return {@code boolean} - true si l'agent est bloque sinon false
	 */
	public boolean isBlocked(){
		return _agent.isBlocked();
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 						  Fonctions des missiles				 	 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant de definir l'angle de la tourelle du rocket launcher
	 * 
	 * @param angle la direction que doit prendre la tourelle
	 */
	public void setAngleTurret(int angle){
		if(_agent instanceof WarRocketLauncher)
			((WarRocketLauncher) _agent).setAngleTurret(angle);
	}
	
	/**
	 * Methode permettant de savoir si un missile est charge.
	 * 
	 * @return {@code boolean} - true si un missile est charge sinon false
	 */
	public boolean isReloaded(){
		if(_agent instanceof WarRocketLauncher)
			return ((WarRocketLauncher) _agent).isReloaded();
		else
			return false;
	}
	
	/**
	 * Methode permettant de savoir si un missile est en cours de chargement.
	 * 
	 * @return {@code boolean} - true si un missile est en cour de chargement sinon false
	 */
	public boolean isReloading(){
		if(_agent instanceof WarRocketLauncher)
			return ((WarRocketLauncher) _agent).isReloading();
		else
			return false;
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 						  Fonctions de la base					 	 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant d'indiquer le prochain type d'unitï¿½ a creer.
	 * 
	 * @param agent le type d'unite a creer
	 */
	public void setNextAgentCreate(String agent){
		if(_agent instanceof WarBase)
			((WarBase) _agent).setNextAgent(agent);
	}
}
