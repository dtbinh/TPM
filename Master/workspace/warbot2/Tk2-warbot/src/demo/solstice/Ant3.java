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
package demo.solstice; 
import java.awt.Color;

import edu.turtlekit2.kernel.agents.Turtle;


@SuppressWarnings("serial")
public class Ant3 extends Turtle
{
	int foodCarried = 0;
	
	public Ant3()
	{
		super("searchForFood");
		randomHeading();
	}
	
	public String searchForFood()
	{
		double foodQuantity = smell("food");
		if (foodQuantity > 0){
			emit("food", -10);
			setColor(Color.YELLOW);
			return ("returnToNest");
		}else{
			wiggle();
			return ("searchForFood");
		}
	}
	
	public String returnToNest()
	{
		if (getPatchColor().equals(Color.RED)){
			emit("foodStock", 10);
			setColor(Color.RED);
			return ("searchForFood");
		}else{
			wiggle();
			return ("returnToNest");
		}
	}	
}
