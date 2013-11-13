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
package edu.turtlekit2.kernel.agents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;

import org.w3c.dom.Element;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.msg.TopMessage;
import edu.turtlekit2.kernel.environment.PatchVariable;
import edu.turtlekit2.kernel.environment.TurtleEnvironment;
import edu.turtlekit2.kernel.python.PythonCommandCenter;
import edu.turtlekit2.ui.ControlBoard;
import edu.turtlekit2.utils.XMLAttributes;

/**
 * This class launch every agents of a simulation then run it by calling the Scheduler at each step.
 * @author G.Beurier
 * @version 0.9 - 8/2008
 * @see TurtleScheduler
 */
public class SimulationRunner extends Agent{
	private static final long serialVersionUID = 1L;
	
	public String simulationGroup;
	public Element simuDescription;
	ControlBoard onScreen;
	public TurtleEnvironment environment;
	public TurtleScheduler sch;
	Collection<Turtle> turtles;
	Collection<Observer> observers;
	Collection<Viewer> viewers = new ArrayList<Viewer>();
	ArrayList<PatchVariable> flavors;
	
	public boolean start = false;
	public boolean run = false;

	public SimulationRunner(Element simuDescription, String simulationName) {
		this.simuDescription = simuDescription;
		this.simulationGroup = simulationName;
	}

	private void launchFlavors( Collection<PatchVariable> flavors ){
		for (Iterator<PatchVariable> iterator = flavors.iterator(); iterator.hasNext();) {
			PatchVariable patchVariable = (PatchVariable) iterator.next();
			environment.addGridVariable(patchVariable);
		} 
	}

	private void launchTurtles(Collection<Turtle> turtles){
		for (Iterator<Turtle> iterator = turtles.iterator(); iterator.hasNext();) {
			Turtle turtle = (Turtle) iterator.next();
			if(turtle.getAttributes().containsKey("x") && turtle.getAttributes().containsKey("y"))
				environment.addAgent(turtle, turtle.getAttributes().getInt("x"), turtle.getAttributes().getInt("y"));
			else{
				environment.addAgent(turtle);
			}
				
		}
	}
	
	public void launchViewer(XMLAttributes att){
		Observer obs = AgentFactory.instanciateObserver(att);
		if(obs != null && obs instanceof Viewer) viewers.add((Viewer)obs);
		obs.simulationGroup = simulationGroup;
		obs.envWidth = environment.x;
		obs.envHeight = environment.y;
		obs.setFlavors(flavors);
		launchAgent(obs, "Viewer", true);
		obs.disposeMyGUI();
	}

	private void launchObservers(Collection<Observer> observers) {
		int i = 0;
		for (Iterator<Observer> iterator = observers.iterator(); iterator.hasNext();) {
			Observer observer = iterator.next();
			if(observer instanceof Viewer) viewers.add((Viewer)observer);
			observer.simulationGroup = simulationGroup;
			observer.envWidth = environment.x;
			observer.envHeight = environment.y;
			observer.setFlavors(flavors);
//			observer.patchGrid = environment.grid;
			launchAgent(observer, "Observer" + i++, true);
			observer.disposeMyGUI();
		}
	}


    public void launchPython() throws Exception {
        AbstractAgent a = new PythonCommandCenter(simulationGroup);
        launchAgent(a, "Python command center", true);
        a.disposeMyGUI();
    }

