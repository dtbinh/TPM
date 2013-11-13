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

package edu.turtlekit2.kernel.environment;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import edu.turtlekit2.kernel.agents.Turtle;

/** The Patch class
 * @author Fabien MICHEL
 * @version 1.2 20/3/2000
 */

final public class Patch {
    TurtleEnvironment world;
    public double diffusion;
    public double[] variableValue;
    Hashtable<String, Object> marks=null;
    public transient Patch neighbors[];
    public Collection<Turtle> turtlesHere = new HashSet<Turtle>();
    public Color color;
    public boolean change = true;
    public int x,y;
    
    public Patch(TurtleEnvironment w,int x,int y) {
        world=w;
        color = Color.black;
        this.x=x;
        this.y=y;
    }
    public final void setNeighborhood(Patch[] acc){neighbors = acc;}
    
    final void update(int i) {
        for (int a=0;a<neighbors.length;a++)
            variableValue[i]+=neighbors[a].diffusion;
    }
    
    public final void removeAgent(Turtle a) {
        change=true;
        turtlesHere.remove(a);
    }
    
    public final void addAgent(Turtle a) {
        turtlesHere.add(a);
        a.position=this;
    }
    
//    public Patch getNeighborWithMaxOf(String patchVariable){
//    	double max = -Double.MAX_VALUE;
//    	for (final Patch neighbor : neighbors) {
//			
//		}
//    }
    
//    final int getVariableIndex(String fl){
//    	return ((Integer)world.gridVariables.get(fl)).intValue();
//    	}
    
    /**set the value of the corresponding patch variable to n,
  use it with observers in the setup method for example*/
    final public void setPatchVariable(String variableName,double n){
        change=true;
        //variableValue[getVariableIndex(variableName)]=n;
        world.gridVariables.get(variableName).gridValues[x][y]=n;
    }
    /**add n to the value of the corresponding patch variable*/
    final public void incrementPatchVariable(String variableName,double n)
    {
    	change=true;
    	world.gridVariables.get(variableName).gridValues[x][y]+=n;
    }
    /**return the value of the corresponding variable*/
    final public double smell(String variableName)
    {
        return world.gridVariables.get(variableName).gridValues[x][y];
    }
    
    final public Color getColor(){return color;}
    
    final public void setColor(Color c) {
        color = c;
        change = true;
    }
    
    public final synchronized Turtle[] getOtherTurtles(Turtle t) {
        Collection<Turtle> c = new HashSet<Turtle>(turtlesHere);
        c.remove(t);
        return c.toArray(new Turtle[0]);
    }
    
    public final synchronized int size() {
        return turtlesHere.size();
    }
    
    /** Drop a mark on the patch
   
  @param markName  mark name
  @param value  mark itself, can be any java object*/
    final public void dropMark(String markName, Object value) {
        change = true;
        if (marks == null)
            marks = new Hashtable<String, Object>(1);
        marks.put(markName,value);
    }
    
    /** get a mark deposed on the patch
         @return the correponding java object, null if not present*/
    final public Object getMark(String markName){
        if (marks == null) return null;
        Object theMark = marks.get(markName);
        if (theMark != null) {
            change = true;
            marks.remove(markName);
            return theMark;
        } else
            return null;
    }
    /** tests if the corresponding mark is present on the patch (true or false)*/
    final public boolean isMarkPresent(String markName ) {
        if (marks != null)
            return marks.containsKey(markName);
        else
            return false;
    }
    
    /** returns the turtles who are on the patch*/
    final public Turtle[] getTurtles() {
        return turtlesHere.toArray(new Turtle[turtlesHere.size()]);
    }
    
    final public Patch[] getNeighbors(){return neighbors;}
    
    final public String toString() {
        String s="";
        for(String var : world.variables.keySet()) {
            double d = smell(var);
            s+=(""+var+"="+d+",");
        }
        return (s+"; ");
    }
    
}
