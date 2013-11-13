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
package edu.turtlekit2.kernel.agents;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import edu.turtlekit2.Tk2Launcher;

import madkit.boot.Madkit;
import madkit.kernel.AbstractAgent;
import madkit.kernel.Activator;

/** The TurtleActivator invoke and set the turtles nextAction variable 

  @author Fabien MICHEL and Gregory Beurier
  @version 5.0 05/2010 */

final class TurtleActivator extends Activator<Turtle>
{     
	Map<Class<Turtle>,Map<String,Method>> methodTable;

	public TurtleActivator(String group)
	{
		super(Tk2Launcher.COMMUNITY,group, Turtle.TURTLE_DEFAULT_ROLE);
		methodTable=new HashMap<Class<Turtle>,Map<String,Method>>();
	}
	
	public TurtleActivator(String group, String role)
	{
		super(Tk2Launcher.COMMUNITY,group, role);
		methodTable=new HashMap<Class<Turtle>,Map<String,Method>>();
	}

	@SuppressWarnings("unchecked")
	public void initialize()
	{
		for(AbstractAgent t : getCurrentAgentsList())
		{
			methodTable.put((Class<Turtle>) t.getClass(),new HashMap<String,Method>());
		}
	}    

	@SuppressWarnings("unchecked")
	public void update(AbstractAgent theAgent, boolean added)
	{
		Class<Turtle> c = (Class<Turtle>) theAgent.getClass();
		if (added && ! methodTable.containsKey(c))
			methodTable.put(c,new HashMap<String,Method>());
	}    

	@Override
	synchronized public void execute()
	{
		for(Turtle t : getCurrentAgentsList()) //TODO shuffle or not !!
		{
			if(t.position==null)
				return;
			String nextMethod=null; 
			try	{	
				nextMethod = (String) t.nextAction.invoke(t);
				if(nextMethod.equals(t.nextAction.getName())){
					t.incrementBehaviorCount();
				} 
				else{
					t.setCurrentBehaviorCount(0);
				}
			}
			catch (Exception e){
				if(Madkit.debug){
					System.err.println("Can't invoke:"+e+" "+(t.nextAction).toString()+"\n");
					e.printStackTrace();
					return;
				}
			}
			if (nextMethod != null)	{
				Method m = methodTable.get(t.getClass()).get(nextMethod);
				if (m == null)	{
					try {
						final Class<? extends Turtle> c = t.getClass();
						m = c.getMethod(nextMethod);
						methodTable.get(c).put(nextMethod.intern(), m);
					}
					catch (NoSuchMethodException e) {System.err.println("Can't find method: "+nextMethod);e.printStackTrace();}
					catch (SecurityException e) {System.err.println("problem with method: "+nextMethod);e.printStackTrace();}
				}
				t.setNextAction(m);
			}
			else
				t.setNextAction(null);
		}
	}

	final synchronized Turtle[] getTurtles()
	{
		return getCurrentAgentsList().toArray(new Turtle[numberOfAgents()]);
	}


}

