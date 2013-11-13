/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.turtlekit2.warbot.controller;

/**
 * Classe permettant de recuperer la configuration de la simulation 
 * effectuee par l'utilisateur (dans l'interface de choix des equipes).
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
public class Simulation {
    
    /**
     * For 1 vs 1 mode
     */
    public static final int                     VS = 0;
    /**
     * For tournament mode
     */
    public static final int                     TOURNAMENT = 1;
    
    private int                                 _nbExplorer = 0;
    private int                                 _nbRocketLauncher = 0;
    private int                                 _nbBase = 0;
    private int                                 _typeSimulation;
    private int									_foodAppearance = 0;
    
    public Simulation(int nbExplorer, int nbRocketLauncher, int nbBase, int typeSimulation, int foodApparance){
        _nbExplorer = nbExplorer;
        _nbRocketLauncher = nbRocketLauncher;
        _nbBase = nbBase;
        _typeSimulation = typeSimulation;
        _foodAppearance = foodApparance;
    }
    
    /**
     * Methode permettant de renvoyer le nombre d'explorers qui
     * vont combattre sur le terrain.
     * 
     * @return {@code int} - le nombre d'explorers qui vont combattre
     * sur le terrain
     */
    public int getNbExplorer(){
        return _nbExplorer;
    }
    
    /**
     * Methode permettant de renvoyer le nombre de rocket-launchers
     * qui combattront sur le terrain.
     * 
     * @return {@code int} - le nombre de rocket-launchers
     * qui combattront sur le terrain
     */
    public int getNbRocketLauncher(){
        return _nbRocketLauncher;
    }
    
    /**
     * Methode permettant de renvoyer le nombre de bases qui 
     * combattront sur le terrain.
     * 
     * @return {@code int} - le nombre de bases qui 
     * combattront sur le terrain
     */
    public int getNbBase(){
        return _nbBase;
    }
    
    /**
     * Methode permettant de renvoyer le mode choisi par 
     * l'utilisateur:
     * <ul>
     * 	<li>0 pour le mode un contre un.</li>
     * 	<li>1 pour le mode tournoi.</li>
     * </ul>
     * 
     * @return {@code int} - le numero du mode choisi
     */
    public int getMode(){
        return _typeSimulation;
    }
    
    /**
     * Methode permettant de renvoyer le pourcentage 
     * d'apparition des ressources.
     * 
     * @return {@code int} - le pourcentage d'apparition
     * des ressources
     */
    public int getFoodAppearance(){
    	return _foodAppearance;
    }
}
