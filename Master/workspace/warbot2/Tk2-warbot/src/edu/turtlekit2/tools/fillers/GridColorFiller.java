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
 * Adapted from the termites simulation of TurtleKit (v1). 
 * @author G.Beurier
 * @version 1.1 - 4/2010
 * 
 * 
 * The only goal of this Observer is to setup the patches for 
	the termite simulation, having its variables: patchGrid, EnvWidth, EnvHeight

  @author Fabien MICHEL
  @version 1.0 20/3/2000 */

@SuppressWarnings("serial")
public class GridColorFiller extends Observer
{
	float densityRate;

	public GridColorFiller()
	{

	}

	public void setup()
	{
		densityRate = new Double(this.getAttrib().getDouble("density")).floatValue();
		String[] rgb = this.getAttrib().getString("color").split(",");
		Color color = new Color(
			Integer.parseInt(rgb[0]),
			Integer.parseInt(rgb[1]),
			Integer.parseInt(rgb[2])
		);
		
		for(int i=0;i<envWidth;i++)
			for(int j=0;j<envHeight;j++)
				if (Math.random() < densityRate){
					patchGrid[i][j].setColor(color);}
				else
					patchGrid[i][j].setColor(Color.black);
	}

}
