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

import edu.turtlekit2.kernel.agents.Observer;
/**
 * <p>Title : Gradient Builder.  </p>
 * <p>Description : observer that build gradient of pheromones in the environement. From a quantity X to 0. </p>
 * <p></p>
 * <p>XML Attributes :</p>
 * <p>Flavor: the gradient pheromone name. No default.</p>
 * <p>Origin: the origin of the gradient (position of the max quantity). "top", "left", "right", "bottom". default is top.</p>
 * <p>Quantity: the quantity of the flavor at the origin position. default is 10000.</p>
 * <p></p>
 * @author G. Beurier
 * @version 1.0 - 6/2008
 */  
public class GradientBuilder extends Observer {
	private static final long serialVersionUID = 1L;


	public void setup(){
	String pheromone = attrib.getString("pheromone");
	String origin = "top";
	if(attrib.containsKey("origin")) origin= attrib.getString("origin");
	double qtty = 10000;
	if(attrib.containsKey("quantity")) qtty = attrib.getDouble("quantity");
	if(origin.equals("left")){
		double decrease = qtty/envWidth;
		for(int i=0;i<envWidth;i++){
			qtty -= decrease;
		      for(int j=0;j<envHeight;j++){
		      	patchGrid[i][j].setPatchVariable(pheromone,qtty);
		      }
		}
	}else if(origin.equals("right")){
		double decrease = qtty/envWidth;
		for(int i=0;i<envWidth;i++){
			qtty -= decrease;
		      for(int j=0;j<envHeight;j++){
		      	patchGrid[envWidth - (i+1)][j].setPatchVariable(pheromone,qtty);
		      }
		}
	}else if(origin.equals("bottom")){
		double decrease = qtty/envHeight;
		for(int i=0;i<envHeight;i++){
			qtty -= decrease;
		      for(int j=0;j<envWidth;j++){
		      	patchGrid[j][i].setPatchVariable(pheromone,qtty);
		      }
		}
	}else if(origin.equals("top")){
		double decrease = qtty/envHeight;
		for(int i=0;i<envHeight;i++){
			qtty -= decrease;
		      for(int j=0;j<envWidth;j++){
		      	patchGrid[j][envHeight - (i+1)].setPatchVariable(pheromone,qtty);
		      }
		}
	}};
	
	
	public void watch(){}
}
