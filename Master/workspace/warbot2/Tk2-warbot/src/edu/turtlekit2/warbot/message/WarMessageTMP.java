package edu.turtlekit2.warbot.message;

import madkit.kernel.Message;

/**
 * Classe permettant de construire toutes les informations concernant un 
 * message. Cette classe permettra d'envoyer une demande a un autre agent.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarMessageTMP extends Message {
	private int						_XSender = 0;
	private int						_YSender = 0;
	private int						_sender = 0;
	private String					_senderTeam = "";
	private String					_senderType = "";
	private String					_message = "";
	
	private String[]				_content = null;
	
	public WarMessageTMP(int x, int y, int senderID, String teamSender, String typeSender, String message){
		_XSender = x;
		_YSender = y;
		_sender = senderID;
		_senderTeam = teamSender;
		_senderType = typeSender;
		_message = message;
	}
	
	public WarMessageTMP(int x, int y, int senderID, String teamSender, String typeSender, String message, String[] content){
		_XSender = x;
		_YSender = y;
		_sender = senderID;
		_senderTeam = teamSender;
		_senderType = typeSender;
		_message = message;
		_content = content;
	}
	
	/**
	 * Methode renvoyant la coordonnee X ou se trouve l'agent envoyeur.
	 * 
	 * @return {@code int} - la coordonnee X ou se trouve l'agent envoyeur
	 */
	public int getXSender(){
		return _XSender;
	}
	
	/**
	 * Methode renvoyant la coordonee Y ou se trouve l'agent envoyeur.
	 * 
	 * @return {@code int} - la coordonee Y ou se trouve l'agent envoyeur
	 */
	public int getYsender(){
		return _YSender;
	}
	
	/**
	 * Methode renvoyant le numero de l'agent envoyeur.
	 * 
	 * @return {@code int} - le numero de l'agent envoyeur
	 */
	public int getSenderID(){
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
	 * Methode renvoyant le type de l'agent envoyeur.
	 * 
	 * @return {@code String} - le type de l'agent envoyeur
	 */
	public String getType(){
		return _senderType;
	}
	
	/**
	 * Methode renvoyant le message envoye par l'agent.
	 * 
	 * @return {@code String} - le message envoye par l'agent
	 */
	public String getMessage(){
		return _message;
	}
	
	/**
	 * Methode renvoyant diverses informations (autre que 
	 * le message) envoye par l'agent.
	 * 
	 * @return {@code String[]} - diverses informations sous 
	 * forme d'un tableau de chaine de caractere envoye par l'agent
	 */
	public String[] getContent(){
		return _content;
	}
}