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
package edu.turtlekit2.ui;

import java.util.HashMap;

import javax.swing.JComponent;

import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Message;
import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.msg.TopMessage;
import edu.turtlekit2.ui.utils.GUIMessage;
import edu.turtlekit2.ui.utils.GUIObjects;

/**
 * An agent that handles the graphics management. It connects agents with a GUI with the Simulation Board.
 * To display a graphical component, an agent has to send a GUIMessage to its group "UIManager":
 * 
 * {@code sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager", 
 * 			new GUIMessage<Component>(ComponentToDisplay, SimulationBoard.ZONE, "NameOfTheComponent"));}
 * 
 * or
 * {@code}
 * {@code sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager", 
 *  	new GUIMessage<Component>(ComponentToDisplay, "NameOfTheComponent"));}
 * 
 * ZONES are disabled except for SimulationBoard.BUTTON_ZONE
 * @author G. Beurier
 * @version 0.9 - 4/2010
 * @see SimulationBoard
 * @see GUIMessage#GUIMessage(Object, int, String)
 * @see GUIMessage#GUIMessage(Object, String)
 */

public class BoardAgent extends Agent {
	static final long serialVersionUID = 1l;
	public SimulationBoard myGUI;
	String simulationName;
	
	HashMap<AgentAddress, GUIObjects> viewsMap = new HashMap<AgentAddress, GUIObjects>();
	HashMap<GUIObjects, AgentAddress> agentsMap = new HashMap<GUIObjects, AgentAddress>();
	
	public BoardAgent(String simulationName) {
		super();
		this.simulationName = simulationName;
		myGUI = new SimulationBoard();
	}
	
	@Override
	public void activate() {
		super.activate();
		this.requestRole(Tk2Launcher.COMMUNITY, simulationName, "UIManager", null);
		//this.disposeMyGUI();
	}
	
	
	/**
	 * Life cycle of the Board Agent. Only computes messages.
	 */
	@SuppressWarnings("unchecked")
	public void live(){
		int nbMsg = 0;
		while(true){
			nbMsg++;
			Message msg = this.waitNextMessage();
			if(msg instanceof GUIMessage)
				computeMessage((GUIMessage<JComponent>)msg);
			else if (msg instanceof TopMessage)
				killAgent(this);
		}
	}
	
	/**
	 * Checks mailBox and forwards message to the Simulation Board.
	 * @param msg - the graphical message
	 */
	public final void computeMessage(GUIMessage<JComponent> msg) {
		AgentAddress sender = msg.getSender();
		
		myGUI.addTabbedComponent(msg.getContent(), msg.getLocation(), msg.name);
	}
	
	
}
