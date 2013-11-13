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
public class Ant4 extends Turtle
{
	double quantity = 50;
	
	public Ant4()
	{
		super("searchForFood");
		randomHeading();
	}
	
	@Override
	public void setup() {
		super.setup();
		emit("nestScent", 500);
	}

	
	public String searchForFood()
	{
		double foodQuantity = smell("food");
		if (foodQuantity > 0){
			emit("food", -10);
			turnLeft(180);
			quantity = 50;
			setColor(Color.YELLOW);
			emit("foodScent", 200);
			return("returnToNest");
		}
		else{
			double foodScent = smell("foodScent");
			if(smell("foodScent") <= 3) 
				wiggle();
			else{
				setHeading(getDirectionOfMaxInMyDirection("foodScent"));
				if(foodScent > smellNextPatch("foodScent"))
					wiggle();
				else
					fd(1);
			}
			if(quantity>0) 
				emit("nestScent", quantity--);
			
			if(getPatchColor().equals(Color.RED))
				emit("nestScent", 100);
			return ("searchForFood");
		}
	}
	

	public String returnToNest()
	{
		if (getPatchColor().equals(Color.red)){
			setColor(Color.RED);
			randomHeading();
			quantity=50;
			emit("nestScent", 500);
			return("searchForFood");
		}
		else{
			double nestScent = smell("nestScent");
			if(nestScent <= 1) 
				wiggle();
			else {
				setHeading(getDirectionOfMaxInMyDirection("nestScent"));
				if(nestScent > smellNextPatch("nestScent"))
					wiggle();
				else
					fd(1);
			}
			if(quantity>0) 
				emit("foodScent", quantity--);
			return("returnToNest");
		}
	}
	
}
