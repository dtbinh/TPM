package edu.turtlekit2.warbot.controller;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.warbot.SingletonAffichage;
import edu.turtlekit2.warbot.WarAgentsAbstract;
import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.WarDynamicAgentsAbstract;
import edu.turtlekit2.warbot.WarViewer;
import edu.turtlekit2.warbot.agents.WarBase;
import edu.turtlekit2.warbot.agents.WarExplorer;
import edu.turtlekit2.warbot.agents.WarRocketLauncher;
import edu.turtlekit2.warbot.waritems.WarFood;

/**
 * Classe servant a:
 * <ul>
 * 	<li>la generation aleatoire des ressources sur le terrain.</li>
 * 	<li>l'apparition de l'interface de choix des equipes pour l'utilisateur.</li>
 * 	<li>l'apparition de l'interface de controle de l'affichage du terrain.</li>
 * 	<li>charger et lancer des equipes sur le terrain.</li>
 * 	<li>gerer la fin de match.</li>
 * </ul>
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class Controller extends Turtle {
	
	static boolean                                  _boucle = true;
    static HashMap<String, Team>                    _listeTeam = new HashMap<String, Team>();
    static Simulation                               _simulationOption;
    private PanelControl							_panelControl = null;
    protected List<String> 							_listRole = null;
    private String									_victoryTeam = "";
    private JFrame									_resultDialog;
    public String									version="1.2";
    public String									date="15/12/2013";

    private static WarLauncher wl;

	public Controller(){
		super("init");
		getRoleNamesInPackage();
		
	}
	
	/**
	 * Methode initialisant le controleur.
	 */
	@SuppressWarnings("deprecation")
	public void setup(){
		System.out.println("Warbot II : v"+version+" du "+date);
    	playRole("Controller");
    //	System.out.println("gno");

        setHidden(true);
   //     System.out.println("gni");
        joinGroup(String.valueOf("admin"));
   //     System.out.println("gne");
        requestRole("admin", "WarExplorer", null);
   //     System.out.println("groumpf");
    }
	
	@Override
	public void die(){
		System.out.println("probleme");
		super.die();
	}
	
	/**
	 * Methode appelee lors du lancement du controleur. Elle permet
	 * tout d'abord de charger toutes les equipes presentes dans le
	 * dossier jar puis de lancer l'interface de choix des equipes
	 * en consequence.
	 * 
	 * @return {@code String} - la prochaine action a effectuer, 
	 * c'est a dire le lancement des equipes choisies
	 */
	public String init(){
		_boucle = true;
		
		try {
			initListTeam();
		} catch (IOException e) {
			System.exit(0);
		}
		wl = new WarLauncher();
        wl.setVisible(true);
        
        while(_boucle){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return "launchTeam";
	}
	
	/**
	 * Methode permettant de lancer les deux equipes choisies par
	 * l'utilisateur sur le terrain (dans le mode un contre un).
	 * 
	 * @return {@code String} - la prochaine action a effectuer, 
	 * c'est a dire action
	 */
	public String launchTeam(){
		initTeamMode1();
		
		return "action";
	}
	
	/**
	 * Methode permettant de faire apparaitre l'interface de 
	 * controle de l'affichage du terrain des le debut du match.
	 * Elle permet egalement de gerer la fin de match et de 
	 * determiner quelle equipe a gagnï¿½.
	 * 
	 * @return {@code String} - la prochaine action a effectuer,
	 * c'est a dire que dans ce cas, on boucle sur cette meme
	 * methode tant que soit une equipe n'a pas encore gagne, 
	 * soit que l'utilisateur n'a pas determine par lui meme
	 * la victoire
	 */
	public String action(){
		_victoryTeam = "";
		
		_victoryTeam = PanelControl.getVictoriousTeam();

		if(!_victoryTeam.equals("")){
			_panelControl.setVisible(false);
			return "endMatch";
		}

		List<WarAgentsAbstract> maListe = getAgents();

		boolean gagne = true;

		for(WarAgentsAbstract waa: maListe){
			if(waa.getType().equals("WarBase")){
				if(_victoryTeam.equals("")){
					_victoryTeam = ((WarDynamicAgentsAbstract)waa).getTeam();
				}else if(!_victoryTeam.equals(((WarDynamicAgentsAbstract)waa).getTeam())){
					gagne = false;
					break;
				}
			}
		}

		if(gagne){
			return endMatch();
		}

		generateFood();

		return "action";
	}
	
	/**
	 * Methode permettant la gestion de la fin du match.
	 * 
	 * @return {@code String} - la prochaine action a effectuer,
	 * c'est a dire que l'on a la premiere methode appelee
	 */
	public String endMatch(){
		_boucle = true;

		List<WarAgentsAbstract> maListe = getAgents();

		PanelControl.InitTeam();
		_panelControl.setVisible(false);
		SingletonAffichage.getInstance(mySelf()).releaseRefresh();

		if(_victoryTeam.equals("#draw#")){
			_resultDialog = new DrawDialog((Team)_listeTeam.values().toArray()[0], (Team)_listeTeam.values().toArray()[1]);
			_resultDialog.setVisible(true);
		}else{
			_resultDialog = new VictoryDialog(_listeTeam.get(_victoryTeam));
			_resultDialog.setVisible(true);
		}

		while(_boucle){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
		
		for(WarAgentsAbstract waa : maListe){
			waa.die();
		}
		
		return "init";
	}
	
	/**
	 * Methode permettant de renvoyer la liste de tous les agents 
	 * (de tous types).
	 * 
	 * @return {@code List<WarAgentsAbstract>} - la liste de tous les agents
	 */
	public List<WarAgentsAbstract> getAgents(){
		List<WarAgentsAbstract> retour = new ArrayList<WarAgentsAbstract>();

		for(int i = 0 ; i < _listRole.size() ; ++i){
			List<Turtle> tmp = getTurtlesListWithRole(_listRole.get(i));
			
			for(int k = 0 ; k < tmp.size() ; k++){
				if(tmp.get(k) instanceof WarAgentsAbstract){
					WarAgentsAbstract t = (WarAgentsAbstract) tmp.get(k);
					retour.add(t);
				}
			}
		}
		
		List<Turtle> tmp = getTurtlesListWithRole("WarRocket");
		
		for(int k = 0 ; k < tmp.size() ; k++){
			if(tmp.get(k) instanceof WarAgentsAbstract){
				WarAgentsAbstract t = (WarAgentsAbstract) tmp.get(k);
				retour.add(t);
			}
		}
		
		return retour;
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
			System.out.println("Erreur de rï¿½cupï¿½ration des rï¿½les");
		}
		_listRole.add("WarFood");
	}
	
	/**
	 * Methode permettant de generer aleatoirement des ressources
	 * sur le terrain.
	 */
	
	
	private void generateFood(){
	    int random = (int) (Math.random() * 1000);
	    if(random > (1000 - _simulationOption.getFoodAppearance())){
	        WarFood wf = new WarFood();
	        SingletonAffichage.getInstance(mySelf()).initRefresh(createTurtle(wf));
	    }
	}
	
	
	
	
	
	/**
	 * Initialisation des equipes dans le mode un contre un.
	 */
	private void initTeamMode1(){
		Set<String> listeNomEquipe = _listeTeam.keySet();
		
		String team1 = "";
		String team2 = "";
		
		Iterator<String> i = listeNomEquipe.iterator();
		int team = 0;
		
		System.out.println("Noms Žquipes: "+listeNomEquipe);
		
		while(i.hasNext()){
			String t = i.next();
			
			if(team == 0){
				team1 = new String(t);
			}else{
				team2 = new String(t);
			}
			
			for(int j = 0 ; j < _simulationOption.getNbExplorer() ; j++){
				try {
					WarBrain monBrain = (WarBrain) _listeTeam.get(t).getClasse("warexplorer").getConstructor().newInstance();
					Turtle turtle = (WarDynamicAgentsAbstract) new WarExplorer(t, (team == 0) ? 50 : (WarViewer.XSIM - 50), (team == 0) ? 50 : (WarViewer.YSIM - 50), monBrain, (team == 0) ? Color.RED : Color.BLUE);
					createTurtle(turtle);
				} catch (InstantiationException e) {
					/*System.out.println("Erreur 1 "+*/e.printStackTrace()/*)*/;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			for(int j = 0 ; j < _simulationOption.getNbRocketLauncher() ; j++){
				try {
					WarBrain monBrain = (WarBrain) _listeTeam.get(t).getClasse("warrocketlauncher").getConstructor().newInstance();
					Turtle turtle = (WarDynamicAgentsAbstract) new WarRocketLauncher(t, (team == 0) ? 50 : (WarViewer.XSIM - 50), (team == 0) ? 50 : (WarViewer.YSIM - 50), monBrain, (team == 0) ? Color.RED : Color.BLUE);
					createTurtle(turtle);
				} catch (InstantiationException e) {
					/*System.out.println("Erreur 1 "+*/e.printStackTrace()/*)*/;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			for(int j = 0 ; j < _simulationOption.getNbBase() ; j++){
				try {
					@SuppressWarnings("rawtypes")
					HashMap<String, Class> tmp = new HashMap<>(_listeTeam.get(t).getClasses());
					tmp.remove("base");
					WarBrain monBrain = (WarBrain) _listeTeam.get(t).getClasse("warbase").getConstructor().newInstance();
					Turtle turtle = (WarDynamicAgentsAbstract) new WarBase(t, (team == 0) ? (Math.random()*50) : (WarViewer.XSIM - (Math.random()*50)), 
																			  (team == 0) ? (Math.random()*50) : (WarViewer.YSIM - (Math.random()*50)), 
																			   monBrain, tmp, (team == 0) ? Color.RED : Color.BLUE);
					createTurtle(turtle);
				} catch (InstantiationException e) {
					/*System.out.println("Erreur 1 "+*/e.printStackTrace()/*)*/;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			team ++;
		}
		
		try{
			_panelControl = new PanelControl(team1, team2);
			_panelControl.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode permettant de renvoyer le type de PATH utilise par l'OS.
	 * 
	 * @return {@code String} - le type de PATH utilise par l'OS
	 */
	private static String getJARPath(){
		String OS = System.getProperty("os.name").toLowerCase();
		
		String retour = "jar"+File.separator;
		
		if(OS.indexOf("win") >= 0){
			System.out.println("Windows : "+retour);
		}else if(OS.indexOf("mac") >= 0){
			System.out.println("Mac : "+retour);
		}else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
			System.out.println("Linux : "+retour);
		}else if(OS.indexOf("sunos") >= 0){
			System.out.println("solaris : "+retour);
		}else{
			System.out.println("Unknow : "+retour);
		}
		
		return retour;
	}
	
	/**
	 * Initialisation des equipes.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private static void initListTeam() throws IOException{
        
        File f = new File(getJARPath()/*"C:\\Users\\Shyzkanza\\Desktop\\jar\\"*/);
        File[] tmp = f.listFiles();
        
        Analyseur analXML = new Analyseur();
        Team t;
        
        HashMap<String, String> listeAgents = new HashMap<String, String>();
        String JARName = "";
        String teamName = "";
        String imageName = "";
        String soundName = "";
        String colorTeam = "";
        
        for(File fic : tmp){
            
            try{
                t = new Team();

                if(fic.getCanonicalPath().endsWith("jar")){

                    ZipFile zf = new ZipFile(fic); 
                    
                    Enumeration<?> entries = zf.entries();
                    ZipEntry entry;

                    while(entries.hasMoreElements()){       
                        entry = (ZipEntry) entries.nextElement();
                        File file = new File("tmp.xml");

                        if(entry.getName().endsWith("config.xml")){
                            int k=0;
                            byte[] bytes = new byte[2048];
                            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                            BufferedInputStream in = new BufferedInputStream(zf.getInputStream(entry));
                            while((k = in.read(bytes)) != -1)
                                out.write(bytes,0,k);

                            in.close();
                            out.flush();
                            out.close();

                            analXML.Ouverture();
                            JARName = fic.getCanonicalPath();
                            teamName = analXML.getTeamName();
                            imageName = analXML.getIconeName();
                            soundName = analXML.getSoundName();
                            listeAgents = analXML.getClasses();
                            colorTeam = analXML.getTeamColor();
                            break;
                        }
                    }
                    
                    entries = zf.entries();
                    ImageIcon newIcon = null;
                    Clip newClip	  = null;

                    while(entries.hasMoreElements()){       
                        entry = (ZipEntry) entries.nextElement();

                        if(entry.getName().endsWith(imageName)){
                            File file = new File("tmpimg"+imageName.substring(imageName.lastIndexOf('.'), imageName.length()));
                            int k=0;
                            byte[] bytes = new byte[2048];
                            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                            BufferedInputStream in = new BufferedInputStream(zf.getInputStream(entry));
                            while((k = in.read(bytes)) != -1)
                                out.write(bytes,0,k);

                            in.close();
                            out.flush();
                            out.close();

                            ImageIcon myIcon2 = new ImageIcon("tmpimg"+imageName.substring(imageName.lastIndexOf('.'), imageName.length()));
                            Image img = myIcon2.getImage();  
                            Image newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);  
                            newIcon = new ImageIcon(newimg); 
                        }else if(entry.getName().endsWith(soundName)){
                            File file = new File("tmpsnd"+imageName.substring(imageName.lastIndexOf('.'), imageName.length()));
                            int k=0;
                            byte[] bytes = new byte[2048];
                            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                            BufferedInputStream in = new BufferedInputStream(zf.getInputStream(entry));
                            while((k = in.read(bytes)) != -1)
                                out.write(bytes,0,k);

                            in.close();
                            out.flush();
                            out.close();

                            /*try {
                    			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("tmpsnd"+imageName.substring(imageName.lastIndexOf('.'), imageName.length())));
                    			newClip = AudioSystem.getClip();
                    			newClip.open(audioInputStream);
                    			newClip.loop(Clip.LOOP_CONTINUOUSLY);
                    			newClip.stop();
                            } catch (UnsupportedAudioFileException e) {
                    			// TODO Auto-generated catch block
                    			e.printStackTrace();
                    		} catch (IOException e) {
                    			// TODO Auto-generated catch block
                    			e.printStackTrace();
                    		} catch (LineUnavailableException e) {
                    			// TODO Auto-generated catch block
                    			e.printStackTrace();
                    		}*/
                        }
                    }
                    t.addInfos(newIcon, teamName, JARName, colorTeam, null);
                    zf.close();

                    // On parcours le jar pour recuperer les classes
                    JarInputStream jarFile = new JarInputStream(new FileInputStream(JARName));
                    JarEntry jarEntry;
                    HashMap<String, Class> listeClassesAgents = new HashMap<String, Class>();

                    while (true) {
                        jarEntry = jarFile.getNextJarEntry();

                        if (jarEntry == null) {
                            break;
                        }

                        if (jarEntry.getName().endsWith(".class")) {
                            String temp = jarEntry.getName().substring(jarEntry.getName().lastIndexOf('/')+1, jarEntry.getName().length());
                            String tempAssoc = listeAgents.get(temp);
                            try {

                                ClassLoader.getSystemClassLoader();
                                URL url = new URL("jar:file:" + JARName + "!/");
                                URLClassLoader ucl = new URLClassLoader(new URL[] { url });

                                String classname = jarEntry.getName().replace('/', '.').substring(0, jarEntry.getName().length() - 6);
                                //System.out.println(temp + " = "+ tempAssoc  +" : " + Class.forName(classname, true, ucl).getName());
                                listeClassesAgents.put(tempAssoc, Class.forName(classname, true, ucl));
                            } catch (ClassNotFoundException ex) {
                                System.err.println("Erreur chargement de classe "+ex.getLocalizedMessage());
                            }
                        }
                    }

                    t.addClasses(listeClassesAgents);
                    jarFile.close();
                    
                    boolean add = true;

                    for(int i = 0 ; i < _listeTeam.size() ; ++i){
                        if(_listeTeam.get(t.getName()) != null){
                            add = false;
                            break;
                        }
                    }

                    if(add){
                        _listeTeam.put(t.getName(), t);
                    }
                }
                
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
    }
}
