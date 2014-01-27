package edu.turtlekit2.warbot;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe permettant le rafraichissement du terrain (et donc des agents).
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public class SingletonAffichage{
	private static SingletonAffichage				_singAff = null;
	private static int								_id = -1;
	
	/**
	 * @param id le numero d'agent qui prendra la main pour faire le 
	 * rafraichissement.
         *
	 * @return {@code SingletonAffichage} - l'instance de SingletonAffichage
	 */
	public static SingletonAffichage getInstance(int id){
		if(_singAff == null){
			_singAff = new SingletonAffichage();
		}
		
		if(_id == -1){
			_id = id;
		}
		
		return _singAff;
	}
	
	/**
	 * Change le numero d'agent qui fait le rafraichissement.
	 * 
	 * @param code le numero d'agent qui prendra la main
	 */
	public void initRefresh(int id){
		_id = id;
	}
	
	/**
	 * Rend le rafraichissement inoperant.
	 */
	public void releaseRefresh(){
		_id = -1;
	}
	
	/**
	 * Methode permettant le rafraichissement du terrain.
	 * 
	 * @param g le graphics correspondant au canevas sur lequel on dessine
	 * @param id le numero de l'agent qui rafraichira
	 * @param taille la taille
	 */
	public void refreshScreen(Graphics g, int id,int taille){
		if(_id == id){
			g.setColor(Color.white);
			g.fillRect(0, 0, WarViewer.XSIM*taille, WarViewer.YSIM*taille);
			g.setColor(Color.black);
			g.drawRect(0, 0, WarViewer.XSIM*taille+4, WarViewer.YSIM*taille+4);
		}
	}
}