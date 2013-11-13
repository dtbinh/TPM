package edu.turtlekit2.warbot.controller;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Classe servant a l'analyse du document XML definit par l'utilisateur.
 * Elle contient les differentes methodes permettant de retourner les
 * differentes informations contenues dans l'XML. 
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public class Analyseur {
    
    Document                        _document;
    Element                         _racine;
    
    /**
     * Methode permettant d'ouvrir l'XML et d'obtenir l'element racine.
     */
    public void Ouverture(){
        SAXBuilder sxb = new SAXBuilder();
        try{
            _document = sxb.build(new File("tmp.xml"));
        }catch (Exception e){

        }
        _racine = _document.getRootElement();
    }
    
    /**
     * Methode permettant de recuperer le nom de l'equipe.
     * 
     * @return {@code String} - le nom de l'equipe definit par l'utilisateur
     */
    public String getTeamName(){
        String retour = "";
        
        retour = _racine.getAttributeValue("team");
        
        return retour;
    }
    
    /**
     * Methode permettant de recuperer la couleur de l'equipe definit par
     * l'utilisateur.
     * 
     * @return {@code String} - la couleur de l'equipe
     */
    public String getTeamColor(){
		String retour = "";
        try{
        	retour = _racine.getAttributeValue("color");
        }catch (Exception e){
        	retour = "000000";
        }
        
        return retour;
    }
    
    /**
     * Methode permettant de recuperer le nom de l'icone definit par 
     * l'utilisateur.
     * 
     * @return {@code String} - le nom de l'icone 
     */
    public String getIconeName(){
        String retour = "";
        
        retour = _racine.getChild("icon").getAttributeValue("name");
        
        return retour;
    }
    
    /**
     * Methode permettant de recuperer le nom de la musique de victoire
     * definit par l'utilisateur.
     * 
     * @return {@code String} - le nom de la musique de victoire
     */
    public String getSoundName(){
        String retour = "";
        try{
        	retour = _racine.getChild("sound").getAttributeValue("name");
        }catch(Exception e){
        	retour = "null";
        }
        return retour;
    }
    
    /**
     * Methode permettant de recuperer sous forme d'une HashMap les noms
     * des classes definis par l'utilisateur (constituant la valeur). La
     * cle est represente par le type d'agent sous forme de chaine de 
     * caractere. 
     * 
     * @return {@code HashMap<String, String>} - une HashMap avec comme cle
     * le type d'agent et comme valeur le nom de la classe correspondante.
     */
    @SuppressWarnings("unchecked")
	public HashMap<String, String> getClasses(){
        HashMap<String, String> retour = new HashMap<String, String>();
        
        List<Element> tmps = _racine.getChild("waragents").getChildren();
        
        for(Element current : tmps){
            retour.put(current.getAttributeValue("name"), current.getName());
        }
        
        return retour;
    }
}
