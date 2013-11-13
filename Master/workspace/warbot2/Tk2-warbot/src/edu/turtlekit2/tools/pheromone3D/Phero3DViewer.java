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
package edu.turtlekit2.tools.pheromone3D;

import javax.swing.JComponent;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;

/**
 * This agent displays a 3D rendering of a pheromone.
 * @author G. Beurier
 * @version 1.0 - 2/2010
 * @see Phero3DPanel
 */
public class Phero3DViewer extends Viewer{
	private static final long serialVersionUID = 1L;
	Phero3DPanel pheroViewer;
	String phero;
	
	@Override
	public void setup() {
		phero = getAttrib().getString("pheromone");
		pheroViewer = new Phero3DPanel(patchGrid, phero, envWidth, envHeight);
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JComponent>(pheroViewer, SimulationBoard.VIEWER_ZONE, "3D Phero"));
	}
	
	public void watch(){
		if(pheroViewer.isShowing())
			pheroViewer.update();
	}
	
	public void reset(){
		pheroViewer.reset(patchGrid, phero, envWidth, envHeight);
	}
}
