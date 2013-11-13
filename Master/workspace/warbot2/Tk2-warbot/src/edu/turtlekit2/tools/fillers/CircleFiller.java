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


package edu.turtlekit2.tools.fillers;

import java.awt.Color;

import edu.turtlekit2.kernel.agents.Observer;
/**
 * <p>Title : Circle Filler.  </p>
 * <p>Description : observer that build
 */  
public class CircleFiller extends Observer {
	private static final long serialVersionUID = 1L;

	public void setup(){
		int x = getIntParam("x");
		int y = getIntParam("y");
		int radius = getIntParam("radius", 5);

		if(attrib.containsKey("color")){
			Color color = getColorParam("color");
			for (int i = x-radius; i < x+radius; i++) {
				for (int j = y-radius; j < y+radius; j++) {
					patchGrid[i][j].setColor(color);
				}
			}
		}else if(attrib.containsKey("pheromone")){
			String pheromone = getParam("pheromone");
			double qtty = getDoubleParam("quantity");
			for (int i = x-radius; i < x+radius; i++) {
				for (int j = y-radius; j < y+radius; j++) {
					patchGrid[i][j].setPatchVariable(pheromone, qtty);
				}
			}
		}
		
			
	}
	
	
	public void watch(){}
}
