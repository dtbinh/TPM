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

package edu.turtlekit2.tools.viewer3D;

import javax.swing.JComponent;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.TurtleProbe;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;

/**
 * This agent displays a 3D rendering of the environment: Patches and Turtles.
 * The turtles are rendered by {@link Quad3DViewer} and the patches by {@link QuadMesh}.
 * <p>XML Attributes :</p>
 * <p>noground: false/true. (De)Activate the displaying of the patches. The display of 
 * patches slow down the simulation.
 * <p></p>
 * @author G. Beurier
 * @version 1.0 - 2/2010
 * @see Quad3DViewer
 */
public class Viewer3D extends Viewer{
	private static final long serialVersionUID = 1L;
	Quad3DViewer quadViewer;
	
	@Override
	public void setup() {
		TurtleProbe allTurtles = new TurtleProbe(getSimulationGroup(),"turtle");
		addProbe(allTurtles);
		boolean noground = false;
		if(getAttrib().containsKey("noground"))
			noground = getAttrib().getBool("noground");
		quadViewer = new Quad3DViewer(patchGrid, allTurtles, noground, envWidth, envHeight);
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JComponent>(quadViewer, SimulationBoard.VIEWER_ZONE, "3D View"));
	}
	
	public void watch(){
		if(quadViewer.isShowing())
			quadViewer.update();
	}
	
	public void reset(){
		quadViewer.reset(patchGrid, envWidth, envHeight);
	}
}
