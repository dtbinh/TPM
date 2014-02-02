package edu.turtlekit2.warbot;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import edu.turtlekit2.kernel.agents.Turtle;

/**
 * Classe abstraite servant au developpement de tous les types d'agents.
 * Elle contient les methodes utiles pour tous les agents.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public abstract class WarAgentsAbstract extends Turtle{
	
	protected int								_radius = 0;
	protected List<String> 						_listRole = null;
	
	public WarAgentsAbstract(String string) {
		super(string);
		getRoleNamesInPackage();
	}
	
	/**
	 * Methode permettant de recuperer l'ID de l'agent.
	 * 
	 * @return {@code int} - l'id de l'agent
	 */
    public int getNumber(){
    	return mySelf();
    }
    
    /**
     * Methode permettant de recuperer la taille du radius
     * de l'agent (correspondant a son champ de perception).
     * @return
     */
    public int getRadius(){
		return _radius;
	}
    
    /**
     * Methode permettant de trouver le PATH de la classe
     * passee en parametre.
     * 
     * @param context classe dont on veut connaitre le PATH
     * @return {@code String} le chemin du PATH trouve
     * @throws IOException
     */
    public String findPathJar(Class<?> context) throws IOException {
		File f = null;
		try {
			f = new File(context.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (f.getCanonicalPath() + "\\edu\\turtlekit2\\warbot\\agents\\").replace('\\', '/');
	}
	
    /**
     * Methode permettant de remplir la liste de tous les roles 
     * joues par les differents agents implementes.
     */
    private void getRoleNamesInPackage (){
		String _path;
		try {
			_path = findPathJar(getClass());

			_listRole = new ArrayList<String>();

			File rep = new File(_path);
			File[] f = rep.listFiles();

			for(int i = 0 ; i < f.length ; ++i){
				try {
					_listRole.add((f[i].getCanonicalPath()).substring((f[i].getCanonicalPath()).lastIndexOf(File.separator)+1, (f[i].getCanonicalPath()).lastIndexOf(".")));
				} catch (IOException e) {
					System.out.println("rate");
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de recuperation des roles");
		}
		_listRole.add("WarFood");
		_listRole.add("WarRocket");
	}
    
    /**
     * Methode abstraite qui devra retourner dans les sous-classes
     * le type de l'agent.
     * 
     * @return {@code String} le type de l'agent
     */
	public abstract String getType();

}