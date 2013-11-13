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

package edu.turtlekit2.tools.viewer2D;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JScrollPane;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.kernel.environment.Patch;
import edu.turtlekit2.kernel.environment.PatchVariable;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;

/**
 * <p>Titre : Viewer2D</p>
 * <p>Description : Viewer2D is a Viewer agent. It displays flavors/pheromones according to RGB canals and
 * permits flavors observations and modifications in the environment in real time. </p>
 * <p>Use : To display a flavor on a RGB canal, click on the color then on the flavor name (mixing canals is allowed). To drop a flavor, click on drop then on the flavor name, then click within the environment. 
 * To modify the displaying scale, click on a color then roll mouse wheel over the environment. To display a flavor/pheromone quantity, do as drop
 * and move the mouse cursor over the environment.</p>
 * <p></p>
 * <p>XML Attributes : None</p>
 * @author G. Beurier
 * @version 0.9 - 4/2007
 */

public class Viewer2D extends Viewer{
	private static final long serialVersionUID = 1L;


	boolean turtleDisplay = true;
	String dropedPheromones = "";
	Hashtable<String,Float> intensity;
	Hashtable<String,Color> colors;
	Hashtable<String,Boolean> pheroDisp;
	ArrayList<String> pheromones;
	double maxRed = 0;
	double maxGreen = 0;
	double maxBlue = 0;

	/**No usage*/
	public void init() {}

	/**MadKit usage, no redefinition*/
	public void initGUI() {
		super.initGUI();
		intensity = new Hashtable<String, Float>();
		colors = new Hashtable<String, Color>();
		pheroDisp = new Hashtable<String, Boolean>();
		pheromones = new ArrayList<String>();


		ArrayList<PatchVariable> flavors = getFlavors();
		for (Iterator<PatchVariable> iterator = flavors.iterator(); iterator.hasNext();) {
			PatchVariable patchVariable = iterator.next();
			pheromones.add(patchVariable.getName());
			intensity.put(patchVariable.getName(), 500f);
			colors.put(patchVariable.getName(), Color.WHITE);
			pheroDisp.put(patchVariable.getName(), false);
		}


		ViewOptions options = new ViewOptions(pheromones, this);
		JScrollPane pane = new JScrollPane(options);
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JScrollPane>(pane, SimulationBoard.CONSOLE_ZONE, "Viewer Options"));
	}



	/**Standard graphical representation of turtle. Can be disabled/enabled in real time. 
	 * You can override this method in order to give a special graphic representation of your turtles.*/
	public void paintTurtle(Graphics g, Turtle t,int x,int y,int cellS)
	{
		if(turtleDisplay){
			g.setColor(t.getColor());
			g.fillRect(x,y,cellS,cellS);
		}
	}

	/**The Paintpatch method has been overriden in order to achieve specific representation of flavors. 
	 * Each flavor can be represented as a function of the Red/Blue/Green/Black canals.*/
	public void paintPatch(Graphics g, Patch p,int x,int y,int CellSize)
	{
		if(p.getTurtles().length == 0){
			if(p.color.equals(Color.BLACK)){

				double red = getQuantity(p,0);
				double blue = getQuantity(p,2);
				double green = getQuantity(p,1);

				if(red > maxRed) maxRed = red;
				if(blue > maxBlue) maxBlue = blue;
				if(green > maxGreen) maxGreen = green;

				if(red<0) red = 0;
				if(green<0) green = 0;
				if(blue<0) blue = 0;

				int r = new Double(red/maxRed * 255).intValue();
				int b = new Double(blue/maxBlue * 255).intValue();
				int gr = new Double(green/maxGreen * 255).intValue();


				Color d = new Color( r, gr, b);
				g.setColor(d);
			}
			else
				g.setColor(p.color);


			g.fillRect(x,y,CellSize,CellSize);

		}
	}



	/**
	 * Sum and scale pheromone quantity for each RGB canal.
	 * @param p - the patch where pheromones are
	 * @param i - the RGB canal (1-red, 2-green, 3-blue)
	 * @return the scaled quantity
	 */
	private double getQuantity(Patch p, int i) {
		double result = 0;
		for (int j = 0; j < pheromones.size(); j++) {
			String o = pheromones.get(j);
			double factor = 0;
			switch (i) {
			case 0:
				factor = (colors.get(o).getRed() / 255);
				break;
			case 1:
				factor = (colors.get(o).getGreen() / 255);
				break;
			case 2:
				factor = (colors.get(o).getBlue() / 255);
				break;
			default:
				break;
			}
			double add = p.smell(o) 
			* (intensity.get(o) / 500)
			* factor;
			if(!pheroDisp.get(o)) add = 0;
			result += add;
		}
		return result;
	}

	public void updatePheromoneColor(String phero2, Color newColor) {
		colors.put(phero2, newColor);
	}

	public void updatePheromoneIntensity(String phero2, Float value) {
		intensity.put(phero2, value);

	}

	public void setViewable(String phero2, boolean b) {
		pheroDisp.put(phero2, b);
	}

	public void setTurtlesViewable(boolean b) {
		turtleDisplay = b;
	}

	public void setDropedPheromones(String selectedValue) {
		dropedPheromones = selectedValue;
	}
}

