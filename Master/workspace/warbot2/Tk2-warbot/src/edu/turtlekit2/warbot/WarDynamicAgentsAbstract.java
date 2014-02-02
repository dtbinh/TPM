package edu.turtlekit2.warbot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import madkit.kernel.Message;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.warbot.controller.PanelControl;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.message.WarMessageTMP;
import edu.turtlekit2.warbot.percepts.Percept;
import edu.turtlekit2.warbot.waritems.WarFood;

/**
 * Classe abstraite servant au developpement des agents qui agiront 
 * sur le terrain et qui auront une influence sur le monde et les 
 * autres agents.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public abstract class WarDynamicAgentsAbstract extends WarAgentsAbstract{
	
	protected static final int						MAX_BAG_VALUE = 4;
	protected int									MAX_HEALTH;

	protected String								_team = "";
	protected int									_energy = 0;
	protected Color									_teamColor = null;
	protected int									_bag = 0;
	protected int									_nextAgentToGiveFood = 0;
	protected String								_statut = "life";
	private int										_dieCPT = 10;
	
	public WarDynamicAgentsAbstract(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	
	/*********************************************************************
	 * 																	 *
	 * 				   Fonctions comunes a tous les agents				 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant de recuperer l'energie de l'agent.
	 * 
	 * @return {@code int} - l'energie de l'agent
	 */
	public int getEnergy(){
		return _energy;
	}
	
	/**
	 * Methode permettant de savoir si le sac de ressource est vide.
	 * 
	 * @return {@code boolean} - true si le sac est vide sinon false
	 */
	public boolean emptyBag(){
		return _bag == 0;
	}
	
	/**
	 * Methode permettant de savoir si le sac de ressource est plein.
	 * 
	 * @return {@code boolean} - true si le sac est plein sinon false
	 */
	public boolean fullBag() {
		return _bag == MAX_BAG_VALUE;
	}
	
	/**
	 * Methode permettant connaitre la taille courante du sac.
	 * 
	 * @return {@code boolean} - true si le sac est plein sinon false
	 */
	public int sizeBag() {
		return _bag;
	}
	
	
	/**
	 * Methode permettant de connaitre l'energie maximale
	 * que peut avoir un agent "dynamic".
	 * 
	 * @return {@code int} - l'energie maximale pour l'agent
	 */
	public int getMaxHealth(){
		return MAX_HEALTH;
	}
	
	/**
	 * Methode permettant de savoir le nom de l'equipe de l'agent.
	 * 
	 * @return {@code String} - le nom de l'equipe de l'agent
	 */
	public String getTeam(){
		return String.valueOf(_team);
	}
	
	public List<String> getListRole(){
		return _listRole;
	}
	
	/**
	 * Methode permettant de savoir si l'agent peut prendre
	 * la nourriture ou non.
	 * 
	 * @return {@code boolean} - true s'il n'a pas atteint
	 * la capacite maximale de son sac sinon false
	 */
	public boolean getFood(){
		if(_bag < MAX_BAG_VALUE){
			_bag ++;
			return true;
		}
		return false;
	}
	
	/**
	 * Methode permettant de faire manger la nourriture
	 * a l'agent (si son sac contient au moins une ressource).
	 */
	protected void eatFood(){
		if(_bag > 0){
			removeFood();
			_energy+=WarFood.ENERGY;
		}
	}
	
	/**
	 * Mathode permettant a un agent de donner une ressource a un 
	 * autre agent.
	 */
	protected void giveFood(){
		for(int i = 0 ; i < _listRole.size() ; ++i){
			List<Turtle> tmp = getTurtlesListWithRole(_listRole.get(i));
		
			for(Turtle t : tmp){
				if(t instanceof WarDynamicAgentsAbstract && _bag > 0 && t.mySelf() == _nextAgentToGiveFood){
					if(getDistance(xcor(), t.xcor(), ycor(), t.ycor()) <= WarFood.MAX_DISTANCE_TAKE){
						((WarDynamicAgentsAbstract)t).getFood();
						removeFood();
					}
				}
			}
		}
	}
	
	/**
	 * Methode permettant de recuperer les percept d'un agent. La fonction retourne une liste avec les differents percepts.
	 * 
	 * @return {@code List<Percept>} - la liste des percepts visible par l'agent
	 */
	public List<Percept> getPercept(){
		List<Percept> retour = new ArrayList<Percept>();
		int radius = getRadius();

		for(int i = 0 ; i < _listRole.size() ; ++i){
			List<Turtle> tmp = getTurtlesListWithRole(_listRole.get(i));
			
			for(int k = 0 ; k < tmp.size() ; k++){
				if(tmp.get(k) instanceof WarDynamicAgentsAbstract){
					WarDynamicAgentsAbstract t = (WarDynamicAgentsAbstract) tmp.get(k);
					Percept p = new Percept(getAngle(xcor(), t.xcor(), ycor(), t.ycor()), getDistance(xcor(), t.xcor(), ycor(), t.ycor()), 
							t.mySelf(), t.getTeam(), t.getType(), t.getEnergy(), t.getHeading());
					if (p.getDistance()<=radius && p.getId() != mySelf()){
						
						if(PanelControl.isInfoLogViewerChecked()){
							System.out.println(p);
						}
						
						retour.add(p);
					}
				}else{
					WarPassiveAgentsAbstract t = (WarPassiveAgentsAbstract) tmp.get(k);
					Percept p = new Percept(getAngle(xcor(), t.xcor(), ycor(), t.ycor()), 
							getDistance(xcor(), t.xcor(), ycor(), t.ycor()), t.mySelf(), "", t.getType(), 0, t.getHeading());
					if (p.getDistance()<=radius && p.getId() != mySelf()){
						
						if(PanelControl.isInfoLogViewerChecked()){
							System.out.println(p);
						}
						
						retour.add(p);
					}
				}
			}
		}
		
		return retour;
	}
	
	/**
	 * Methode permettant d'enlever une ressource a un agent 
	 * (si son sac en contient au moins une).
	 */
	public void removeFood(){
		if(_bag > 0)
			_bag --;
	}
	
	/**
	 * Methode permettant d'inflige des degats a un agent.
	 * 
	 * @param damage le degat a inflige a l'agent
	 */
	public void damage(int damage){
		_energy -= damage;
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 				 Fonctions communes aux agents mobiles			 	 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant de savoir si une unite est bloque dans un mur.
	 * 
	 * @return {@code boolean} - true si l'agent est bloque sinon false
	 */
	public boolean isBlocked(){
		boolean retour = false;

		if(getHeading() >= 0 && getHeading() <= 180 && ycor() >= (WarViewer.YSIM-2)){
			retour = true;
		}else if(getHeading() >= 90 && getHeading() <= 270 && xcor() <= 2){
			retour = true;
		}else if(getHeading() >= 180 && getHeading() < 360 && ycor() <= 2){
			retour = true;
		}else if(((getHeading() >= 270 && getHeading() <= 360) || (getHeading() >= -1 && getHeading() < 90)) && xcor() >= (WarViewer.XSIM-2)){
			retour = true;
		}

		return retour;
	}
	
	/**
	 * Methode permettant de faire prendre a un agent la ressource qui 
	 * se trouve a cote de lui.
	 */
	protected void takeFood(){
		List<Turtle> tmp = getTurtlesListWithRole("WarFood");
		
		for(Turtle t : tmp){
			if(t instanceof WarFood && _bag < MAX_BAG_VALUE){
				if(getDistance(xcor(), t.xcor(), ycor(), t.ycor()) <= WarFood.MAX_DISTANCE_TAKE){
					t.die();
					_bag++;
					break;
				}
			}
		}
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 				     Fonctions specifiques a la base				 *	
	 * 																	 *
	 *********************************************************************/
	
	
	/**
	 * Methode permettant de retourner tous les agents "dynamics" se trouvant
	 * sur le terrain.
	 * 
	 * @return {@code List<WarDynamicAgentsAbstract>} - la liste de tous les agents "dynamic"
	 */
	public List<WarDynamicAgentsAbstract> getAgents(){
		List<WarDynamicAgentsAbstract> retour = new ArrayList<WarDynamicAgentsAbstract>();
		
		for(int i = 0 ; i < _listRole.size() ; ++i){
			List<Turtle> tmp = getTurtlesListWithRole(_listRole.get(i));
			
			for(int k = 0 ; k < tmp.size() ; k++){
				if(tmp.get(k) instanceof WarDynamicAgentsAbstract){
					WarDynamicAgentsAbstract t = (WarDynamicAgentsAbstract) tmp.get(k);
					retour.add(t);
				}
			}
		}
		
		return retour;
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 						  Fonctions utilitaires					 	 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant de retourner la couleur de l'equipe a 
	 * laquelle l'agent appartient.
	 * 
	 * @return {@code Color} - la couleur de l'equipe a laquelle l'agent
	 * appartient
	 */
	public Color getTeamColor(){
		return _teamColor;
	}
	
	/**
	 * Fonction retournant l'angle de la direction de l'envoyeur du message.
	 * 
	 * 
	 * @param x1 Receveur
	 * @param x2 Envoyeur
	 * @param y1 Receveur
	 * @param y2 Envoyeur
	 * 
	 * @return {@code int} l'angle donnant la direction d'ou a envoy� le message.
	 */
	private int getAngle(double x1, double x2, double y1, double y2){
		int angle = 0;

		double radian = Math.atan2((y2 - y1), (x2 - x1));

		angle = (int) Math.toDegrees(radian);

		return angle%360;
	}

	/**
	 * Fonction retournant la distance entre le recevoir et l'endroit ou a ete emis le message.
	 * 
	 * 
	 * @param x1 Receveur
	 * @param x2 Envoyeur
	 * @param y1 Receveur
	 * @param y2 Envoyeur
	 * 
	 * @return {@code int} la distance par rapport a l'endroit de l'emission du message.
	 */	
	private int getDistance(int x1, int x2, int y1, int y2){
		int distance = 0;

		int part1 = (x2 - x1) * (x2 - x1);
		int part2 = (y2 - y1) * (y2 - y1);
		distance = (int) Math.sqrt(part1 + part2);

		return distance;
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 				     Gestion de l'animation de mort 				 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode permettant de savoir dans quel etat est l'agent 
	 * (vivant ou mort).
	 * 
	 * @return {@code String} - l'etat de l'agent
	 */
	public String getStatut(){
		return _statut;
	}

        /**
         * Fonction permettant de savoir ou on en est dans l'animation de mort.
         *
         * @return {@code int} - a quel niveau de l'animation de mort est on
         */
	public int getDieCpt(){
		return _dieCPT;
	}

        /**
         * Fonction permettant de gérer l'animation de mort avant de détruire l'agent.
         *
         * @return {@code String} - la fonction suivante
         */
	public String dieAnimation(){
		_statut = "die";
		
		if(_dieCPT == 0){
			die();
		}
		
		_dieCPT--;
		
		return "dieAnimation";
	}
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 		Fonctions abstraites devant etre ecritent dans un agent		 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
	 * Methode a definir. Elle est utilisee par les agents pour savoir les actions a faire.
	 * 
	 * @return {@code String} - l'action que doit effectuer l'agent
	 */
	protected abstract String action();
	
	
	
	/*********************************************************************
	 * 																	 *
	 * 						   Gestion des messages						 *	
	 * 																	 *
	 *********************************************************************/
	
	/**
     * Methode permettant de recuperer les messages d'un agent sous forme de liste.
     * 
     * @return {@code List<WarMessage>} - la liste des messages d'un agent
     */
	public List<WarMessage> getMessage(){
		List<WarMessage> retour = new ArrayList<WarMessage>();
		
		Message m;
		while((m = nextMessage()) != null){
			if(m instanceof WarMessageTMP && ((WarMessageTMP)m).getSenderID() != mySelf()){
				WarMessage wmf = new WarMessage((WarMessageTMP)m, xcor(), ycor());
				if(PanelControl.isMessageViewerChecked()){
					System.out.println(wmf.toString());
				}
				retour.add(wmf);
			}
		}
		
		return retour;
	}
	
	 /**
     * Methode permettant d'envoyer un message a un agent precis.
     * 
     * @param agent l'id de l'agent de destination
     * @param message le message principal
     * @param content un tableau de string pour des donnees additionnels
     */
	public void sendMessage(int agent, String message, String[] content){
		List<WarDynamicAgentsAbstract> maListe = getAgents();
		for(WarDynamicAgentsAbstract currentAgent : maListe){
			if(currentAgent.mySelf() == agent && currentAgent.getTeam().equals(getTeam())){
				sendMessage(currentAgent.getAddress(), new WarMessageTMP(xcor(), ycor(), mySelf(), getTeam(), getType(), message, content));
				break;
			}
		}
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
		WarMessageTMP m = new WarMessageTMP(xcor(), ycor(), mySelf(), getTeam(), getType(), message, content);
		if (!(unite == null || unite.equals("all"))){
			broadcastMessage(getMyGroups()[0], unite, m);
		}else{
			List<String> list = getListRole();
			
			for(int i = 0 ; i < list.size() ; ++i){
				if(!list.get(i).equals("WarFood"))
					broadcastMessage(getMyGroups()[0], list.get(i), m);
			}
		}
	}
	
	/**
     * Methode permettant de faire une reponse rapide a un message.
     * 
     * @param wmf le message auquel on repond
     * @param message le message principal 
     * @param content un tableau de string pour des donnees additionnels
     */
	public void reply(WarMessage wmf, String message, String[] content){
		sendMessage(wmf.getSender(), message, content);
	}
}
