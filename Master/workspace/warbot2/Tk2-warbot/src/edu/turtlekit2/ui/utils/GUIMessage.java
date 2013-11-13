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
package edu.turtlekit2.ui.utils;

import edu.turtlekit2.ui.BoardAgent;
import edu.turtlekit2.ui.SimulationBoard;
import madkit.kernel.ObjectMessage;

/**
 * A GUI message. An agent can send a GUI Message to the "UIManager" of its group in order to display 
 * gui components (windows).
 * @author G. Beurier
 * @version 0.1 - 6/2008 
 * @param <T> - the type of the displayed component
 * @see BoardAgent
 */
public class GUIMessage <T>  extends ObjectMessage<T> {
	static final long serialVersionUID = 1l;
	int location;
	public String name;
	
	public GUIMessage(final T theContent, int location, String name){
		super(theContent);
		this.location = location;
		this.name = name;
	}
	
	public GUIMessage(final T theContent, String name){
		super(theContent);
		this.location = 1;
		this.name = name;
	}
	
	/**
	 * @return the location of the window
	 * @see SimulationBoard
	 */
	public int getLocation(){
		return location;
	}
	
	/**
	 * @return the name of the window
	 */
	public String getName(){
		return name;
	}
	
}
