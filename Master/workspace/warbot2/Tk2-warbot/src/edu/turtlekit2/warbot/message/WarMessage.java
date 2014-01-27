package edu.turtlekit2.warbot.message;

/**
 * Classe permettant de recuperer toutes les informations concernant un 
 * message. Cette classe permettra a l'utilisateur de repondre a une demande.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public class WarMessage{
	
	private int						_angle = 0;
	private int 					_distance = 0;
	private int						_sender = 0;
	private String					_senderTeam = "";
	private String					_type = "";
	private String					_message = "";
	private String[]				_content = null;
	
	public WarMessage(WarMessageTMP wm, int x, int y){
		_angle = getAngle(x, wm.getXSender(), y, wm.getYsender());
		_distance = getDistance(x, wm.getXSender(), y, wm.getYsender());
		_sender = wm.getSenderID();
		_senderTeam = wm.getSenderTeam();
		_type = wm.getType();
		_message = wm.getMessage();
		_content = wm.getContent();
	}
	
	/**
	 * Methode permettant de renvoyer l'angle de la direction de l'envoyeur du message. 
	 * 
	 * @return {@code int} - l'angle de la direction de l'envoyeur du message
	 */
	public int getAngle(){
		return _angle;
	}
	
	/**
	 * Methode permettant de renvoyer la distance entre l'envoyeur et le recepteur
	 * du message.
	 * 
	 * @return {@code int} - la distance entre l'envoyeur et le recepteur
	 */
	public int getDistance(){
		return _distance;
	}
	
	/**
	 * Methode renvoyant le numero de l'agent envoyeur du message.
	 * 
	 * @return {@code int} - le numero de l'agent envoyeur du message
	 */
	public int getSender(){
		return _sender;
	}
	
	/**
	 * Methode renvoyant l'equipe a laquelle l'agent envoyeur appartient.
	 * 
	 * @return {@code String} - l'equipe a laquelle l'agent envoyeur appartient
	 */
	public String getSenderTeam(){
		return _senderTeam;
	}
	
	/**
	 * Methode renvoyant le type de l'agent envoyeur du message.
	 * 
	 * @return {@code String} - le type de l'agent envoyeur du message
	 * @deprecated
	 */
	public String getType(){
		return _type;
	}
	/**
	 * Methode renvoyant le type de l'agent envoyeur du message.
	 * 
	 * @return {@code String} - le type de l'agent envoyeur du message
	 */
	
	public String getSenderType(){
		return _type;
	}
	
	/**
	 * Methode permettant de recuperer le message envoye.
	 * 
	 * @return {@code String} - le message envoye.
	 */
	public String getMessage(){
		return _message;
	}
	
	/**
	 * Methode permettant de recuperer le message envoye.
	 * 
	 * @return {@code String} - le message envoye.
	 */
	public String getPerformative(){
		return _message;
	}
	
	/**
	 * Methode permettant de recuperer les differentes informations 
	 * transmises.
	 * 
	 * @return {@code String[]} - les differentes informations 
	 * transmises sous forme d'un tableau de chaine de caractere
	 */
	public String[] getContent(){
		return _content;
	}
	
	/**
	 * Methode permettant de retourner pour l'affichage, l'ensemble 
	 * des informations concernant le message.
	 */
	public String toString(){
		String retour = "";
		
		retour = "--- Message : "+getType()+" "+getSender()+" - Equipe : "+getSenderTeam()+" - "+getAngle()+"° "+getDistance()+" => "+getMessage();
		
		return retour;
	}
	
	/**
	 * Fonction retournant l'angle de la direction de l'envoyeur du message.
	 * 
	 * @param x1 Receveur
	 * @param x2 Envoyeur
	 * @param y1 Receveur
	 * @param y2 Envoyeur
	 * 
	 * @return {@code int} l'angle donnant la direction d'ou a envoye le message.
	 */
	private int getAngle(double x1, double x2, double y1, double y2){
		int angle = 0;
        
		double radian = Math.atan2((y2 - y1), (x2 - x1));

        angle = (int) Math.toDegrees(radian);
        
        return angle;
	}
	
	/**
	 * Fonction retournant la distance entre le receveur et l'endroit ou a ete emis le message.
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
}