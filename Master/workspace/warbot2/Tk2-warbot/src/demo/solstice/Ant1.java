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
import edu.turtlekit2.kernel.agents.Turtle;


@SuppressWarnings("serial")
public class Ant1 extends Turtle
{
	public Ant1()
	{
		super("moveAround");
		randomHeading();
	}

	
	public String moveAround()
	{
		wiggle();
		return ("moveAround");
	}
	
	
	
}
