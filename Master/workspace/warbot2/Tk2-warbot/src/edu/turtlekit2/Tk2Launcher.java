/*
 * TurtleKit - An Artificial Life Simulation Platform
 * Copyright (C) 2000-2010 Fabien Michel, Gregory Beurier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package edu.turtlekit2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import madkit.kernel.AbstractAgent;

import org.w3c.dom.Document;

import edu.turtlekit2.kernel.agents.AgentFactory;
import edu.turtlekit2.kernel.agents.SimulationRunner;
import edu.turtlekit2.ui.BoardAgent;
import edu.turtlekit2.ui.TurtleKitGUI;
import edu.turtlekit2.utils.TxtFileReader;
import edu.turtlekit2.utils.XMLParser;


/**
 * The Launcher Agent of the TurtleKit2 platform.
 * @author G. Beurier
 * @version 1.0 - 4/2010
 */
public class Tk2Launcher extends AbstractAgent {
	private static final long serialVersionUID = 1L;

	/** The default TurtleKit community */
	public static final String COMMUNITY="Turtlekit";

	private TurtleKitGUI myGUI;

	/**
	 * Launch the Tk2 GUI.
	 * @see TurtleKitGUI
	 */
	public void activate(){
		
		this.disposeMyGUI();
		final Tk2Launcher m = this;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				myGUI = new TurtleKitGUI(m);
				myGUI.setVisible(true);
				loadDefaultSimulations("Simulations.cfg");
			}
		});
	}

	/**
	 * Launch the simulations defined in a file
	 * @param fileName - the file name
	 */
	private void loadDefaultSimulations(String fileName){
		ArrayList<String> simus = new ArrayList<String>();
		try {
			simus = TxtFileReader.processLineByLine(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (Iterator<String> iterator = simus.iterator(); iterator.hasNext();) {
			String simuPath = (String) iterator.next();
			if(simuPath.startsWith("#"))
				;
//				System.err.println("Ignoring: " + simuPath);
			else if (simuPath.length() == 0 || simuPath.startsWith("//"))
				;
			else{
				File file = new File(simuPath);
				if(file.exists()) 
					newSimulation(simuPath);
				else 
					System.err.println("Config file invalid. Unable to load " + simuPath);
			}
		}
	}


	/**
	 * Checks existing MadKit groups to create the simulation group name.
	 * @param doc - the simulation description file (XML)
	 * @return - the simulation group name
	 */
	public String getGroupName(Document doc){
		String simulationName = AgentFactory.getFromNode(doc.getDocumentElement(), "name", "Tk2 Simu");
		int i = 2;
		if (isGroup(Tk2Launcher.COMMUNITY,simulationName)) {
			while (isGroup(Tk2Launcher.COMMUNITY,simulationName + " " + i))
				i++;
			simulationName += " " + i;
		}
		return simulationName;
	}

	/**
	 * Launch a new simulation
	 * @param fileName - the simulation fileName (.XML)
	 */
	public void newSimulation(String fileName){
		System.err.println("Loading " + fileName);
		Document doc = XMLParser.getDocFromFile(fileName);

		String simulationGroup = getGroupName(doc);

		createGroup(false, Tk2Launcher.COMMUNITY, simulationGroup, null, null);

		System.err.println(simulationGroup + " loaded...");

		BoardAgent uiManager = new BoardAgent(simulationGroup);
		launchAgent(uiManager, simulationGroup + " UIManager", true);

		System.err.println(uiManager.getName() + " loaded...");

		SimulationRunner newLauncher = new SimulationRunner(
				XMLParser.getDocFromFile(fileName).getDocumentElement(),
				simulationGroup);
		launchAgent((AbstractAgent)newLauncher, simulationGroup + " launcher" ,true);
		newLauncher.disposeMyGUI();

		myGUI.createSimulationWindow(simulationGroup, newLauncher, uiManager);
		uiManager.disposeMyGUI();
		System.err.println(newLauncher.getName() + " loaded...");

		//				TreeEditorUI treeEdit = new TreeEditorUI(doc);
		//				m.sendMessage(Tk2Launcher.COMMUNITY, simulationGroup, "UIManager", new GUIMessage<JComponent>(treeEdit, SimulationUI.EDITOR_ZONE, "Parameters"));
	}
}
