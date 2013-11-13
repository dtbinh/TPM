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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.Turtle;

import madkit.kernel.AbstractAgent;
import madkit.kernel.ReferenceableAgent;

/**This Agent is the one who creates turtles,patches and who cares about managing them all (creation, death...)

  @author Fabien MICHEL
  @version 3.0 09/10/2001  */

public class TurtleEnvironment extends AbstractAgent implements ReferenceableAgent, Serializable
{
	private static final long serialVersionUID = 1L;
	public Map<String, Integer> variables=null;
	public Map<String, GridVariable> gridVariables=new HashMap<String, GridVariable>();
	Map<String, Double> diffuseCoef=null;
	Map<String, Double> evapCoef=null;
	public Patch[][] grid = new Patch[0][0];
	public int x, y;
	int turtlesCount=-1;
	public String simulationGroup;
	public boolean wrap=true;

	//new 
	Map<Integer, Turtle> launchedTurtles=new HashMap<Integer, Turtle>(50000);
	
	@Deprecated
	public ArrayList<Pheromone> pheromones;

	public TurtleEnvironment ()
	{
		
	}
	
	final public void initialize(final int width, final int height) {
		x = width;
		y = height;
		grid = new Patch[x][y];
		initGrid();
		fillGrid();
	}
	
	/*
	 * Override this method to fill the environment
	 */
	public void fillGrid(){
	}

	public final void initialize(final int width, final int height, final boolean torusMode) {
		x = width;
		y = height;
		wrap = torusMode;
		grid = new Patch[x][y];
		initGrid();
		fillGrid();
	}

	public void displayOff(){
		for (int i=x-1;i>=0;i--)
			for (int j=y-1; j >=0;j--)
				if (grid[i][j].change) grid[i][j].change=false;}

	public void displayOn(){
		for (int i=x-1;i>=0;i--)
			for (int j=y-1; j >=0;j--)
				grid[i][j].change=true;}

	final void addVariables(final String[] var,final double[] values)
	{
		variables = new HashMap<String, Integer>(var.length);
		for(int i=0;i<var.length;i++) variables.put(var[i],new Integer(i));
		for (int i=x-1;i>=0;i--)
			for (int j=y-1; j >=0;j--)
			{
				final double[] val = new double[values.length];
				for(int k=0;k<var.length;k++) val[k]=values[k];
				grid[i][j].variableValue = val;
			}
	}

	final void diffuseVariables(final String[] var,final double[] dc)
	{
		diffuseCoef = new HashMap<String, Double>(var.length);
		for(int i=0;i<var.length;i++)
			diffuseCoef.put(var[i],new Double(dc[i]));
	}

	final void evapVariables(final String[] var,final double[] dc)
	{
		evapCoef = new HashMap<String, Double>(var.length);
		for(int i=0;i<var.length;i++)
			evapCoef.put(var[i],new Double(dc[i]));
	}

	final public void  initNeighborhood()
	{
		Patch acc[];
		if (wrap)
			for (int i=x-1;i>=0;i--)
				for (int j=y-1; j >=0;j--)
				{
					acc = new Patch[8];
					acc[0]=grid[(i+1) % x][j];
					acc[7]=grid[(i+1) % x][(j-1+y) % y];
					acc[6]=grid[i][(j-1+y) % y];
					acc[5]=grid[(i-1+x) % x][(j-1+y) % y];
					acc[4]=grid[(i-1+x) % x][j];
					acc[3]=grid[(i-1+x) % x][(j+1) % y];
					acc[2]=grid[i][(j+1) % y];
					acc[1]=grid[(i+1) % x][(j+1) % y];
					grid[i][j].setNeighborhood(acc);
				}
		else
		{
			for (int i=x-1;i>=0;i--)
				for (int j=y-1; j >=0;j--)
				{
					if (i==0)
						if (j> 0 && j< y-1)
						{
							acc = new Patch[5];
							acc[0]=grid[i+1][j];
							acc[1]=grid[i+1][j+1];
							acc[2]=grid[i][j+1];
							acc[3]=grid[i][j-1];
							acc[4]=grid[i+1][j-1];
						}
						else
						{
							if (j==0)
							{
								acc= new Patch[3];
								acc[0]=grid[i+1][j];
								acc[1]=grid[i+1][j+1];
								acc[2]=grid[i][j+1];
							}
							else
							{
								acc= new Patch[3];
								acc[0]=grid[i+1][j];
								acc[1]=grid[i][j-1];
								acc[2]=grid[i+1][j-1];
							}
						}
					else 
						if (i==(x-1))
							if (j> 0 && j< y-1)
							{
								acc = new Patch[5];
								acc[0]=grid[i][j+1];
								acc[1]=grid[i-1][j+1];
								acc[2]=grid[i-1][j];
								acc[3]=grid[i-1][j-1];
								acc[4]=grid[i][j-1];
							}
							else
							{
								if (j==0)
								{
									acc= new Patch[3];
									acc[0]=grid[i][j+1];
									acc[1]=grid[i-1][j+1];
									acc[2]=grid[i-1][j];
								}
								else
								{
									acc= new Patch[3];
									acc[0]=grid[i-1][j];
									acc[1]=grid[i-1][j-1];
									acc[2]=grid[i][j-1];
								}
							}
						else
							if (j==0)
							{
								acc = new Patch[5];
								acc[0]=grid[i+1][j];
								acc[1]=grid[i+1][j+1];
								acc[2]=grid[i][j+1];
								acc[3]=grid[i-1][j+1];
								acc[4]=grid[i-1][j];
							}
							else
								if (j==y-1)
								{
									acc = new Patch[5];
									acc[0]=grid[i+1][j];
									acc[1]=grid[i-1][j];
									acc[2]=grid[i-1][j-1];
									acc[3]=grid[i][j-1];
									acc[4]=grid[i+1][j-1];
								}
								else
								{
									acc = new Patch[8];
									acc[0]=grid[i+1][j];
									acc[7]=grid[i+1][j-1];
									acc[6]=grid[i][j-1];
									acc[5]=grid[i-1][j-1];
									acc[4]=grid[i-1][j];
									acc[3]=grid[i-1][j+1];
									acc[2]=grid[i][j+1];
									acc[1]=grid[i+1][j+1];
								}
					grid[i][j].setNeighborhood(acc);
				}
		}
	}

