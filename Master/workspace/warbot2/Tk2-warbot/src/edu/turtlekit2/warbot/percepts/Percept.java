package edu.turtlekit2.warbot.percepts;

/**
 * Classe permettant de recuperer toutes les informations concernant un 
 * percept effectue par un agent.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public class Percept {

	private int								_angle = 0;
	private int								_distance = 0;
	private int								_id = -1;
	private String							_team = "";
	private String							_type = "";
	private int								_energy = 0;
	private double							_heading = 0.0;
	
	
	public Percept(int angle, int distance, int id, String team, String type, int energy, double heading){
		_angle = angle;
		_distance = distance;
		_id = id;
		_team = team;
		_type = new String(type);
		_heading = heading;
		_energy = energy;
	}

	/**
	 * Methode renvoyant l'angle dans lequel est l'agent percu.
	 * 
	 * @return {@code int} - l'angle dans lequel est l'agent percu
	 */
	public int getAngle() {
		return _angle;
	}

	/**
	 * Methode renvoyant la distance a laquelle l'agent percu
	 * se trouve.
	 * 
	 * @return {@code int} - la distance a laquelle l'agent percu
	 * se trouve
	 */
	public int getDistance() {
		return _distance;
	}
	
	/**
	 * Methode retournant la direction dans laquelle avance l'agent perçu a cet instant.
	 * 
	 * @return {@code double} - la direction dans laquelle avance l'agent perçu
	 */
	public double getHeading(){
		return _heading;
	}

	/**
	 * Methode renvoyant le numero de l'agent percu.
	 * 
	 * @return {@code int} - le numero de l'agent percu
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Methode renvoyant l'equipe a laquelle l'agent percu
	 * appartient.
	 * 
	 * @return {@code String} - l'equipe a laquelle l'agent percu
	 * appartient
	 */
	public String getTeam() {
		return _team;
	}

	/**
	 * Methode renvoyant le type que possede l'agent percu.
	 * 
	 * @return {@code String} - le type que possede l'agent percu
	 */
	public String getType() {
		return _type;
	}
	
	/**
	 * Methode renvoyant l'energie que possede l'agent percu.
	 * 
	 * @return {@code int} - l'energie que possede l'agent percu
	 */
	public int getEnergy(){
		return _energy;
	}
	
	/**
	 * Methode permettant de retourner pour l'affichage, l'ensemble
	 * des informations concernant les percepts.
         *
         * @return {@code String} - les infos du percept sous forme de chaine de caracteres
	 */
	public String toString(){
		return "------ Percept : "+_type+" "+_id+" : team = "+_team+" : "+_angle+"¬∞ "+_distance;
	}
}