	/////////////////////////////////////////// Activation and running methods
	/** MadKit usage */
	public void activate() {
		
//		GroupObserver gobs = new GroupObserver();
//		launchAgent(gobs, simulationGroup+ "gobs", true);
//		sendMessage(Tk2Launcher.COMMUNITY, simulationGroup, "UIManager", 
//				new GUIMessage<Component>((JComponent)gobs.getGUIObject(), SimulationBoard.VIEWER_ZONE, "gobs"));
//		gobs.disposeMyGUI();
		
		
		//Phase 1: MAS organization
//		createGroup(false, Tk2Launcher.COMMUNITY, simulationName, null, null);
		requestRole(Tk2Launcher.COMMUNITY, simulationGroup, "launcher", null);
		
		//Phase 3: Simulation initialization
		sch = new TurtleScheduler(simulationGroup);
		sch.group = simulationGroup;
		sch.delay =  AgentFactory.getIntFromNode(simuDescription, "CyclePause", 1);
		launchAgent(sch, simulationGroup + " scheduler", false);
		if(AgentFactory.hasActivators(simuDescription))
			sch.importActivators(AgentFactory.getActivators(simuDescription, simulationGroup));
		else sch.initDefaultActivators();
		
		
		waitNextMessage();

		environment = AgentFactory.getEnvironment(simuDescription);
		environment.simulationGroup = simulationGroup;
		launchAgent(environment, simulationGroup + " world", false);
		
		
		flavors = AgentFactory.getFlavors(simuDescription);
		this.launchFlavors(flavors);
		environment.initNeighborhood();

		//        if(geneticMode) initializeGenetic();

		turtles = AgentFactory.getTurtles(simuDescription);
		this.launchTurtles(turtles);
		
		
		
		
//		this.launchViewers(AgentFactory.getViewers(simuDescription));
		
		observers = AgentFactory.getObservers(simuDescription);
		this.launchObservers(observers);
		

		//Phase 2 GUI init
		onScreen = new ControlBoard(this);
		onScreen.initialisation();
		
//		try {
//			launchPython();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		println("Simulation is initialized !!");
		println("Click to begin...");
		while (true) {
			pause(50);
			if (start) {
				break;
			}
			if (nextMessage() != null) {
				onScreen.startStop.doClick();
			}
		}

		//phase 3: launching simulation
		start = true;
		run = true;
		if(flavors.size()>0)
			sch.setExistingFlavors(true);
		sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.RUNNING));
		
		
//		disposeMyGUI();
	}




	/** MadKit kernel usage */
	public final void live() {
		int i = -1;
		while (true) {
			pause(sch.delay);
//			exitImmediatlyOnKill();
			if (sch != null && start && i != sch.iteration) {
				i = sch.iteration;
				if(i%100 == 0) onScreen.stepLabel.setText(new Integer(i).toString());
//				if(i%100 == 0) println("step " + i);
			}
		}

	}

	/** MadKit kernel usage. No redefinition. Closing the simulation. */
	public final void end() {
		println("Closing simulation");
		println("Please wait...");
		sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.PAUSED));
		sendMessage(Tk2Launcher.COMMUNITY, simulationGroup, "UIManager", new TopMessage(-1));
		if (environment != null){
			Collection<Turtle> turtles = environment.getTurtlesList();
			for (Iterator<Turtle> iterator = turtles.iterator(); iterator.hasNext();) {
				Turtle turtle = (Turtle) iterator.next();
				environment.killAgent(turtle);
			}
			killAgent(environment);
		}
		
		for (Iterator<Observer> iterator = observers.iterator(); iterator.hasNext();) {
			Observer observer = (Observer) iterator.next();
			killAgent(observer);
		}
		for (Iterator<Viewer> iterator = viewers.iterator(); iterator.hasNext();) {
			Viewer viewer = (Viewer) iterator.next();
			killAgent(viewer);
		}
		killAgent(sch);
	}



	/** reseting the simulation. Unstable in genetic Mode*/
	final public void setReset() {
		sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.STOPPED));
		start = false;
		println("Reseting: Please wait ...");
		run = false;
		environment.clearAllTurtles();
		environment.initGrid();
		
		flavors = AgentFactory.getFlavors(simuDescription);
		this.launchFlavors(flavors);
		environment.initNeighborhood();

		//        if(geneticMode) initializeGenetic();

		turtles = AgentFactory.getTurtles(simuDescription);
		this.launchTurtles(turtles);
		
		
		for (Iterator<Observer> iterator = observers.iterator(); iterator.hasNext();) {
			Observer observer = (Observer) iterator.next();
			observer.setup();
		}

		start=true;
		run=true;
		sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.RUNNING));
	}



	/** setter for toroidal world usage */
	final public void setWrapModeOn(boolean b) {
		if(run)
			pauseSimulation();
		if (environment != null){
			environment.wrap = b;
			environment.initNeighborhood();
		}
		if(run)
			sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.RUNNING));
	}

	public void setStop() {
		if (run) {
			run = false;
			pauseSimulation();
			println("Simulation paused");
		} else {
			sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.RUNNING));
			println("Simulation running");
			run = true;
		}
	}

	final public void stepByStep() {
		sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.STEP));
		run=false;
	}
	
    
    public void pauseSimulation(){
    	sendMessage(sch.getAddress(), new TopMessage(TurtleScheduler.PAUSED));
        waitNextMessage();
    }

}