	final public  void initGrid()
	{
		for (int i=x-1;i>=0;i--)
			for (int j=y-1; j >=0;j--)
				grid[i][j] = new Patch(this,i,j);
	}


	final public void diffusion()
	{
		for(final GridVariable z : gridVariables.values())
		{
			z.diffusion();
		}
		/*for(Iterator z = pheromones.iterator();z.hasNext();)
		{
                    if(((Pheromone) z.next()).diffusion())
                        z.remove();
                }*/
		for (int i=x-1; i >=0 ; i--)
			for (int j=y-1; j >=0 ; j--)
				grid[i][j].change = true;
	}


	final public void evaporation()
	{
		for(final GridVariable z : gridVariables.values())
		{
			z.evaporation();
		}
	}

	final public synchronized void clearAllTurtles(){
		for(final Iterator<Turtle> e = getTurtlesList().iterator();e.hasNext();)
		{
			final Turtle t = e.next();
			(t.position).removeAgent(t);
			killAgent(t);
			e.remove();
			//launchedTurtles.remove(new Integer(t.mySelf()));
		}
	}

	final int createAgent(final Turtle agt, final int a, final int b)
	{
		turtlesCount++;
		agt.initialisation(a,b,this,turtlesCount,grid[a][b]); 
		grid[a][b].addAgent(agt);
		//theTurtles.addElement(agt);
		launchedTurtles.put(new Integer(turtlesCount), agt);
		launchAgent(agt,"turtle"+turtlesCount,false);
		return turtlesCount;
	}
	public final int addAgent(final Turtle agt){return createAgent(agt, (int) (Math.random() * x), (int) (Math.random() * y) );}
	public final int addAgent(final Turtle agt, final int u, final int t) {	return createAgent(agt, u%x, t%y);  }

	public final void removeTurtle(final Turtle t)
	{
		(t.position).removeAgent(t);
		launchedTurtles.remove(t.mySelf());
		killAgent(t);
	}

	public final Turtle getTurtleWithID(final int id){
		return launchedTurtles.get(id);
	}

	final public Collection<Turtle> getTurtlesList(){
		return launchedTurtles.values();
	}

	public final Turtle[] turtlesAt(final int u,final int z)
	{
		return grid[u][z].getTurtles();
	}

	public final int turtlesCountAt(final int u, final int v){return grid[u][v].size();}

	public final Color getPatchColor(final int u,final int v){    return (grid[u][v]).color;}
	public final void setPatchColor(final Color c,final int u,final int v){(grid[u][v]).setColor(c);}

	public final void moveTurtle(final double a,final double b,final Turtle t)
	{
		final int u = (int) Math.round(a)%x;
		final int v = (int) Math.round(b)%y;
		if (grid[u][v] != t.position)
		{
			(t.position).removeAgent(t);
			grid[u][v].addAgent(t);
		}
	}

	final public void activate()
	{
		requestRole(Tk2Launcher.COMMUNITY,simulationGroup,"world",null);
	}

	public void addGridVariable(final PatchVariable pv){
		final GridVariable gv=new GridVariable(this);
		gv.setDiffuseCoef(pv.diffCoef);
		gv.setEvapCoef(pv.evaporation);
		gridVariables.put(pv.name,gv);
	}

	public void clearVariable(final String type){
		for (int i=x-1;i>=0;i--)
			for (int j=y-1; j >=0;j--)
				grid[i][j].setPatchVariable(type,0);
	}

	public void clearVariables(final ArrayList<PatchVariable> volatileVariables){
		for (int i=0; i < volatileVariables.size(); i++){
			clearVariable(((PatchVariable)(volatileVariables.get(i))).getName());
			System.out.println((volatileVariables.get(i)).getName());
		} 
	}
}
