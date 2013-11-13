/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.turtlekit2.warbot.controller;

import java.awt.Color;
import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

/**
 * Classe permettant de recuperer toutes les informations concernant
 * l'equipe.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public class Team {
    
    private ImageIcon                       _teamImage = null;
    private Clip							_clipVictory = null;
    private String                          _name = "";
    private Color							_teamColor = null;
    @SuppressWarnings("rawtypes")
	private HashMap<String, Class>          _listClasses = null;
    
    public Team(){
        
    }        
    
    /**
     * Methode permettant d'initialiser les informations de l'equipe.
     * 
     * @param teamImage l'image de l'equipe
     * @param name le nom de l'equipe
     * @param JARName le nom du jar de l'equipe
     * @param teamColor la couleur de l'equipe
     * @param clipVictory la musique de victoire de l'equipe
     */
    public void addInfos(ImageIcon teamImage, String name, String JARName, String teamColor, Clip clipVictory){
    	_clipVictory = clipVictory;
        _teamImage = teamImage;
        _name = name;
        _teamColor = Color.decode("0x"+teamColor);
    }
    
    /**
     * Methode permettant d'initialiser la HashMap:
     * <ul>
     * 	<li>clef : le nom de la classe</li>
     * 	<li>valeur : la classe</li>
     * </ul>
     * 
     * @param listClasses
     */
    @SuppressWarnings("rawtypes")
	public void addClasses(HashMap<String, Class> listClasses){
        _listClasses = listClasses;
    }
    
    /**
     * Methode permettant de recuperer le nom de l'equipe.
     * 
     * @return {@code String} - le nom de l'equipe
     */
    public String getName(){
        return _name;
    }
    
    /**
     * Methode permettant de recuperer la classe correspondante
     * en fonction du nom passe en parametre.
     * 
     * @param param le nom de la classe
     * @return {@code Class<?>} - la classe correspondante au nom passe
     * en parametre
     */
    public Class<?> getClasse(String param){
        return _listClasses.get(param);
    }
    
    /**
     * Methode permettant de recuperer l'image de l'equipe.
     * 
     * @return {@code ImageIcon} - l'icone de l'equipe
     */
    public ImageIcon getImage(){
        return _teamImage;
    }
    
    /**
     * Methode permettant de recuperer la musique de victoire
     * de l'equipe.
     * 
     * @return {@code Clip} - la musique de victoire de l'equipe
     */
    public Clip getClip(){
    	return _clipVictory;
    }
    
    /**
     * Methode permettant de recuperer la HashMap associee a
     * l'equipe.
     * 
     * @return {@code HashMap<String, Class>} - la HashMap associee a
     * l'equipe. 
     */
    @SuppressWarnings("rawtypes")
	public HashMap<String, Class> getClasses(){
    	return _listClasses;
    }
    
    /**
     * Methode permettant de recuperer la couleur de l'equipe.
     * 
     * @return {@code Color} - la couleur de l'equipe
     */
    public Color getTeamColor(){
    	return _teamColor;
    }
}
